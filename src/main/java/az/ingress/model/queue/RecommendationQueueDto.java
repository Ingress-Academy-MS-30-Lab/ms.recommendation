package az.ingress.model.queue;

import az.ingress.model.enums.RecommendationSourceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationQueueDto {
    private Long userId;
    private Long categoryId;
    private Long productId;
    private RecommendationSourceType sourceType;
}
