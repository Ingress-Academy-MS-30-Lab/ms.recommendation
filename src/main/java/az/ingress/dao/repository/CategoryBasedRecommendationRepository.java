package az.ingress.dao.repository;

import az.ingress.dao.entity.CategoryBasedRecommendationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryBasedRecommendationRepository extends CrudRepository<CategoryBasedRecommendationEntity, Long> {

    List<CategoryBasedRecommendationEntity> findAllByUserId(Long userId);

    Optional<CategoryBasedRecommendationEntity> findByUserIdAndCategoryId(Long userId, Long categoryId);
}
