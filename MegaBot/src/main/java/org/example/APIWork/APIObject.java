package org.example.APIWork;

import org.example.Bot.MainBotLogic;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Console;

public class APIObject extends TelegramLongPollingBot {
    public MainBotLogic Bot;

    @Override
    public String getBotUsername() {
        return "OOPBotJavaBot";
    }

    @Override
    public String getBotToken() {
        return "7756987157:AAFVeDXqnusdRbPFURxqyN0MI1BD-N2E1Hk";
    }

    @Override
    public void onUpdateReceived(Update update) {
        var msg = update.getMessage();
        var id = msg.getFrom().getId();
        Bot.Think(id, msg.getText());
    }

    public void sendText(Long who, String what){
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what).build();

        try { execute(sm); }
        catch (TelegramApiException e) {
            System.out.println("Ошибка отправки ответа: ");
            System.out.println(e.getMessage());

            sm = SendMessage.builder()
                    .chatId(who.toString())
                    .text("API запретила отправку данного сообщения. Причину увидит разработчик").build();
            try {
                execute(sm);
            }catch (Exception ex){

            }

        }
    }

}
