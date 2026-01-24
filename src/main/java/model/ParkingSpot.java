package model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ParkingSpot {
    private final int spotNo;
    private final SpotType type;
    private boolean isFree;
    private final Lock spotLock = new ReentrantLock(true);

    public ParkingSpot(int spotNo, SpotType type) {
        this.spotNo = spotNo;
        this.type = type;
        this.isFree = true;
    }

    public Lock getLock() {
        return spotLock;
    }

    public int getSpotNo() {
        return spotNo;
    }

    public SpotType getType() {
        return type;
    }

    public boolean isFree() {
        return isFree;
    }

    public void occupy() {
        isFree = false;
    }

    public void setFree() {
        isFree = false;
    }
}
