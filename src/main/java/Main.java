import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Bot bot = FileLoad.load();
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot(bot.getBotUsername(),bot.getBotToken(),bot.getArr(),bot.getArrAnswer(),bot.getArrButtons(),bot.getArrQuestions()));
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }



    }
}
