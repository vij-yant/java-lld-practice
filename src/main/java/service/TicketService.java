package service;

import model.Receipt;
import model.Ticket;
import model.Vehicle;
import strategy.FeeCalculator;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TicketService {
    private final FeeCalculator feeCalculator;
    private final Map<String,Ticket> activeTickets = new HashMap<>();

    public TicketService(FeeCalculator feeCalculator) {
        this.feeCalculator = feeCalculator;
    }


    public Ticket createTicket(int floorNo , int spotNo, Vehicle vehicle) {
        Ticket t = new Ticket(spotNo,floorNo, vehicle.getPlate(), vehicle.getVehicleType(),LocalDateTime.now());
        activeTickets.put(t.getTicketId(),t);
        return t;
    }
    public Receipt processTicket(Ticket ticket) throws Exception {
        LocalDateTime exitTime = LocalDateTime.now();
        double hours = Duration.between(ticket.getEntryTime(),exitTime).toMinutes() / 60.0;
        double fee = feeCalculator.calculate(ticket.getVehicleType(),hours);
        Receipt r = new Receipt(ticket.getTicketId(),
                ticket.getVehicleNo(), ticket.getVehicleType(),
                ticket.getFloorNo(), ticket.getSpotNo(),
                ticket.getEntryTime(), exitTime, fee);
        return r;
    }

    public void closeTicket(String ticketId) {
        activeTickets.remove(ticketId);
    }
    public Ticket getTicketById(String ticketId) throws Exception {
        if(!activeTickets.containsKey(ticketId)) {
            throw new Exception("Invalid ticket");
        }
        return activeTickets.get(ticketId);
    }
}
