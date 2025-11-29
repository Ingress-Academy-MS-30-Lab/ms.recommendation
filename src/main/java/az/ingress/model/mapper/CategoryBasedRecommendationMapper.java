package az.ingress.model.mapper;

import az.ingress.dao.entity.CategoryBasedRecommendationEntity;

import java.math.BigDecimal;

public enum CategoryBasedRecommendationMapper {

    CATEGORY_BASED_RECOMMENDATION_MAPPER;

    public CategoryBasedRecommendationEntity buildEntity(Long userId, Long categoryId, BigDecimal weight) {
        return CategoryBasedRecommendationEntity.builder()
                .userId(userId)
                .categoryId(categoryId)
                .weight(weight)
                .build();
    }
}
