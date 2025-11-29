package az.ingress.service.concrete;

import az.ingress.aop.Log;
import az.ingress.service.abstraction.CategoryBasedRecommendationService;
import az.ingress.service.abstraction.CategoryBasedRecommendationAggregatorService;
import az.ingress.service.abstraction.RecommendationEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static az.ingress.model.mapper.CategoryBasedRecommendationMapper.CATEGORY_BASED_RECOMMENDATION_MAPPER;
import static az.ingress.service.strategy.RecommendationWeights.applyReducing;
import static java.math.BigDecimal.ZERO;

@Service
@RequiredArgsConstructor
@Log
public class CategoryBasedRecommendationAggregatorServiceImpl implements CategoryBasedRecommendationAggregatorService {

    private final RecommendationEventService recommendationEventService;
    private final CategoryBasedRecommendationService categoryBasedRecommendationService;

    @Override
    public void createOrUpdateUserDetails(Long userId, Long categoryId) {
        var events = recommendationEventService.findAllByUserIdAndCategoryId(userId, categoryId);
        if (events.isEmpty()) return;

        var newWeight = ZERO;

        for (var event : events) {
            var reducedWeight = applyReducing(event.getSourceType().getWeight(), event.getCreatedAt());
            newWeight = newWeight.add(reducedWeight);
        }
        var existing = categoryBasedRecommendationService.
                findByUserIdAndCategoryId(userId, categoryId).orElse(null);

        if (existing == null) {
            var entity = CATEGORY_BASED_RECOMMENDATION_MAPPER
                    .buildEntity(userId, categoryId, newWeight);
            categoryBasedRecommendationService.save(entity);
        } else {
            existing.setWeight(newWeight);
            categoryBasedRecommendationService.save(existing);
        }
    }
}
