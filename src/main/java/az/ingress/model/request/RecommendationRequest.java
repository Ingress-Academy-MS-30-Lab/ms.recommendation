package az.ingress.model.request;

import az.ingress.model.enums.RecommendationSourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class RecommendationRequest {
    private Long userId;
    private String category;
    private RecommendationSourceType sourceType;
}
