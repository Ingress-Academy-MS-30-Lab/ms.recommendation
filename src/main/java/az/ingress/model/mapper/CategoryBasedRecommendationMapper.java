package az.ingress.model.mapper;

import az.ingress.dao.entity.CategoryBasedRecommendationEntity;

public enum CategoryBasedRecommendationMapper {

    CATEGORY_BASED_RECOMMENDATION_MAPPER;

    public CategoryBasedRecommendationEntity buildEntity(Long userId, Long categoryId, double weight) {
        return CategoryBasedRecommendationEntity.builder()
                .userId(userId)
                .categoryId(categoryId)
                .weight(weight)
                .build();
    }
}
