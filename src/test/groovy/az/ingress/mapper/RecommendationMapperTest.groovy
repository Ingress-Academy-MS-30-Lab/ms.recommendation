package az.ingress.mapper

import az.ingress.dao.entity.RecommendationEntity
import az.ingress.model.enums.RecommendationSourceType
import az.ingress.model.events.CartEvent
import az.ingress.model.events.OrderEvent
import az.ingress.model.mapper.RecommendationMapper
import az.ingress.model.request.RecommendationRequest
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

import static az.ingress.model.enums.RecommendationSourceType.CART
import static az.ingress.model.enums.RecommendationSourceType.ORDER
import static az.ingress.model.mapper.RecommendationMapper.RECOMMENDATION_MAPPER
import static az.ingress.model.mapper.RecommendationMapper.RECOMMENDATION_MAPPER

class RecommendationMapperTest extends Specification{

    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "buildEntity"() {
        given:
        def recommendationRequest = random.nextObject(RecommendationRequest)

        when:
        def entity = RECOMMENDATION_MAPPER.buildEntity(recommendationRequest)

        then:
        entity.userId == recommendationRequest.userId
        entity.category == recommendationRequest.category
        entity.sourceType == recommendationRequest.sourceType
        entity.updatedAt == null
        entity.createdAt == null
    }

    def "buildEntity"() {
        given:
        def cartEvent = random.nextObject(CartEvent)

        when:
        def entity = RECOMMENDATION_MAPPER.buildEntity(cartEvent)

        then:
        entity.userId == cartEvent.userId
        entity.category == cartEvent.category
        entity.sourceType == CART
        entity.updatedAt == null
        entity.createdAt == null
    }

    def "buildEntity"() {
        given:
        def orderEvent = random.nextObject(OrderEvent)

        when:
        def entity = RECOMMENDATION_MAPPER.buildEntity(orderEvent)

        then:
        entity.userId == orderEvent.userId
        entity.category == orderEvent.category
        entity.sourceType == ORDER
        entity.updatedAt == null
        entity.createdAt == null
    }

    def "toResponse"() {
        given:
        def entity = random.nextObject(RecommendationEntity)

        when:
        def response = RECOMMENDATION_MAPPER.toResponse(entity)

        then:
        response.userId == entity.userId
        response.category == entity.category
        response.sourceType == entity.sourceType
        response.updatedAt == entity.updatedAt
        response.createdAt == entity.createdAt
    }
}
