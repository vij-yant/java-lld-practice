package strategy;

import java.time.Duration;

public interface PricingStrategy {
    public double calculateFee(double durationInHours);
}
