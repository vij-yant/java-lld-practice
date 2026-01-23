package model;

public enum SpotType {
    SMALL,MEDIUM,LARGE;
    public boolean canPark(VehicleType type) {
        return switch (this) {
            case SMALL -> type == VehicleType.BIKE;
            case MEDIUM -> type == VehicleType.BIKE || type == VehicleType.CAR;
            case LARGE -> true;
        };
    }
}
