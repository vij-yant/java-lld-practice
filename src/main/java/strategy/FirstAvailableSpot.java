package strategy;

import model.ParkingSpot;
import model.VehicleType;

import java.util.List;
import java.util.Optional;

public class FirstAvailableSpot implements SpotAllocationStrategy{
    @Override
    public Optional<ParkingSpot> allocate(VehicleType type, List<ParkingSpot> freeSpots) {
        return freeSpots.stream().filter(spot -> spot.getType().canPark(type)).findFirst();
    }
}
