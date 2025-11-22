package az.ingress.service.concrete;

import az.ingress.aop.Log;
import az.ingress.dao.entity.RecommendationEntity;
import az.ingress.dao.repository.RecommendationEventRepository;
import az.ingress.dao.repository.RecommendationRepository;
import az.ingress.model.mapper.RecommendationMapper;
import az.ingress.service.abstraction.RecommendationAggregatorService;
import az.ingress.service.strategy.RecommendationWeights;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static az.ingress.model.mapper.RecommendationMapper.RECOMMENDATION_MAPPER;
import static az.ingress.service.strategy.RecommendationWeights.applyDecay;

@Service
@RequiredArgsConstructor
@Log
public class RecommendationAggregatorServiceImpl implements RecommendationAggregatorService {

    private final RecommendationEventRepository eventRepository;
    private final RecommendationRepository recommendationRepository;
    private final ObjectMapper mapper;

    @Override
    @SneakyThrows
    public void aggregateUser(Long userId) {
        var events = eventRepository.findAllByUserId(userId);
        if (events.isEmpty()) return;

        Map<Long, Double> categoryWeights = new HashMap<>();

        for (var event : events) {
            var finalWeight = applyDecay(event.getWeight(), event.getCreatedAt());
            categoryWeights.merge(event.getCategoryId(), finalWeight, Double::sum);
        }

        var json = mapper.writeValueAsString(categoryWeights);

        var entity = RECOMMENDATION_MAPPER.buildEntity(userId, json);

        recommendationRepository.save(entity);
    }
}