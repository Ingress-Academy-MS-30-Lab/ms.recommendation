package az.ingress.model.response;

import az.ingress.model.enums.RecommendationSourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecommendationResponse {
    private Long userId;
    private String category;
    private RecommendationSourceType sourceType;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
