package az.ingress.service.abstraction;

public interface CategoryBasedRecommendationAggregatorService {

    void createOrUpdateUserDetails(Long userId, Long categoryId);
}
