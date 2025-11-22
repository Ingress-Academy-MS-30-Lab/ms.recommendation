package az.ingress.model.mapper;

import az.ingress.dao.entity.RecommendationEventEntity;
import az.ingress.model.queue.RecommendationQueueDto;

public enum RecommendationEventMapper {

    RECOMMENDATION_EVENT_MAPPER;

    public RecommendationEventEntity buildEntity(RecommendationQueueDto queueDto) {
        return RecommendationEventEntity.builder()
                .userId(queueDto.getUserId())
                .categoryId(queueDto.getCategoryId())
                .sourceType(queueDto.getSourceType())
                .build();
    }
}
