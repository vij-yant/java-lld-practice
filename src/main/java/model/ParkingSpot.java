package model;

public class ParkingSpot {
    private final int spotNo;
    private final SpotType type;
    private boolean isFree;

    public ParkingSpot(int spotNo, SpotType type) {
        this.spotNo = spotNo;
        this.type = type;
        this.isFree = true;
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
        isFree = true;
    }
}
