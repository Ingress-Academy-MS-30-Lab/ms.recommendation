package az.ingress.scheduler;

import az.ingress.service.abstraction.CategoryBasedRecommendationService;
import az.ingress.service.abstraction.RecommendationEventService;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecommendationEventScheduler {
    private final RecommendationEventService recommendationEventService;

    @Scheduled(cron = "0 0 1 * * *")
    @SchedulerLock(name = "refreshRecommendationProducts",
                   lockAtLeastFor = "PT1M",
                   lockAtMostFor = "PT5M")
    public void refreshRecommendationProducts() {
        recommendationEventService.cleanupOldEvents();
    }
}
