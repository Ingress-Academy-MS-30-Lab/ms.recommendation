package az.ingress.service

import az.ingress.client.ProductClient
import az.ingress.dao.repository.RecommendationRepository
import az.ingress.model.response.ProductResponse
import az.ingress.service.abstraction.RecommendationService
import az.ingress.service.concrete.RecommendationServiceImpl
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

class RecommendationServiceTest extends Specification{

    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()
    RecommendationRepository recommendationRepository
    ProductClient productClient
    RecommendationCacheService recommendationCacheService
    RecommendationService recommendationService

    def setup() {
        recommendationRepository = Mock()
        recommendationCacheService = Mock()
        productClient = Mock()
        recommendationService = new RecommendationServiceImpl(recommendationRepository, productClient, recommendationCacheService)
    }

    def "TestGetProducts success case when user exist and get product from cache" () {
        given:
        def userId = random.nextObject(Long)
        def category = "trousers"
        def cachedProducts = random.objects(ProductResponse, 10).toList()

        when:
        def actual = recommendationService.getProducts(userId)

        then:
        1 * recommendationRepository.existsByUserId(userId) >> true
        1 * recommendationRepository.findCategoryByUserId(userId) >> category
        1 * recommendationCacheService.getCachedRecommendationProductsByCategory(category) >> cachedProducts
        0 * productClient.getTopProductsByCategory(_)
        0 * recommendationCacheService.save(category, cachedProducts)

        actual == cachedProducts
    }

    def "TestGetProducts success case when user exist but get products from client"() {
        given:
        def userId = random.nextObject(Long)
        def category = random.nextObject(String)
        def productsFromClient = random.objects(ProductResponse, 10).toList()

        when:
        def actual = recommendationService.getProducts(userId)

        then:
        1 * recommendationRepository.existsByUserId(userId) >> true
        1 * recommendationRepository.findCategoryByUserId(userId) >> category
        1 * recommendationCacheService.getCachedRecommendationProductsByCategory(category) >> null
        1 * productClient.getTopProductsByCategory(category) >> productsFromClient
        1 * recommendationCacheService.save(category, productsFromClient)

        actual == productsFromClient
    }

    def "TestGetProducts success case when user does not exist and get products from cache"() {
        given:
        def userId = random.nextObject(Long)
        def cacheTopRatedProducts = random.objects(ProductResponse, 10).toList()

        when:
        def actual = recommendationService.getProducts(userId)

        then:
        1 * recommendationRepository.existsByUserId(userId) >> false
        1 * recommendationCacheService.getCachedRecommendationTopRatedProducts() >> cacheTopRatedProducts
        0 * productClient.getTopProductsByCategory(_)
        0 * recommendationCacheService.save(cacheTopRatedProducts)

        actual == cacheTopRatedProducts
    }

    def "TestGetProducts success case when user does not exist and get products from client"() {
        given:
        def userId = random.nextObject(Long)
        def productsFromClient = random.objects(ProductResponse, 10).toList()

        when:
        def actual = recommendationService.getProducts(userId)

        then:
        1 * recommendationRepository.existsByUserId(userId) >> false
        1 * recommendationCacheService.getCachedRecommendationTopRatedProducts() >> null
        1 * productClient.getMostRatedProducts() >> productsFromClient
        1 * recommendationCacheService.save(productsFromClient)

        actual == productsFromClient
    }

    def "TestRefreshRecommendationProducts success case"() {
        given:
        def categories = ["glasses", "books", "T-shirt"]
        def glassesProducts = random.objects(ProductResponse, 10).toList()
        def booksProducts = random.objects(ProductResponse, 10).toList()
        def tshirtProducts = random.objects(ProductResponse, 10).toList()
        def topRatedProducts = random.objects(ProductResponse, 10).toList()

        when:
        recommendationService.refreshRecommendationProducts()

        then:
        1 * recommendationRepository.findAllDistinctCategory() >> categories
        1 * productClient.getTopProductsByCategory("glasses") >> glassesProducts
        1 * recommendationCacheService.save("glasses", glassesProducts)
        1 * productClient.getTopProductsByCategory("books") >> booksProducts
        1 * recommendationCacheService.save("books", booksProducts)
        1 * productClient.getTopProductsByCategory("T-shirt") >> tshirtProducts
        1 * recommendationCacheService.save("T-shirt", tshirtProducts)
        1 * productClient.getMostRatedProducts() >> topRatedProducts
        1 * recommendationCacheService.save(topRatedProducts)
    }
}
