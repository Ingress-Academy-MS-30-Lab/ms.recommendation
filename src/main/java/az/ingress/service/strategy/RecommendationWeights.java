package az.ingress.service.strategy;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static az.ingress.model.constants.RecommendationConstants.REDUCING_NUMBER;
import static java.time.Duration.between;
import static java.time.LocalDateTime.now;

public class RecommendationWeights {

    public static BigDecimal applyReducing(BigDecimal weight, LocalDateTime eventTime) {
        var days = (between(eventTime, now()).toDays());
        var reducingProcess = new BigDecimal(REDUCING_NUMBER).pow((int) days);
        return weight.multiply(reducingProcess);
    }
}
