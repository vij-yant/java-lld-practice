package service;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import strategy.*;

import java.util.*;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class TestSetup {

    static ParkingService createParkingService(int floors, int spotsPerFloor) {
        List<ParkingFloor> floorList = new ArrayList<>();

        for (int f = 0; f < floors; f++) {
            Map<Integer,ParkingSpot> spots = new ConcurrentHashMap<>();
            for (int s = 0; s < spotsPerFloor; s++) {
                spots.put(s,new ParkingSpot(s, SpotType.MEDIUM));
            }
            ParkingFloor floor = new ParkingFloor(f,spots);
            floorList.add(floor);
        }

        ParkingLot lot = new ParkingLot(floorList);

        SpotAllocationStrategy strategy = new FirstAvailableSpot();

        Map<VehicleType, PricingStrategy> pricing = new HashMap<>();
        pricing.put(VehicleType.CAR, new CarPricingStrategy());

        FeeCalculator feeCalculator = new FeeCalculator(pricing);
        TicketService ticketService = new TicketService(feeCalculator);

        return new ParkingService(lot, ticketService, strategy);
    }
}


public class parkingServiceTest {

    @Test
    void concurrentPark_shouldAllocateUniqueSpots() throws Exception {
        ParkingService service = TestSetup.createParkingService(1, 3);

        int threads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(threads);

        List<Ticket> tickets = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < threads; i++) {
            int idx = i;
            executor.submit(() -> {
                try {
                    start.await();
                    Ticket t = service.parkVehicle(new Car("CAR-" + idx));
                    if (t != null) tickets.add(t);
                } catch (Exception ignored) {}
                finally { done.countDown(); }
            });
        }

        start.countDown();
        done.await();
        executor.shutdown();

        assertFalse(tickets.isEmpty());
        assertTrue(tickets.size() < 3);

        // Ensure no duplicate spot allocation
        Set<String> spotKeys = new HashSet<>();
        for (Ticket t : tickets) {
            String key = t.getFloorNo() + "-" + t.getSpotNo();
            assertTrue(spotKeys.add(key), "Duplicate spot allocated!");
        }
    }

    @Test
    void concurrentUnpark_sameTicket_onlyOneSucceeds() throws Exception {
        ParkingService service = TestSetup.createParkingService(1, 1);
        Ticket ticket = service.parkVehicle(new Car("CAR-1"));

        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch start = new CountDownLatch(1);

        List<Exception> exceptions = Collections.synchronizedList(new ArrayList<>());
        List<Receipt> receipts = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < 2; i++) {
            executor.submit(() -> {
                try {
                    start.await();
                    receipts.add(service.unParkVehicle(ticket.getTicketId()));
                } catch (Exception e) {
                    exceptions.add(e);
                }
            });
        }

        start.countDown();
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        assertEquals(1, receipts.size());
        assertEquals(1, exceptions.size());
    }

    @Test
    void parkAndUnpark_interleaving_shouldRemainConsistent() throws Exception {
        ParkingService service = TestSetup.createParkingService(1, 1);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch start = new CountDownLatch(1);

        Ticket[] ticketHolder = new Ticket[1];

        executor.submit(() -> {
            try {
                start.await();
                ticketHolder[0] = service.parkVehicle(new Car("CAR-X"));
            } catch (Exception ignored) {}
        });

        executor.submit(() -> {
            try {
                start.await();
                Thread.sleep(10);
                if (ticketHolder[0] != null) {
                    service.unParkVehicle(ticketHolder[0].getTicketId());
                }
            } catch (Exception ignored) {}
        });

        start.countDown();
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        // Spot must be free again
        ParkingSpot spot = service.getLot().getFloors().get(0).getSpot(0);
        assertTrue(spot.isFree());
    }


}
