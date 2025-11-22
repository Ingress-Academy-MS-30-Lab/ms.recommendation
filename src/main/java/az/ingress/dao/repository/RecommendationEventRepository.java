package az.ingress.dao.repository;

import az.ingress.dao.entity.RecommendationEventEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecommendationEventRepository extends CrudRepository<RecommendationEventEntity, Long> {

    List<RecommendationEventEntity> findAllByUserId(Long userId);
}
