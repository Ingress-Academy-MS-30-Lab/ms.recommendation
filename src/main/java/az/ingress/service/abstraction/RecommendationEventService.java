package az.ingress.service.abstraction;

import az.ingress.dao.entity.RecommendationEventEntity;

import java.util.List;

public interface RecommendationEventService {

    List<RecommendationEventEntity> findAllByUserIdAndCategoryId(Long userId, Long categoryId);

    void save(RecommendationEventEntity recommendationEvent);

    void cleanupOldEvents();
}
