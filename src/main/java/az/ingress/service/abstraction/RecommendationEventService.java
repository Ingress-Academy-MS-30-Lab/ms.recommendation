package az.ingress.service.abstraction;

import az.ingress.dao.entity.RecommendationEventEntity;

import java.util.List;

public interface RecommendationEventService {

    List<RecommendationEventEntity> findAllByUserId(Long userId);

    void save(RecommendationEventEntity recommendationEvent);

    void cleanupOldEvents();
}
