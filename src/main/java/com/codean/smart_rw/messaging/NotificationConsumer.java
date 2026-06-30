package com.codean.smart_rw.messaging;

import com.codean.smart_rw.mapper.LogNotifikasiMapper;
import com.codean.smart_rw.model.dto.NotifikasiMessage;
import com.codean.smart_rw.model.pojo.LogNotifikasiPojo;
import com.codean.smart_rw.service.TelegramService;
import com.codean.smart_rw.util.DateHelper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
@RequiredArgsConstructor
public class NotificationConsumer {
    private final TelegramService telegramService;
    private final LogNotifikasiMapper logNotifikasiMapper;

    private static final Logger log = LogManager.getLogger(NotificationConsumer.class);

    @RabbitListener(queues = "${rabbitmq.queue.notifikasi}")
    public void consume(NotifikasiMessage message){
        log.info("Menerima pesan dari queue: userId={},jenis={}",message.getUserId(),message.getJenis());

        LogNotifikasiPojo logNotif = new LogNotifikasiPojo();
        logNotif.setLogId(UUID.randomUUID().toString());
        logNotif.setUserId(message.getUserId());
        logNotif.setChannel("Telegram");
        logNotif.setJenisNotifikasi(message.getJenis());
        logNotif.setPengumumanId(message.getPengumumanId());
        logNotif.setSentAt(new DateHelper().getCurrentTimestamp());

        try{
            String response;
            if(message.getImageUrl() != null && !message.getImageUrl().isBlank()){
                response = telegramService.sendPhoto(message.getChatId(), message.getImageUrl(), message.getPesan());
            }else {
                response = telegramService.sendMessage(message.getChatId(), message.getPesan());
            }
            logNotif.setStatus("Success");
            logNotif.setResponseTelegram(response);
            log.info("Berhasil kirim notifikasi ke chatId={}",message.getChatId());
        }catch (Exception e){
            log.error("gagal kirim notifikasi ke chatId={}",message.getChatId());
            logNotif.setStatus("Pending");
            logNotif.setResponseTelegram(e.getMessage());
        }

        logNotifikasiMapper.insert(logNotif);
    }
}
