package org.example;

import org.example.Bot.MainBotLogic;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.example.APIWork.APIObject;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        System.out.println("Hello, World!");
        var API = new APIObject();
        var vot = new MainBotLogic(API);
        API.Bot = vot;
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(API);

    }
}