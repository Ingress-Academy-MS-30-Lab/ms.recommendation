package az.ingress.service

import az.ingress.dao.entity.RecommendationEntity
import az.ingress.dao.repository.RecommendationRepository
import az.ingress.model.enums.RecommendationSourceType
import az.ingress.model.events.CartEvent
import az.ingress.model.events.OrderEvent
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification
import java.time.LocalDateTime
import az.ingress.model.mapper.RecommendationMapper

import static az.ingress.model.enums.RecommendationSourceType.CART
import static az.ingress.model.enums.RecommendationSourceType.ORDER
import static az.ingress.model.enums.RecommendationSourceType.ORDER
import static az.ingress.model.mapper.RecommendationMapper.RECOMMENDATION_MAPPER
import static az.ingress.model.mapper.RecommendationMapper.RECOMMENDATION_MAPPER

class QueueServiceTest extends Specification {


    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()
    RecommendationRepository recommendationRepository
    QueueService queueService

    def setup() {
        recommendationRepository = Mock()
        queueService = new QueueService(recommendationRepository)
    }

    def "TestProcessCartEvent success case when entity exist and update this value"() {
        given:
        def entity = new RecommendationEntity(
                userId: 10L,
                category: "old-category",
                updatedAt: LocalDateTime.now().minusMinutes(5)
        )
        def event = new CartEvent(10L, "new-category", LocalDateTime.now())

        when:
        queueService.processCartEvent(event)

        then:
        1 * recommendationRepository.findByUserId(10L) >> entity
        1 * recommendationRepository.save({
            it.category == "new-category" &&
                    it.sourceType == CART
        })
    }

    def "TestProcessCartEvent success case when entity exist and does not update this value"() {
        given:
        def entity = new RecommendationEntity(
                userId: 10L,
                category: "old-category",
                updatedAt: LocalDateTime.now()
        )
        def event = new CartEvent(10L, "new-category", LocalDateTime.now().minusMinutes(5L))

        when:
        queueService.processCartEvent(event)

        then:
        1 * recommendationRepository.findByUserId(10L) >> entity
        0 * recommendationRepository.save(_)
    }

    def "TestProcessCartEvent should create new entity when not entity does not exists with userId"() {
        given:
        def event = new CartEvent(20L, "electronics", LocalDateTime.now())
        def mappedEntity = new RecommendationEntity(
                userId: 20L,
                category: "electronics",
                sourceType: CART
        )

        when:
        RECOMMENDATION_MAPPER.buildEntity(event) >> mappedEntity

        queueService.processCartEvent(event)

        then:
        1 * recommendationRepository.findByUserId(20L) >> null
        1 * recommendationRepository.save({
            it.userId == 20L &&
            it.category == "electronics" &&
            it.sourceType == CART
        })
    }

    def "TestProcessOrderEvent success case when entity exist and update this value"() {
        given:
        def event = new OrderEvent(1L, "books", LocalDateTime.now())
        def entity = new RecommendationEntity(
                "userId": 1L,
                "category": "glasses",
                "updatedAt": LocalDateTime.now().minusMinutes(10L)
        )

        when:
        queueService.processOrderEvent(event)

        then:
        1 * recommendationRepository.findByUserId(1L) >> entity
        1 * recommendationRepository.save({
            it.category == "books" && it.sourceType == ORDER
        })
    }

    def "TestProcessOrderEvent success case when entity exist but does not update this value"() {
        given:
        def event = new OrderEvent(1L, "books", LocalDateTime.now().minusMinutes(5L))
        def entity = new RecommendationEntity(
                "userId": 1L,
                "category": "glasses",
                "updatedAt": LocalDateTime.now()
        )
        when:
        queueService.processOrderEvent(event)

        then:
        1 * recommendationRepository.findByUserId(1L) >> entity
        0 * recommendationRepository.save(_)
    }

    def "TestProcessOrderEvent should create new entity when not entity does not exists with userId"() {
        given:
        def event = new OrderEvent(1L, "books", LocalDateTime.now())
        def mappedEntity = new RecommendationEntity(
                "userId": 1L,
                "category": "books",
                "sourceType": ORDER
        )

        when:
        RECOMMENDATION_MAPPER.buildEntity(event) >> mappedEntity
        queueService.processOrderEvent(event)

        then:
        1 * recommendationRepository.findByUserId(1L) >> null
        1 * recommendationRepository.save({
            it.userId == 1L && it.category == "books" && it.sourceType == ORDER
        })

    }

}
