package strategy;

public class CarPricingStrategy implements PricingStrategy{
    @Override
    public double calculateFee(double durationInHours) {
        return 50.0 * durationInHours;
    }
}
