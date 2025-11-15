package az.ingress.model.mapper;

import az.ingress.dao.entity.RecommendationEntity;
import az.ingress.model.queue.RecommendationQueueDto;

public enum RecommendationMapper {

    RECOMMENDATION_MAPPER;

    public RecommendationEntity buildEntity(RecommendationQueueDto queueDto) {
        return RecommendationEntity.builder()
                .userId(queueDto.getUserId())
                .category(queueDto.getCategory())
                .sourceType(queueDto.getSourceType())
                .build();
    }
}
