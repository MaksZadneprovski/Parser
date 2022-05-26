package telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class BotTG extends TelegramLongPollingBot {
    public static BotTG botTG = new BotTG("ZadneprovskiBot" , "2117503517:AAF8PUfOND53zI1YGGmre3lj95EWzpp2pA4");
    final int RECONNECT_PAUSE =10000;
    private final String botName;
    private final String token;
    String chatId;

    public BotTG(String botName, String token) {
        this.botName = botName;
        this.token = token;
    }
    public void sendMessage(String message){
        if(!Users.listUsers.isEmpty()) {
            for (String s : Users.listUsers) {
                SendMessage sendMessage = new SendMessage(s, message);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message;
        System.out.println(1);
        if (update.hasMessage()){
            System.out.println(2);
            message = update.getMessage();
            chatId = String.valueOf(message.getChatId());
        }
        if (!Users.listUsers.contains(chatId)) {
            try(PrintWriter printWriter = new PrintWriter(Users.path.toString())) {
                printWriter.println(chatId);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClosing() {
        super.onClosing();
    }

    public void botConnect()  {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (Exception e) {
            try {
                e.printStackTrace();
                Thread.sleep(RECONNECT_PAUSE);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                return;
            }
            botConnect();
        }
    }

}
