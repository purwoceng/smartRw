package com.codean.smart_rw.messaging;

import com.codean.smart_rw.model.dto.NotifikasiMessage;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    private static final Logger log = LogManager.getLogger(NotificationProducer.class);

    public void publish(NotifikasiMessage message){
        log.info("Publising notifikasi ke queue untuk userid={},jenis={}",message.getUserId(),message.getJenis());
    }
}
