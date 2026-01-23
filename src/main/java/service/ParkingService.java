package service;

import model.*;
import strategy.SpotAllocationStrategy;

import java.util.List;
import java.util.Optional;

public class ParkingService {
    private final ParkingLot lot;
    private final TicketService ticketService;
    private final SpotAllocationStrategy strategy;

    public ParkingService(ParkingLot lot, TicketService ticketService, SpotAllocationStrategy strategy) {
        this.lot = lot;
        this.ticketService = ticketService;
        this.strategy = strategy;
    }

    public Ticket parkVehicle(Vehicle vehicle) {
        for(ParkingFloor floor : lot.getFloors()) {
            List<ParkingSpot> spots = floor.getAvailableSpots();
            Optional<ParkingSpot> isFound = strategy.allocate(vehicle.getVehicleType(),spots);
            if(isFound.isEmpty())continue;
            ParkingSpot found = isFound.get();
            found.occupy();
            return ticketService.createTicket(floor.getFloorNumber(), found.getSpotNo(),vehicle);
        }
        return null;
    }

    public Receipt unParkVehicle(String ticketId) throws Exception {
        Ticket t = ticketService.getTicketById(ticketId);
        Receipt r = ticketService.processTicket(t);
        ParkingFloor floor = lot.getFloors().get(t.getFloorNo());
        ParkingSpot spot = floor.getSpots().get(t.getSpotNo());
        spot.setFree();
        ticketService.closeTicket(ticketId);
        return r;
    }
}
