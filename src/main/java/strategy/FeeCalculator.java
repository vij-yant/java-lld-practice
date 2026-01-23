package strategy;

import model.VehicleType;

import java.util.Map;

public class FeeCalculator {
    private final Map<VehicleType,PricingStrategy> strategies;

    public FeeCalculator(Map<VehicleType, PricingStrategy> strategies) {
        this.strategies = strategies;
    }

    public double calculate(VehicleType type, double hours) {
        PricingStrategy strategy = strategies.get(type);
        if(strategy == null) {
            throw new IllegalArgumentException("No pricing strategy for " + type);
        }
        return strategy.calculateFee(hours);
    }
}
