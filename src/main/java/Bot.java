import org.apache.commons.logging.Log;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static sun.util.logging.LoggingSupport.log;

public class Bot extends TelegramLongPollingBot {

    String name;
    String token;
    String[] arr = {"пример1", "пример2", "пример3", "пример4", "пример5", "пример6", "пример7", "пример8"};
    CallBackInformation callBackInformation = new CallBackInformation();

    public Bot(String name, String token) {
        this.name = name;
        this.token = token;
    }

    public String getArrIndex(int index) {
        return arr[index];
    }


    public void onUpdateReceived(Update update) {
        int i = 0;
        SendMessage sendMessage = new SendMessage();
        Message message = update.getMessage();
        boolean callbackQuery = update.hasCallbackQuery();
        // Long chatId = update.getMessage().getChatId().longValue();
        if (update.hasMessage()) {
            if (message != null && message.hasText()) {
                switch (message.getText()) {
                    case "/start":
                        sendMsg(update.getMessage().getChatId(), "Привет! Я Telegram бот, и я помогу составить тебе твое колесо жизненного баланса. Нажми на кнопку Составить колесо");
                        break;
                    case "Помощь":
                        sendMsg(update.getMessage().getChatId(), "\"Распишите свои цели по 8-ми сферам жизни, реализовав которые вы будете себя чувствовать на 10 баллов из 10 в этой сфере (полностью удовлетворены результатом в этой сфере и счастливы). Сделайте это максимально конкретно по SMART (укажите дедлайн цели, четкую характеристику достижения в цифрах (3000$, 20 км бега, 40 книг в год, 10 новых стран, 3 отдыха в год и т.д.)). \\n\" +\n" +
                                "                    \"\\n\" +\n" +
                                "                    \"Примеры правильных целей из разных сфер: \\\"\\\"хочу читать 52 книги в год\\\"\\\"(развитие), \\\"\\\"хочу получать 3000$ через 3 года\\\"\\\" (финансы), \\\"\\\"хочу побывать в 7 странах за год\\\"\\\"(отдых, путешествия), \\\"\\\"хочу пробежать полумарафон\\\"\\\" (здоровье).\"");
                        break;
                    case "Составить колесо":
                        sendMsg(update.getMessage().getChatId(), "Отлично, начинаем! Я буду показывать тебе сферы твоей жизни. Оценивай их по десятибальной шкале, где 1 - это все плохо, 10 - это очень хорошо, лучше тебе и не нужно в этой сфере. Поехали!\"\n");
                        try {
                            execute(sendInlineKeyBoardMessage(update.getMessage().getChatId(), i));
                            i++;
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        sendMsg(update.getMessage().getChatId(), "SO");

                }
            }
        } else if (callbackQuery) {
            try {
                String str = update.getCallbackQuery().getData();
                System.out.println(str);
                //  callBackInformation.getCallBack(str);
                execute(new SendMessage().setText(update.getCallbackQuery().getData()).setChatId(update.getCallbackQuery().getMessage().getChatId()));

            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
    }


       /* if(update.hasMessage()){
            if (update.getMessage().hasText()){
                    if(update.getMessage().getText().equals("/start")||update.getMessage().getText().equals("Помощь")||update.getMessage().getText().equals("Составить колесо")){
                        startBot(update);
                        if(update.getMessage().getText().equals("1"));
                        startAskForBalance(update);
                    }
                }
            }else if(update.hasCallbackQuery()){
            try {
                String str = update.getCallbackQuery().getData();
                System.out.println(str);
              //  callBackInformation.getCallBack(str);
                execute(new SendMessage().setText(update.getCallbackQuery().getData()).setChatId(update.getCallbackQuery().getMessage().getChatId()));

            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
    }*/

       /* SendMessage sendMessage = new SendMessage();
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                if (update.getMessage().getText().equals("/start")) {
                    sendMsg(update.getMessage().getChatId().toString(), "Привет! Я Telegram бот, и я помогу составить тебе твое колесо жизненного баланса. Нажми на кнопку Составить колесо");//Привет! Я Telegram бот, и я помогу составить тебе твое колесо жизненного баланса. Нажми на кнопку Составить колесо
                }
                if (update.getMessage().getText().equals("Помощь")) {
                    sendMsg(update.getMessage().getChatId().toString(), "Распишите свои цели по 8-ми сферам жизни, реализовав которые вы будете себя чувствовать на 10 баллов из 10 в этой сфере (полностью удовлетворены результатом в этой сфере и счастливы). Сделайте это максимально конкретно по SMART (укажите дедлайн цели, четкую характеристику достижения в цифрах (3000$, 20 км бега, 40 книг в год, 10 новых стран, 3 отдыха в год и т.д.)). \n" +
                            "\n" +
                            "Примеры правильных целей из разных сфер: \"\"хочу читать 52 книги в год\"\"(развитие), \"\"хочу получать 3000$ через 3 года\"\" (финансы), \"\"хочу побывать в 7 странах за год\"\"(отдых, путешествия), \"\"хочу пробежать полумарафон\"\" (здоровье).");
                }
                if (update.getMessage().getText().equals("Составить колесо")) {

                    try {
                        execute(sendInlineKeyBoardMessage(update.getMessage().getChatId()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (update.hasCallbackQuery()) {
            try {
                String str = update.getCallbackQuery().getData();
                System.out.println(str);
                execute(new SendMessage().setText(
                        update.getCallbackQuery().getData())
                        .setChatId(update.getCallbackQuery().getMessage().getChatId()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }



        }*/


    public synchronized void sendMsg(long chatId, String s) {

        SendMessage sendMessage = new SendMessage();
        setButtons(sendMessage);

        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log(Level.SEVERE, "Exception: ", e.toString());
        }
    }


    public String getBotUsername() {
        return name;
    }

    public String getBotToken() {
        return token;
    }


    public synchronized void setButtons(SendMessage sendMessage) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);


        List<KeyboardRow> keyboard = new ArrayList<>();


        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("Составить колесо"));
        keyboardFirstRow.add(new KeyboardButton("Помощь"));
        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public static SendMessage sendInlineKeyBoardMessage(long chatId,int i) {

        String[] arr = {"пример1", "пример2", "пример3", "пример4", "пример5", "пример6", "пример7", "пример8"};

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("1").setCallbackData("1"));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("2").setCallbackData("2"));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("3").setCallbackData("3"));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("4").setCallbackData("4"));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("5").setCallbackData("5"));
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText("6").setCallbackData("6"));
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText("7").setCallbackData("7"));
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText("8").setCallbackData("8"));
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText("9").setCallbackData("9"));
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText("10").setCallbackData("10"));
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);

        return new SendMessage().setChatId(chatId).setText(arr[i]).setReplyMarkup(inlineKeyboardMarkup);
    }


    public void startBot(Update update) {
        if (update.getMessage().getText().equals("/start")) {
            sendMsg(update.getMessage().getChatId(), "Привет! Я Telegram бот, и я помогу составить тебе твое колесо жизненного баланса. Нажми на кнопку Составить колесо");//Привет! Я Telegram бот, и я помогу составить тебе твое колесо жизненного баланса. Нажми на кнопку Составить колесо
        }
        if (update.getMessage().getText().equals("Помощь")) {
            sendMsg(update.getMessage().getChatId(), "Распишите свои цели по 8-ми сферам жизни, реализовав которые вы будете себя чувствовать на 10 баллов из 10 в этой сфере (полностью удовлетворены результатом в этой сфере и счастливы). Сделайте это максимально конкретно по SMART (укажите дедлайн цели, четкую характеристику достижения в цифрах (3000$, 20 км бега, 40 книг в год, 10 новых стран, 3 отдыха в год и т.д.)). \n" +
                    "\n" +
                    "Примеры правильных целей из разных сфер: \"\"хочу читать 52 книги в год\"\"(развитие), \"\"хочу получать 3000$ через 3 года\"\" (финансы), \"\"хочу побывать в 7 странах за год\"\"(отдых, путешествия), \"\"хочу пробежать полумарафон\"\" (здоровье).");
        }
    }


    }






/*

  public String input(String msg){
        if (msg.contains("Hi") || msg.contains("Hello") || msg.contains("Привет")) {
            return "Привет друг!";
        }
        if(msg.contains("Информация о книге")){
            return getInfoBook();
        }
        return msg;
    }


 */