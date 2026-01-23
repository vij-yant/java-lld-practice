import model.*;
import service.*;
import strategy.*;

import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {


        List<ParkingFloor> floors = new ArrayList<>();

        for(int i = 0; i < 2; i++) { // 2 floors
            List<ParkingSpot> spots = new ArrayList<>();
            // Each floor has 3 spots
            spots.add(new ParkingSpot(0, SpotType.SMALL));
            spots.add(new ParkingSpot(1, SpotType.MEDIUM));
            spots.add(new ParkingSpot(2, SpotType.LARGE));
            floors.add(new ParkingFloor(i, spots));
        }
        ParkingLot lot = new ParkingLot(floors);

        Map<VehicleType, PricingStrategy> pricingMap = new HashMap<>();
        pricingMap.put(VehicleType.CAR,new CarPricingStrategy());
        pricingMap.put(VehicleType.BIKE,new BikePricingStrategy());
        pricingMap.put(VehicleType.TRUCK, new TruckPricingStrategy());

        FeeCalculator feeCalculator = new FeeCalculator(pricingMap);
        TicketService ticketService = new TicketService(feeCalculator);

        SpotAllocationStrategy strategy = new FirstAvailableSpot();


        ParkingService parkingService = new ParkingService(lot, ticketService, strategy);


        Vehicle bike = new Bike("BIKE-001");
        Vehicle car = new Bike("CAR-001");

        Ticket bikeTicket = parkingService.parkVehicle(bike);
        System.out.println("Bike parked: " + bikeTicket);

        Ticket carTicket = parkingService.parkVehicle(car);
        System.out.println("Car parked: " + carTicket);

        // Wait a bit to simulate parking duration
        Thread.sleep(2000); // 2 seconds

        Receipt bikeReceipt = parkingService.unParkVehicle(bikeTicket.getTicketId());
        System.out.println("Bike Receipt : "+bikeReceipt);

        Receipt carReceipt = parkingService.unParkVehicle(carTicket.getTicketId());
        System.out.println("Car Receipt : "+carReceipt);

    }

}
