package az.ingress.service.concrete;

import az.ingress.aop.Log;
import az.ingress.service.abstraction.CategoryBasedRecommendationService;
import az.ingress.service.abstraction.CategoryBasedRecommendationAggregatorService;
import az.ingress.service.abstraction.RecommendationEventService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static az.ingress.model.mapper.CategoryBasedRecommendationMapper.CATEGORY_BASED_RECOMMENDATION_MAPPER;
import static az.ingress.service.strategy.RecommendationWeights.applyDecay;

@Service
@RequiredArgsConstructor
@Log
public class CategoryBasedRecommendationAggregatorServiceImpl implements CategoryBasedRecommendationAggregatorService {

    private final RecommendationEventService recommendationEventService;
    private final CategoryBasedRecommendationService categoryBasedRecommendationService;

    @Override
    @SneakyThrows
    public void aggregateUser(Long userId) {
        var events = recommendationEventService.findAllByUserId(userId);
        if (events.isEmpty()) return;

        Map<Long, Double> newWeights = new HashMap<>();

        for (var event : events) {
            var decayed = applyDecay(event.getSourceType().getWeight(), event.getCreatedAt());
            newWeights.merge(event.getCategoryId(), decayed, Double::sum);
        }

        newWeights.forEach((categoryId, newWeight) -> {
            var existing = categoryBasedRecommendationService.
                    findByUserIdAndCategoryId(userId, categoryId)
                    .orElse(null);

            if (existing == null) {
                var entity = CATEGORY_BASED_RECOMMENDATION_MAPPER
                        .buildEntity(userId, categoryId, newWeight);
                categoryBasedRecommendationService.save(entity);
            } else {
                existing.setWeight(newWeight);
                categoryBasedRecommendationService.save(existing);
            }
        });
    }
}