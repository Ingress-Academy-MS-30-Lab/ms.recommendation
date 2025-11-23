package az.ingress.service.abstraction;

import az.ingress.dao.entity.CategoryBasedRecommendationEntity;
import az.ingress.model.client.response.ProductResponseDto;

import java.util.List;
import java.util.Optional;

public interface CategoryBasedRecommendationService {

    List<ProductResponseDto> getRecommendationProducts(Long userId);

    Optional<CategoryBasedRecommendationEntity> findByUserIdAndCategoryId(Long userId, Long categoryId);

    void save(CategoryBasedRecommendationEntity categoryBasedRecommendationEntity);

//    void refreshRecommendationProducts();
}
