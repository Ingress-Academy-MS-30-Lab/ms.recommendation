package az.ingress.service.concrete;

import az.ingress.aop.Log;
import az.ingress.client.ProductClient;
import az.ingress.dao.repository.RecommendationRepository;
import az.ingress.model.client.response.ProductResponseDto;
import az.ingress.service.RecommendationCacheService;
import az.ingress.service.abstraction.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final ProductClient productClient;
    private final RecommendationCacheService recommendationCacheService;




    @Override
    public List<ProductResponseDto> getRecommendationProducts(Long userId) {
        var checkUserIdIfExist = recommendationRepository.existsByUserId(userId);
        if (checkUserIdIfExist) {
            var category = recommendationRepository.findCategoryByUserId(userId);
            var cachedRecommendationProducts = recommendationCacheService.getCachedRecommendationProductsByCategory(category);
            if (cachedRecommendationProducts != null) return cachedRecommendationProducts;

            var recommendationProducts = productClient.getTopProductsByCategory(category);
            recommendationCacheService.save(category, recommendationProducts);
            return recommendationProducts;
        } else {
            var cachedRecommendationTopRatedProducts = recommendationCacheService.getCachedRecommendationTopRatedProducts();
            if (cachedRecommendationTopRatedProducts != null) return cachedRecommendationTopRatedProducts;

            var topRatedProducts = productClient.getMostRatedProducts();
            recommendationCacheService.save(topRatedProducts);
            return topRatedProducts;
        }
    }

    @Override
    public void refreshRecommendationProducts() {
        var allDistinctCategory = recommendationRepository.findAllDistinctCategory();

        for(var category : allDistinctCategory) {
            var recommendationProductsByCategory = productClient.getTopProductsByCategory(category);
            recommendationCacheService.save(category, recommendationProductsByCategory);
        }

        var recommendationTopRatedProducts = productClient.getMostRatedProducts();
        recommendationCacheService.save(recommendationTopRatedProducts);
    }
}
