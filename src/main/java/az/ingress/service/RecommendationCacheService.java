package az.ingress.service;

import az.ingress.aop.AspectLogging;
import az.ingress.logger.ApplicationLogger;
import az.ingress.model.client.response.ProductResponseDto;
import az.ingress.util.CacheUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.ingress.model.constants.CacheConstants.EXPIRE_TIME;
import static az.ingress.model.constants.CacheConstants.PREFIX_CATEGORY;
import static az.ingress.model.constants.CacheConstants.PREFIX_CATEGORY_TOP_RATED;
import static java.time.temporal.ChronoUnit.HOURS;

@Service
@RequiredArgsConstructor
public class RecommendationCacheService {

    private final CacheUtil cacheUtil;
    private final ApplicationLogger log = ApplicationLogger.getLogger(AspectLogging.class);

    public List<ProductResponseDto> getCachedRecommendationProductsByCategory(Long categoryId) {
        var key = PREFIX_CATEGORY + categoryId;
        try {
            return cacheUtil.getBucket(key);
        } catch (Exception e) {
            log.warn("ActionLog.getCachedRecommendationProductsByCategory.failed - {}", categoryId);
            return null;
        }
    }

    public void save(Long categoryId, List<ProductResponseDto> products) {
        var key = PREFIX_CATEGORY + categoryId;
        try {
            cacheUtil.saveToCache(key, products, EXPIRE_TIME, HOURS);
        } catch (Exception e) {
            log.warn("ActionLog.save.failed");
        }
    }

    public List<ProductResponseDto> getCachedRecommendationTopRatedProducts() {
        try {
            return cacheUtil.getBucket(PREFIX_CATEGORY_TOP_RATED);
        } catch (Exception e) {
            log.warn("ActionLog.getCachedRecommendationTopRatedProducts.failed");
            return null;
        }
    }

    public void save(List<ProductResponseDto> products) {
        try {
            cacheUtil.saveToCache(PREFIX_CATEGORY_TOP_RATED, products, EXPIRE_TIME, HOURS);
        } catch (Exception e) {
            log.warn("ActionLog.save.failed");
        }
    }
}
