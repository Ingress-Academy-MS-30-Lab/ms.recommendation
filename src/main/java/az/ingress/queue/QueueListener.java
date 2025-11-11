package az.ingress.queue;

import az.ingress.exception.ErrorMessage;
import az.ingress.exception.QueueException;
import az.ingress.model.events.CartEvent;
import az.ingress.model.events.OrderEvent;
import az.ingress.service.QueueService;
import az.ingress.service.abstraction.RecommendationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static az.ingress.exception.ErrorMessage.QUEUE_ERROR;

@Slf4j
@Component
@RequiredArgsConstructor
public class QueueListener {

    private final ObjectMapper objectMapper;
    private final RecommendationService recommendationService;
    private final QueueService queueService;

    @RabbitListener(queues = "${rabbitmq.publisher-service.cart.queue}")
    public void consume(String message) {
        try {
            var cart = objectMapper.readValue(message, CartEvent.class);
            queueService.processCartEvent(cart);
        }catch(JsonProcessingException ex) {
            log.error("ActionLog.consume.error.message invalid format - {}", ex.getMessage());
        }catch (Exception ex) {
            throw new QueueException(QUEUE_ERROR.getValue());
        }
    }

    @RabbitListener(queues = "${rabbitmq.publisher-service.order.queue}")
    public void consumeOrder(String message) {
        try{
            var order = objectMapper.readValue(message, OrderEvent.class);
            queueService.processOrderEvent(order);
        }catch(JsonProcessingException ex) {
            log.error("ActionLog.consume.error.message invalid format - {}", ex.getMessage());
        }catch (Exception ex) {
            throw new QueueException(QUEUE_ERROR.getValue());
        }
    }
}
