package strategy;

public class TruckPricingStrategy implements PricingStrategy{
    @Override
    public double calculateFee(double durationInHours) {
        return 70.0 * durationInHours;
    }
}
