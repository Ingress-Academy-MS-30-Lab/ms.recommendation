package az.ingress.service.strategy;

import az.ingress.model.enums.RecommendationSourceType;

import java.time.LocalDateTime;

import static java.lang.Math.pow;
import static java.time.Duration.between;
import static java.time.LocalDateTime.now;

public class RecommendationWeights {

    public static double getBaseWeight(RecommendationSourceType type) {
        return switch (type) {
            case ORDER -> 5.0;
            case CART -> 3.0;
        };
    }

    public static double applyDecay(double weight, LocalDateTime eventTime) {
        var days = between(eventTime, now()).toDays();
        var decayFactor = pow(0.95, days);
        return weight * decayFactor;
    }
}
