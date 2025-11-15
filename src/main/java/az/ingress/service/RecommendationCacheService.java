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
    private static final String PREFIX = "ms-recommendation:";

    public List<ProductResponseDto> getCachedRecommendationProductsByCategory(String category) {
        var key = PREFIX + "recommendations:" + category;
        return cacheUtil.getBucket(key);
    }

    public void save(String category, List<ProductResponseDto> products) {
        var key = PREFIX + "recommendations:" + category;
        cacheUtil.saveToCache(key, products, 24L, HOURS);
    }

    public List<ProductResponseDto> getCachedRecommendationTopRatedProducts() {
        var key = PREFIX + "recommendations:top-rated";
        return cacheUtil.getBucket(key);
    }

    public void save(List<ProductResponseDto> products) {
        var key = PREFIX + "recommendations:top-rated";
        cacheUtil.saveToCache(key, products, 24L, HOURS);
    }
}
