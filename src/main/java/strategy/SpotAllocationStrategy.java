package strategy;

import model.ParkingSpot;
import model.Vehicle;
import model.VehicleType;

import java.util.List;
import java.util.Optional;

public interface SpotAllocationStrategy {
    public Optional<ParkingSpot> allocate(VehicleType type, List<ParkingSpot> freeSpots);
}
