package az.ingress.model.mapper;

import az.ingress.dao.entity.RecommendationEntity;
import az.ingress.model.events.CartEvent;
import az.ingress.model.events.OrderEvent;
import az.ingress.model.request.RecommendationRequest;
import az.ingress.model.response.RecommendationResponse;

import static az.ingress.model.enums.RecommendationSourceType.CART;
import static az.ingress.model.enums.RecommendationSourceType.ORDER;

public enum RecommendationMapper {

    RECOMMENDATION_MAPPER;

    public RecommendationEntity buildEntity(RecommendationRequest requestDto) {
        return RecommendationEntity.builder()
                .userId(requestDto.getUserId())
                .category(requestDto.getCategory())
                .sourceType(requestDto.getSourceType())
                .build();
    }

    public RecommendationEntity buildEntity(CartEvent cartEvent) {
        return RecommendationEntity.builder()
                .userId(cartEvent.getUserId())
                .category(cartEvent.getCategory())
                .sourceType(CART)
                .build();
    }

    public RecommendationEntity buildEntity(OrderEvent orderEvent) {
        return RecommendationEntity.builder()
                .userId(orderEvent.getUserId())
                .category(orderEvent.getCategory())
                .sourceType(ORDER)
                .build();
    }

    public RecommendationResponse toResponse(RecommendationEntity recommendationEntity) {
        return RecommendationResponse.builder()
                .userId(recommendationEntity.getUserId())
                .category(recommendationEntity.getCategory())
                .sourceType(recommendationEntity.getSourceType())
                .updatedAt(recommendationEntity.getUpdatedAt())
                .createdAt(recommendationEntity.getCreatedAt())
                .build();
    }

}
