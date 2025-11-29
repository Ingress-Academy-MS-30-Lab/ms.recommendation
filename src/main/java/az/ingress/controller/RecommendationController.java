package az.ingress.controller;

import az.ingress.model.client.response.ProductResponseDto;
import az.ingress.service.abstraction.CategoryBasedRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static az.ingress.model.constants.HeaderConstants.USER_ID;

@RestController
@RequestMapping("/v1/product-recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final CategoryBasedRecommendationService categoryBasedRecommendationService;

    @GetMapping
    public List<ProductResponseDto> getRecommendedProducts(@RequestHeader(USER_ID) Long userId) {
        return categoryBasedRecommendationService.getRecommendedProducts(userId);
    }
}
