package az.ingress.service;

import az.ingress.aop.ToLog;
import az.ingress.dao.repository.RecommendationRepository;
import az.ingress.model.events.CartEvent;
import az.ingress.model.events.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static az.ingress.model.enums.RecommendationSourceType.CART;
import static az.ingress.model.enums.RecommendationSourceType.ORDER;
import static az.ingress.model.mapper.RecommendationMapper.RECOMMENDATION_MAPPER;

@Service
@RequiredArgsConstructor
public class QueueService {

    private final RecommendationRepository recommendationRepository;

    @ToLog
    public void processCartEvent(CartEvent cartEvent) {
        var entity = recommendationRepository.findByUserId(cartEvent.getUserId());

        if (entity != null) {
            if (cartEvent.getCreatedAt().isAfter(entity.getUpdatedAt())) {
                entity.setSourceType(CART);
                entity.setCategory(cartEvent.getCategory());
                recommendationRepository.save(entity);
            }
        }else {
            var newEntity = RECOMMENDATION_MAPPER.buildEntity(cartEvent);
            recommendationRepository.save(newEntity);
        }
    }

    @ToLog
    public void processOrderEvent(OrderEvent orderEvent) {
        var entity = recommendationRepository.findByUserId(orderEvent.getUserId());

        if (entity != null) {
            if (orderEvent.getCreatedAt().isAfter(entity.getUpdatedAt())) {
                entity.setSourceType(ORDER);
                entity.setCategory(orderEvent.getCategory());
                recommendationRepository.save(entity);
            }
        }else {
            var newEntity = RECOMMENDATION_MAPPER.buildEntity(orderEvent);
            recommendationRepository.save(newEntity);
        }
    }
}
