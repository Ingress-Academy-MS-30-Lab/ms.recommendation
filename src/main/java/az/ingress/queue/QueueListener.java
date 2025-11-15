package az.ingress.queue;

import az.ingress.exception.QueueException;
import az.ingress.logger.ApplicationLogger;
import az.ingress.model.queue.RecommendationQueueDto;
import az.ingress.service.QueueService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueListener {

    private final ApplicationLogger log = ApplicationLogger.getLogger(QueueService.class);

    private final ObjectMapper objectMapper;
    private final QueueService queueService;

    @RabbitListener(queues = "${rabbitmq.publisher-service.queue}")
    public void consume(String message) {
        try {
            var queueDto = objectMapper.readValue(message, RecommendationQueueDto.class);
            queueService.queueProcess(queueDto);
        } catch (JsonProcessingException ex) {
            log.error("ActionLog.consume.error.message invalid format - {}", ex.getMessage());
        } catch (Exception ex) {
            throw new QueueException();
        }
    }
}
