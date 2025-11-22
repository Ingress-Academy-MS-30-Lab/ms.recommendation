package az.ingress.scheduler;

import az.ingress.service.abstraction.RecommendationService;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecommendationScheduler {
    private final RecommendationService recommendationService;

    @Scheduled(cron = "0 0 0 * * *")
    @SchedulerLock(name = "refreshRecommendationProducts",
                   lockAtLeastFor = "PT5M",
                   lockAtMostFor = "PT10M")
    public void refreshRecommendationProducts() {
//        recommendationService.refreshRecommendationProducts();
    }
}
