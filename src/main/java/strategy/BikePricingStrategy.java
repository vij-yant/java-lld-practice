package strategy;

public class BikePricingStrategy implements PricingStrategy{
    @Override
    public double calculateFee(double durationInHours) {
        return 20.0 * durationInHours;
    }
}
