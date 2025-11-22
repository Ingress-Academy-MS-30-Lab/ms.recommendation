package az.ingress.service;

import az.ingress.aop.Log;
import az.ingress.dao.entity.RecommendationEntity;
import az.ingress.dao.entity.RecommendationEventEntity;
import az.ingress.dao.repository.RecommendationEventRepository;
import az.ingress.dao.repository.RecommendationRepository;
import az.ingress.model.mapper.RecommendationEventMapper;
import az.ingress.model.queue.RecommendationQueueDto;
import az.ingress.service.abstraction.RecommendationAggregatorService;
import az.ingress.service.strategy.RecommendationWeights;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static az.ingress.model.enums.RecommendationSourceType.CART;
import static az.ingress.model.enums.RecommendationSourceType.ORDER;
import static az.ingress.model.mapper.RecommendationEventMapper.RECOMMENDATION_EVENT_MAPPER;
import static az.ingress.model.mapper.RecommendationMapper.RECOMMENDATION_MAPPER;

@Service
@RequiredArgsConstructor
@Log
public class QueueService {

    private final RecommendationEventRepository eventRepository;
    private final RecommendationAggregatorService recommendationAggregatorService;

    public void processQueueEvent(RecommendationQueueDto dto) {
        var base = RecommendationWeights.getBaseWeight(dto.getSourceType());
        var weight = RecommendationWeights.applyDecay(base, dto.getCreatedAt());

        var entity = RECOMMENDATION_EVENT_MAPPER.buildEntity(dto);
        entity.setWeight(weight);

        eventRepository.save(entity);
        recommendationAggregatorService.aggregateUser(dto.getUserId());

    }
}
