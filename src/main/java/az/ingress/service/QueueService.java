package az.ingress.service;

import az.ingress.aop.Log;
import az.ingress.model.queue.RecommendationQueueDto;
import az.ingress.service.abstraction.CategoryBasedRecommendationAggregatorService;
import az.ingress.service.abstraction.RecommendationEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static az.ingress.model.mapper.RecommendationEventMapper.RECOMMENDATION_EVENT_MAPPER;

@Service
@RequiredArgsConstructor
@Log
public class QueueService {

    private final RecommendationEventService recommendationEventService;
    private final CategoryBasedRecommendationAggregatorService categoryBasedRecommendationAggregatorService;

    @Transactional(rollbackFor = Exception.class)
    public void processQueueEvent(RecommendationQueueDto dto) {
        var entity = RECOMMENDATION_EVENT_MAPPER.buildEntity(dto);
        recommendationEventService.save(entity);
        categoryBasedRecommendationAggregatorService.createOrUpdateUserDetails(dto.getUserId(), dto.getCategoryId());
    }
}
