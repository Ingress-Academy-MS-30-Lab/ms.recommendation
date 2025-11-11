package az.ingress.service;

import az.ingress.model.response.ProductResponse;
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

    public List<ProductResponse> getCachedRecommendationProductsByCategory(String category) {
        var key = PREFIX + "recommendations:" + category;
        return cacheUtil.getBucket(key);
    }

    public void save(String category, List<ProductResponse> products) {
        var key = PREFIX + "recommendations:" + category;
        cacheUtil.saveToCache(key, products, 24L, HOURS);
    }

    public List<ProductResponse> getCachedRecommendationTopRatedProducts() {
        var key = PREFIX + "recommendations:top-rated";
        return cacheUtil.getBucket(key);
    }

    public void save(List<ProductResponse> products) {
        var key = PREFIX + "recommendations:top-rated";
        cacheUtil.saveToCache(key, products, 24L, HOURS);
    }
}
