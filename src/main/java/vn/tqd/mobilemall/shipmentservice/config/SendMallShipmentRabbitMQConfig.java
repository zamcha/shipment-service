package vn.tqd.mobilemall.shipmentservice.config;//package vn.tqd.mobilemall.mallservice.config;
//
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//@Configuration
//public class SendMallShipmentRabbitMQConfig {
//    @Value("${queue.mall-shipments.routing-key}")
//    private String routingKey;
//
//    @Value("${queue.mall-shipments.queue}")
//    private String queueName;
//
//    @Value("${queue.mall-shipments.exchange}")
//    private String exchangeName;
//
////    @Value("${queue.mall-shipments.exchange}")
////    private String exchange;
////
////    // 1. Tạo Exchange (Nếu chưa có)
////    @Bean
////    public DirectExchange exchange() {
////        return new DirectExchange(exchange);
////    }
////
////    // 2. Cấu hình gửi JSON (Bắt buộc để bên kia đọc được)
////    @Bean
////    public MessageConverter jsonMessageConverter() {
////        return new Jackson2JsonMessageConverter();
////    }
//    @Bean
//    DirectExchange sendExchange() {
//        return new DirectExchange(exchangeName);
//    }
//
//    @Bean
//    public Queue sendQueue() {
//        return QueueBuilder.durable(queueName).build();
//    }
//
//    @Bean
//    Binding mallQueueBinding(Queue sendEmailQueue,
//                              DirectExchange sendEmailExchange) {
//        return BindingBuilder
//                .bind(sendEmailQueue)
//                .to(sendEmailExchange)
//                .with(routingKey);
//    }
//}
//
//
