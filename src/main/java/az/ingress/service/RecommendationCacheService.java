package az.ingress.service;

import az.ingress.model.client.response.ProductResponseDto;
import az.ingress.util.CacheUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.time.temporal.ChronoUnit.HOURS;

@Service
@RequiredArgsConstructor
public class RecommendationCacheService {

    private final CacheUtil cacheUtil;
    private static final String PREFIX_CATEGORY = "ms-recommendation:recommendations";
    private static final String PREFIX_CATEGORY_TOP_RATED = "ms-recommendation:recommendations:top-rated";

    public List<ProductResponseDto> getCachedRecommendationProductsByCategory(Long categoryId) {
        var key = PREFIX_CATEGORY + categoryId;
        return cacheUtil.getBucket(key);
    }

    public void save(Long categoryId, List<ProductResponseDto> products) {
        var key = PREFIX_CATEGORY + categoryId;
        cacheUtil.saveToCache(key, products, 24L, HOURS);
    }

    public List<ProductResponseDto> getCachedRecommendationTopRatedProducts() {
        return cacheUtil.getBucket(PREFIX_CATEGORY_TOP_RATED);
    }

    public void save(List<ProductResponseDto> products) {
        cacheUtil.saveToCache(PREFIX_CATEGORY_TOP_RATED, products, 24L, HOURS);
    }
}
