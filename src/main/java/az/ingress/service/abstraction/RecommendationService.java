package az.ingress.service.abstraction;

import az.ingress.model.response.ProductResponse;

import java.util.List;

public interface RecommendationService {

    List<ProductResponse> getProducts(Long userId);

    void refreshRecommendationProducts();
}
