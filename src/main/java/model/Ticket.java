package model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {
    private final String ticketId;
    private final int spotNo;
    private final int floorNo;
    private final String vehicleNo;
    private final VehicleType vehicleType;
    private final LocalDateTime entryTime;

    public Ticket(int spotNo, int floorNo, String vehicleNo, VehicleType vehicleType, LocalDateTime entryTime) {
        this.ticketId = UUID.randomUUID().toString();
        this.spotNo = spotNo;
        this.floorNo = floorNo;
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
        this.entryTime = entryTime;
    }

    public int getSpotNo() {
        return spotNo;
    }

    public String getTicketId() {
        return ticketId;
    }


    public int getFloorNo() {
        return floorNo;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId='" + ticketId + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", vehicleType=" + vehicleType +
                ", floorNo=" + floorNo +
                ", spotNo=" + spotNo +
                ", entryTime=" + entryTime +
                '}';
    }

}
