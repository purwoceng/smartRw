package com.codean.smart_rw.service.impl;

import com.codean.smart_rw.service.TelegramService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TelegramServiceImpl implements TelegramService {
    @Value("${telegram.bot.token}")
    private String botToken;

    private static final String TELEGRAM_URL = "https://api.telegram.org/bot%s/sendMessage";

    private static final String TELEGRAM_URL_PHOTO = "https://api.telegram.org/bot%s/sendPhoto";

    private static final Logger log = LogManager.getLogger(TelegramServiceImpl.class);

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String sendMessage(String chatId,String message){
        try{
            String url = String.format(TELEGRAM_URL,botToken);
            String cleanChatId = chatId.trim();

            Map<String,Object> body = new HashMap<>();
            body.put("chat_id",cleanChatId);
            body.put("text",message);
            String response = restTemplate.postForObject(url,body, String.class);
            return response;
        }catch (Exception e){
            log.error("Error when send message.", e);
            throw e;
        }
    }

    @Override
    public String sendPhoto(String chatId,String photo, String caption){
        try{
            String url = String.format(TELEGRAM_URL_PHOTO,botToken);
            String cleanChatId = chatId.trim();

            Map<String,Object> body = new HashMap<>();
            body.put("chat_id",cleanChatId);
            body.put("photo",photo);
            body.put("caption",caption);
            String response = restTemplate.postForObject(url,body, String.class);
            return response;
        } catch (Exception e) {
            log.error("error when send message ",e);
            throw e;
        }
    }

    @Override
    public boolean isChatIdValid(String chatId) {
        try {
            String url = String.format(
                    "https://api.telegram.org/bot%s/getChat?chat_id=%s",
                    botToken, chatId
            );

            restTemplate.getForObject(url, String.class);
            return true;
        } catch (HttpClientErrorException e) {
            return false;
        }
    }

}
