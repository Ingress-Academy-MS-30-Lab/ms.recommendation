package az.ingress.service;

import az.ingress.aop.Log;
import az.ingress.dao.entity.RecommendationEntity;
import az.ingress.dao.repository.RecommendationRepository;
import az.ingress.model.events.CartEvent;
import az.ingress.model.events.OrderEvent;
import az.ingress.model.queue.RecommendationQueueDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static az.ingress.model.enums.RecommendationSourceType.CART;
import static az.ingress.model.enums.RecommendationSourceType.ORDER;
import static az.ingress.model.mapper.RecommendationMapper.RECOMMENDATION_MAPPER;

@Service
@RequiredArgsConstructor
@Log
public class QueueService {

    private final RecommendationRepository recommendationRepository;

    public void queueProcess(RecommendationQueueDto queueDto) {
        var entity = recommendationRepository.findByUserId(queueDto.getUserId());
        if (entity != null) {
            if (queueDto.getCreatedAt().isAfter(entity.getUpdatedAt())) {
                entity.setSourceType(queueDto.getSourceType());
                entity.setCategory(queueDto.getCategory());
                recommendationRepository.save(entity);
            }
        } else {
            var newEntity = RECOMMENDATION_MAPPER.buildEntity(queueDto);
            recommendationRepository.save(newEntity);
        }
    }
}
