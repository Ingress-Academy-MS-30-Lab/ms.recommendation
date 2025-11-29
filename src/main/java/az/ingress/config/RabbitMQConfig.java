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

    private final String recommendationQ;
    private final String recommendationDLQ;
    private final String recommendationQExchange;
    private final String recommendationDLQExchange;
    private final String recommendationQKey;
    private final String recommendationDLQKey;

    public RabbitMQConfig(@Value("${rabbitmq.publisher-service.queue}") String recommendationQ,
                          @Value("${rabbitmq.publisher-service.dlq}") String recommendationDLQ) {
        this.recommendationQ = recommendationQ;
        this.recommendationDLQ = recommendationDLQ;
        this.recommendationQExchange = recommendationQ + "_EXCHANGE";
        this.recommendationDLQExchange = recommendationDLQ + "_EXCHANGE";
        this.recommendationQKey = recommendationQ + "_KEY";
        this.recommendationDLQKey = recommendationDLQ + "_KEY";
    }

    @Bean
    public DirectExchange recommendationQExchange() {
        return new DirectExchange(recommendationQExchange);
    }

    @Bean
    public DirectExchange recommendationDLQExchange() {
        return new DirectExchange(recommendationDLQExchange);
    }

    @Bean
    public Queue recommendationDLQ() {
        return QueueBuilder.durable(recommendationDLQ).build();
    }

    @Bean
    public Queue recommendationQ() {
        return QueueBuilder.durable(recommendationQ)
                .withArgument("x-dead-letter-exchange", recommendationDLQExchange)
                .withArgument("x-dead-letter-routing-key", recommendationDLQKey)
                .build();
    }

    @Bean
    public Binding recommendationDLQBinding() {
        return BindingBuilder.bind(recommendationDLQ())
                .to(recommendationDLQExchange())
                .with(recommendationDLQKey);
    }

    @Bean
    public Binding recommendationQBinding() {
        return BindingBuilder.bind(recommendationQ())
                .to(recommendationQExchange())
                .with(recommendationQKey);
    }
}
