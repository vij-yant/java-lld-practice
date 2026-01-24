package model;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class ParkingFloor {
    private final int floorNumber;
    private final Map<Integer,ParkingSpot> spots;

    private final Map<VehicleType, ReentrantLock> floorVehicleLock = new EnumMap<>(VehicleType.class);

    public ParkingFloor(int floorNumber, Map<Integer,ParkingSpot>spots) {
        this.floorNumber = floorNumber;
        this.spots = spots;
        for(VehicleType type : VehicleType.values()) {
            floorVehicleLock.put(type,new ReentrantLock());
        }
    }

    public ReentrantLock getFloorVehicleLock(VehicleType type) {
        return floorVehicleLock.get(type);
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public ParkingSpot getSpot(int spotNo) {
        return spots.getOrDefault(spotNo,null);
    }

    public List<ParkingSpot> getAvailableSpots() {
        return spots.values().stream().filter(ParkingSpot::isFree).toList();
    }
}
