package com.codean.smart_rw.service;

public interface TelegramService {
    String sendMessage(String chatId,String message);

    String sendPhoto(String chatId,String photo, String caption);

    boolean isChatIdValid(String chatId);
}
