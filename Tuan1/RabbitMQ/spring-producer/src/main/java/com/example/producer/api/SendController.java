package com.example.producer.api;

import com.example.producer.model.SendRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SendController {

    private final RabbitTemplate rabbitTemplate;
    private final String orderQueue;

    public SendController(RabbitTemplate rabbitTemplate,
                          @Value("${app.queues.order}") String orderQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.orderQueue = orderQueue;
    }

    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> send(@Valid @RequestBody SendRequest request) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("message", request.getMessage());
        payload.put("orderId", request.getOrderId());
        payload.put("timestamp", Instant.now().toString());

        rabbitTemplate.convertAndSend("", orderQueue, payload);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "sent");
        response.put("dataSent", payload);
        return ResponseEntity.ok(response);
    }
}
