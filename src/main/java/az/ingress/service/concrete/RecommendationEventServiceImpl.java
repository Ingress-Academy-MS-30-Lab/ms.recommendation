package az.ingress.service.concrete;

import az.ingress.dao.entity.RecommendationEventEntity;
import az.ingress.dao.repository.RecommendationEventRepository;
import az.ingress.model.constants.RecommendationConstants;
import az.ingress.service.abstraction.RecommendationEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static az.ingress.model.constants.RecommendationConstants.TIME_RANGE;

@Service
@RequiredArgsConstructor
public class RecommendationEventServiceImpl implements RecommendationEventService {


    private final RecommendationEventRepository recommendationEventRepository;

    @Override
    public List<RecommendationEventEntity> findAllByUserIdAndCategoryId(Long userId, Long categoryId) {
        return recommendationEventRepository.findAllByUserIdAndCategoryId(userId, categoryId);
    }

    @Override
    public void save(RecommendationEventEntity recommendationEvent) {
        recommendationEventRepository.save(recommendationEvent);
    }

    @Override
    public void cleanupOldEvents() {
        var timeHold = LocalDateTime.now().minusDays(TIME_RANGE);
        recommendationEventRepository.deleteOlderThan(timeHold);
    }
}
