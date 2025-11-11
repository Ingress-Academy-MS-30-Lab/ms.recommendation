package az.ingress.controller;

import az.ingress.model.response.ProductResponse;
import az.ingress.service.abstraction.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/{userId}")
    public List<ProductResponse> getRecommendationProducts(@PathVariable Long userId) {
        return recommendationService.getProducts(userId);
    }
}
