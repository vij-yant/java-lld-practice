package model;

public abstract class Vehicle {
    protected String plate;
    protected VehicleType vehicleType;

    protected Vehicle(String plate, VehicleType vehicleType) {
        this.plate = plate;
        this.vehicleType = vehicleType;
    }

    public String getPlate() {
        return plate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }
}

