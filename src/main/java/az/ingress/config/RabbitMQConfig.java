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

    //cart
    private final String publisherCartQ;
    private final String publisherCartDLQ;
    private final String publisherCartQExchange;
    private final String publisherCartDLQExchange;
    private final String publisherCartQKey;
    private final String publisherCartDLQKey;

    //order
    private final String publisherOrderQ;
    private final String publisherOrderDLQ;
    private final String publisherOrderQExchange;
    private final String publisherOrderDLQExchange;
    private final String publisherOrderQKey;
    private final String publisherOrderDLQKey;

    public RabbitMQConfig(@Value("${rabbitmq.publisher-service.cart.queue}") String publisherCartQ,
                          @Value("${rabbitmq.publisher-service.cart.dlq}") String publisherCartDLQ,
                          @Value("${rabbitmq.publisher-service.order.queue}") String publisherOrderQ,
                          @Value("${rabbitmq.publisher-service.order.dlq}") String publisherOrderDLQ) {
        this.publisherCartQ = publisherCartQ;
        this.publisherCartDLQ = publisherCartDLQ;
        this.publisherCartQExchange = publisherCartQ + "_EXCHANGE";
        this.publisherCartDLQExchange = publisherCartDLQ + "_EXCHANGE";
        this.publisherCartQKey = publisherCartQ + "_KEY";
        this.publisherCartDLQKey = publisherCartDLQ + "_KEY";

        this.publisherOrderQ = publisherOrderQ;
        this.publisherOrderDLQ = publisherOrderDLQ;
        this.publisherOrderQExchange = publisherOrderQ + "_EXCHANGE";
        this.publisherOrderDLQExchange = publisherOrderDLQ + "_EXCHANGE";
        this.publisherOrderQKey = publisherOrderQ + "_KEY";
        this.publisherOrderDLQKey = publisherOrderDLQ + "_KEY";
    }


    //cart
    @Bean
    DirectExchange publisherCartQExchange() {
        return new DirectExchange(publisherCartQExchange);
    }

    @Bean
    DirectExchange publisherCartDLQExchange() {
        return new DirectExchange(publisherCartDLQExchange);
    }

    @Bean
    Queue publisherCartDLQ() {
        return QueueBuilder.durable(publisherCartDLQ).build();
    }

    @Bean
    Queue publisherCartQ() {
        return QueueBuilder.durable(publisherCartQ)
                .withArgument("x-dead-letter-exchange", publisherCartDLQExchange)
                .withArgument("x-dead-letter-routing-key", publisherCartDLQKey)
                .build();
    }

    @Bean
    Binding publisherCartDLQBinding() {
        return BindingBuilder.bind(publisherCartDLQ())
                .to(publisherCartDLQExchange())
                .with(publisherCartDLQKey);
    }

    @Bean
    Binding publisherCartQBinding() {
        return BindingBuilder.bind(publisherCartQ())
                .to(publisherCartQExchange())
                .with(publisherCartQKey);
    }


    //order
    @Bean
    DirectExchange publisherOrderQExchange() {
        return new DirectExchange(publisherOrderQExchange);
    }

    @Bean
    DirectExchange publisherOrderDLQExchange() {
        return new DirectExchange(publisherOrderDLQExchange);
    }

    @Bean
    Queue publisherOrderDLQ() {
        return QueueBuilder.durable(publisherOrderDLQ).build();
    }

    @Bean
    Queue publisherOrderQ() {
        return QueueBuilder.durable(publisherOrderQ)
                .withArgument("x-dead-letter-exchange", publisherOrderDLQExchange)
                .withArgument("x-dead-letter-routing-key", publisherOrderDLQKey)
                .build();
    }

    @Bean
    Binding publisherOrderDLQBinding() {
        return BindingBuilder.bind(publisherOrderDLQ())
                .to(publisherOrderDLQExchange())
                .with(publisherOrderDLQKey);
    }

    @Bean
    Binding publisherOrderQBinding() {
        return BindingBuilder.bind(publisherOrderQ())
                .to(publisherOrderQExchange())
                .with(publisherOrderQKey);
    }
}
