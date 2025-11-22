package az.ingress.controller;

import az.ingress.model.client.response.ProductResponseDto;
import az.ingress.service.abstraction.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private static final String USER_ID = "User-Id";
    private final RecommendationService recommendationService;

    @GetMapping
    public List<ProductResponseDto> getRecommendationProducts(@RequestHeader(USER_ID) Long userId) {
        return recommendationService.getRecommendationProducts(userId);
    }
}
