package exception;


import model.Vehicle;

public class NoAvailableSpotException extends RuntimeException {

    public NoAvailableSpotException(Vehicle vehicle) {
        super("No parking spot available :" + vehicle.getVehicleType() + " : "+ vehicle.getPlate());
    }
}
