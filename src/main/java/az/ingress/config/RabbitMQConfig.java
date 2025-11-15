package az.ingress.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private final String publisherRecommendationQ;
    private final String publisherRecommendationDLQ;
    private final String publisherRecommendationQExchange;
    private final String publisherRecommendationDLQExchange;
    private final String publisherRecommendationQKey;
    private final String publisherRecommendationDLQKey;

    public RabbitMQConfig(@Value("${rabbitmq.publisher-service.queue}") String publisherRecommendationQ,
                          @Value("${rabbitmq.publisher-service.dlq}") String publisherRecommendationDLQ) {
        this.publisherRecommendationQ = publisherRecommendationQ;
        this.publisherRecommendationDLQ = publisherRecommendationDLQ;
        this.publisherRecommendationQExchange = publisherRecommendationQ + "_EXCHANGE";
        this.publisherRecommendationDLQExchange = publisherRecommendationDLQ + "_EXCHANGE";
        this.publisherRecommendationQKey = publisherRecommendationQ + "_KEY";
        this.publisherRecommendationDLQKey = publisherRecommendationDLQ + "_KEY";
    }

    @Bean
    DirectExchange publisherRecommendationQExchange() {
        return new DirectExchange(publisherRecommendationQExchange);
    }

    @Bean
    DirectExchange publisherRecommendationDLQExchange() {
        return new DirectExchange(publisherRecommendationDLQExchange);
    }

    @Bean
    Queue publisherRecommendationDLQ() {
        return QueueBuilder.durable(publisherRecommendationDLQ).build();
    }

    @Bean
    Queue publisherRecommendationQ() {
        return QueueBuilder.durable(publisherRecommendationQ)
                .withArgument("x-dead-letter-exchange", publisherRecommendationDLQExchange)
                .withArgument("x-dead-letter-routing-key", publisherRecommendationDLQKey)
                .build();
    }

    @Bean
    Binding publisherRecommendationDLQBinding() {
        return BindingBuilder.bind(publisherRecommendationDLQ())
                .to(publisherRecommendationDLQExchange())
                .with(publisherRecommendationDLQKey);
    }

    @Bean
    Binding publisherCartQBinding() {
        return BindingBuilder.bind(publisherRecommendationQ())
                .to(publisherRecommendationQExchange())
                .with(publisherRecommendationQKey);
    }
}
