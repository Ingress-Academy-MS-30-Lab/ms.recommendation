package az.ingress.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RecommendationSourceType {
    ORDER(5.0),
    CART(3);

    private final double weight;
}
