package az.ingress.service.concrete;

import az.ingress.dao.entity.RecommendationEventEntity;
import az.ingress.dao.repository.RecommendationEventRepository;
import az.ingress.service.abstraction.RecommendationEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationEventServiceImpl implements RecommendationEventService {


    private final RecommendationEventRepository recommendationEventRepository;

    @Override
    public List<RecommendationEventEntity> findAllByUserId(Long userId) {
        return recommendationEventRepository.findAllByUserId(userId);
    }

    @Override
    public void save(RecommendationEventEntity recommendationEvent) {
        recommendationEventRepository.save(recommendationEvent);
    }

    @Override
    public void cleanupOldEvents() {
        var timeHold = LocalDateTime.now().minusDays(60);
        recommendationEventRepository.deleteOlderThan(timeHold);
    }


}
