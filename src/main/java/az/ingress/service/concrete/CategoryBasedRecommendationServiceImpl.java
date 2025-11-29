
package az.ingress.service.concrete;

import az.ingress.aop.Log;
import az.ingress.client.ProductClient;
import az.ingress.dao.entity.CategoryBasedRecommendationEntity;
import az.ingress.dao.repository.CategoryBasedRecommendationRepository;
import az.ingress.model.client.response.ProductResponseDto;
import az.ingress.service.RecommendationCacheService;
import az.ingress.service.abstraction.CategoryBasedRecommendationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;

@Service
@RequiredArgsConstructor
@Log
public class CategoryBasedRecommendationServiceImpl implements CategoryBasedRecommendationService {

    private final CategoryBasedRecommendationRepository categoryBasedRecommendationRepository;
    private final ProductClient productClient;
    private final RecommendationCacheService recommendationCacheService;


    @Override
    public List<ProductResponseDto> getRecommendedProducts(Long userId) {

        var entity = categoryBasedRecommendationRepository.findAllByUserId(userId);

        if (entity == null) {
            var cachedTopRatedProducts = recommendationCacheService.getCachedRecommendationTopRatedProducts();
            if (cachedTopRatedProducts != null) return cachedTopRatedProducts;

            var topRatedProducts = productClient.getTopProductsByCategory(null);
            recommendationCacheService.save(topRatedProducts);
            return topRatedProducts;
        }

        var bestCategory = entity.stream()
                .max(comparing(CategoryBasedRecommendationEntity::getWeight))
                .map(CategoryBasedRecommendationEntity::getCategoryId)
                .orElse(null);

        if (bestCategory == null) {
            var cachedTopRatedProducts = recommendationCacheService.getCachedRecommendationTopRatedProducts();
            if (cachedTopRatedProducts != null) return cachedTopRatedProducts;

            var topRatedProducts = productClient.getTopProductsByCategory(null);
            recommendationCacheService.save(topRatedProducts);
            return topRatedProducts;
        }

        var cached = recommendationCacheService.getCachedRecommendationProductsByCategory(bestCategory);
        if (cached != null) return cached;

        var products = productClient.getTopProductsByCategory(bestCategory);
        recommendationCacheService.save(bestCategory, products);
        return products;
    }

    @Override
    public Optional<CategoryBasedRecommendationEntity> findByUserIdAndCategoryId(Long userId, Long categoryId) {
        return categoryBasedRecommendationRepository.findByUserIdAndCategoryId(userId, categoryId);
    }

    @Override
    public void save(CategoryBasedRecommendationEntity categoryBasedRecommendationEntity) {
        categoryBasedRecommendationRepository.save(categoryBasedRecommendationEntity);
    }
}
