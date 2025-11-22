package az.ingress.model.mapper;

import az.ingress.dao.entity.RecommendationEntity;
import az.ingress.model.queue.RecommendationQueueDto;
import az.ingress.model.request.RecommendationRequest;

public enum RecommendationMapper {

    RECOMMENDATION_MAPPER;

    public RecommendationEntity buildEntity(Long userId, String categoryWeights) {
        return RecommendationEntity.builder()
                .userId(userId)
                .categoryWeights(categoryWeights)
                .build();
    }
}
