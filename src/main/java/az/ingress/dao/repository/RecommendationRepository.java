package az.ingress.dao.repository;

import az.ingress.dao.entity.RecommendationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RecommendationRepository extends JpaRepository<RecommendationEntity, Long> {

    RecommendationEntity findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    @Query("SELECT r.category FROM RecommendationEntity r WHERE r.userId = :userId")
    String findCategoryByUserId(Long userId);

    @Query("SELECT DISTINCT r.category FROM RecommendationEntity r")
    List<String> findAllDistinctCategory();
}
