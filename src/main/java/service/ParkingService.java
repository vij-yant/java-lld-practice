package service;

import exception.NoAvailableSpotException;
import exception.TicketAlreadyClosedException;
import model.*;
import strategy.SpotAllocationStrategy;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ParkingService {
    private final ParkingLot lot;
    private final TicketService ticketService;
    private final SpotAllocationStrategy strategy;

    public ParkingService(ParkingLot lot, TicketService ticketService, SpotAllocationStrategy strategy) {
        this.lot = lot;
        this.ticketService = ticketService;
        this.strategy = strategy;
    }

    public ParkingLot getLot() {
        return lot;
    }

    public Ticket parkVehicle(Vehicle vehicle) {
        for(ParkingFloor floor : lot.getFloors()) {
            ReentrantLock floorLock = floor.getFloorVehicleLock(vehicle.getVehicleType());

            if(!floorLock.tryLock()){
                continue;
            }

            try {
                List<ParkingSpot> spots = floor.getAvailableSpots();
                Optional<ParkingSpot> spot = strategy.allocate(vehicle.getVehicleType(),spots);
                if(spot.isPresent()) {
                    ParkingSpot found = spot.get();
                    found.occupy();
                    return ticketService.createTicket(floor.getFloorNumber(), found.getSpotNo(),vehicle);
                }
            } finally {
                floorLock.unlock();
            }
        }
        throw new NoAvailableSpotException(vehicle);
    }

    public Receipt unParkVehicle(String ticketId) throws Exception {
        Ticket t = ticketService.getTicketById(ticketId);
        Receipt r;
        synchronized (t) {
            if (t.isClosed()) {
                throw new TicketAlreadyClosedException(t.getTicketId());
            }
            r = ticketService.processTicket(t);
            t.close();
        }
        ParkingFloor floor = lot.getFloors().get(t.getFloorNo());
        ParkingSpot spot = floor.getSpot(t.getSpotNo());
        Lock lock = spot.getLock();
        lock.lock();
        try {
            if(spot.isFree()){
                throw new IllegalArgumentException("Spot already free");
            }
            spot.setFree();
        } finally {
            lock.unlock();
        }
        ticketService.closeTicket(ticketId);
        return r;
    }
}
