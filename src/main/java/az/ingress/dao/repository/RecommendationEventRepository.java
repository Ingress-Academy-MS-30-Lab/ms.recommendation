package az.ingress.dao.repository;

import az.ingress.dao.entity.RecommendationEventEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface RecommendationEventRepository extends CrudRepository<RecommendationEventEntity, Long> {

    List<RecommendationEventEntity> findAllByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM RecommendationEventEntity e WHERE e.createdAt < :timeHold")
    void deleteOlderThan(LocalDateTime timeHold);
}
