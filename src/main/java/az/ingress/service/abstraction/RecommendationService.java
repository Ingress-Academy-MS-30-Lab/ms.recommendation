package az.ingress.service.abstraction;

import az.ingress.model.client.response.ProductResponseDto;

import java.util.List;

public interface RecommendationService {

    List<ProductResponseDto> getRecommendationProducts(Long userId);

    void refreshRecommendationProducts();
}
