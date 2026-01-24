package model;

import java.time.LocalDateTime;

public class Receipt {
    private final String ticketId;
    private final String vehicleNo;
    private final VehicleType vehicleType;
    private final int floorNo;
    private final int spotNo;
    private final LocalDateTime entryTime;
    private final LocalDateTime exitTime;
    private final double amountPaid;

    public Receipt(String ticketId, String vehicleNo, VehicleType vehicleType, int floorNo, int spotNo, LocalDateTime entryTime, LocalDateTime exitTime, double amountPaid) {
        this.ticketId = ticketId;
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
        this.floorNo = floorNo;
        this.spotNo = spotNo;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.amountPaid = amountPaid;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "ticketId='" + ticketId + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", vehicleType=" + vehicleType +
                ", floorNo=" + floorNo +
                ", spotNo=" + spotNo +
                ", entryTime=" + entryTime +
                ", exitTime=" + exitTime +
                ", totalFee=" + amountPaid +
                '}';
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public int getFloorNo() {
        return floorNo;
    }

    public int getSpotNo() {
        return spotNo;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public double getAmountPaid() {
        return amountPaid;
    }
}
