package az.ingress.controller

import az.ingress.exception.ErrorHandler
import az.ingress.model.response.ProductResponse
import az.ingress.service.abstraction.RecommendationService
import az.ingress.service.concrete.RecommendationServiceImpl
import org.mockito.Mock
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class RecommendationControllerTest extends Specification {

    RecommendationService recommendationService
    RecommendationController recommendationController
    MockMvc mockMvc

    def setup() {
        recommendationService = Mock(RecommendationServiceImpl)
        recommendationController = new RecommendationController(recommendationService)
        mockMvc = MockMvcBuilders.standaloneSetup(recommendationController)
                .setControllerAdvice(new ErrorHandler())
                .build()
    }

    def "TestGetRecommendationProducts"() {
        given:
        def userId = 1L;
        def url = "/v1/recommendations/${userId}"
        def products = [
                new ProductResponse(
                        1L, 11L, 101L, "Electronics", "image1.jpg",
                        "iPhone 15", "Latest model", BigDecimal.valueOf(2500),
                        BigDecimal.valueOf(2300), true, 4.9, 1200L
                ),
                new ProductResponse(
                        2L, 22L, 202L, "Computers", "image2.jpg",
                        "MacBook Pro", "M3 chip", BigDecimal.valueOf(4500),
                        null, false, 4.8, 900L
                )
        ]

        recommendationService.getProducts(userId) >> products

        when:
        def actual = mockMvc.perform (get(url))

        then:
        actual.andExpect(status().isOk())
                .andExpect(jsonPath('$.length()').value(2))
                .andExpect(jsonPath('$[0].productId').value(1))
                .andExpect(jsonPath('$[0].title').value("iPhone 15"))
                .andExpect(jsonPath('$[0].onSale').value(true))
                .andExpect(jsonPath('$[1].productId').value(2))
                .andExpect(jsonPath('$[1].categoryName').value("Computers"))
                .andExpect(jsonPath('$[1].rating').value(4.8))
    }
}
