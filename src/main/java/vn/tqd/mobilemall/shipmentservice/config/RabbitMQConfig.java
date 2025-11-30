package vn.tqd.mobilemall.shipmentservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${queue.mall-shipments.queue}")
    private String queueName;

    @Value("${queue.mall-shipments.exchange}")
    private String exchangeName;

    @Value("${queue.mall-shipments.routing-key}")
    private String routingKey;

    // 1. Tạo Queue
    @Bean
    public Queue shipmentQueue() {
        return new Queue(queueName, true);
    }

    // 2. Khai báo Exchange (Để đảm bảo nó tồn tại)
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchangeName);
    }

    // 3. Gắn Queue vào Exchange
    @Bean
    public Binding binding(Queue shipmentQueue, DirectExchange exchange) {
        return BindingBuilder.bind(shipmentQueue).to(exchange).with(routingKey);
    }

    // 4. Converter để đọc JSON
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

//import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitMQConfig {
//    @Bean
//    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
//            ConnectionFactory connectionFactory) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConcurrentConsumers(3);
//        factory.setMaxConcurrentConsumers(3);
//        factory.setConnectionFactory(connectionFactory);
//        factory.setMessageConverter(new Jackson2JsonMessageConverter());
//        return factory;
//    }
//}