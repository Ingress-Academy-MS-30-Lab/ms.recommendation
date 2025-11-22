
package az.ingress.service.concrete;

import az.ingress.aop.Log;
import az.ingress.client.ProductClient;
import az.ingress.dao.repository.RecommendationRepository;
import az.ingress.model.client.response.ProductResponseDto;
import az.ingress.service.RecommendationCacheService;
import az.ingress.service.abstraction.RecommendationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;

@Service
@RequiredArgsConstructor
@Log
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final ProductClient productClient;
    private final RecommendationCacheService recommendationCacheService;


    @Override
    public List<ProductResponseDto> getRecommendationProducts(Long userId) {
        var entity = recommendationRepository.findById(userId).orElse(null);
        if (entity == null) {
            var cachedTopRatedProducts = recommendationCacheService.getCachedRecommendationTopRatedProducts();
            if (cachedTopRatedProducts != null) return cachedTopRatedProducts;

            var topRatedProducts = productClient.getMostRatedProducts();
            recommendationCacheService.save(topRatedProducts);
            return topRatedProducts;
        }
        Map<String, Double> weights = parseJson(entity.getCategoryWeights());
        String bestCategory = weights.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        if (bestCategory == null) return productClient.getMostRatedProducts();

        var cached = recommendationCacheService.getCachedRecommendationProductsByCategory(bestCategory);
        if (cached != null) return cached;

        var products = productClient.getTopProductsByCategory(bestCategory);
        recommendationCacheService.save(bestCategory, products);
        return products;
    }

//    @Override
//    public void refreshRecommendationProducts() {
//        var allDistinctCategory = recommendationRepository.findAllDistinctCategory();
//
//        for(var category : allDistinctCategory) {
//            var recommendationProductsByCategory = productClient.getTopProductsByCategory(category);
//            recommendationCacheService.save(category, recommendationProductsByCategory);
//        }
//
//        var recommendationTopRatedProducts = productClient.getMostRatedProducts();
//        recommendationCacheService.save(recommendationTopRatedProducts);
//    }

    private Map<String, Double> parseJson(String json) {
        try {
            return new ObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (Exception e) {
            return emptyMap();
        }
    }
}
