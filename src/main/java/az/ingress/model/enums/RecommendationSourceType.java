package az.ingress.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public enum RecommendationSourceType {
    ORDER(new BigDecimal(5)),
    CART(new BigDecimal(3));

    private final BigDecimal weight;
}
