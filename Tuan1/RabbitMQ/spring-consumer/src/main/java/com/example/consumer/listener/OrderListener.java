package com.example.consumer.listener;

import com.example.consumer.model.OrderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

@Component
public class OrderListener {

    private static final Logger log = LoggerFactory.getLogger(OrderListener.class);

    @RabbitListener(queues = "${app.queues.order}")
    public void handle(OrderMessage message, Message raw, Channel channel) throws Exception {
        if (message == null || message.getOrderId() == null || message.getOrderId().isBlank()) {
            log.warn("Missing orderId, send to DLQ: {}", message);
            throw new IllegalArgumentException("Missing orderId");
        }

        // Simulate slower processing to observe queue depth
        Thread.sleep(5000);
        log.info("Processed orderId={} message={} (no ack to demonstrate unacked state)", message.getOrderId(), message.getMessage());

        // Intentionally not ack/nack to keep message in unacked state
        // channel.basicAck(raw.getMessageProperties().getDeliveryTag(), false);
    }
}
