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
    private int count = 0;
    String name;
    String token;
    String[] arrText;
    String [] arrAnswer;



    String [] arrButtons;
    CallBackInformation callBackInformation = new CallBackInformation();

    public Bot(String name, String token, String[] arr,String[] arrAnswer,String[]arrButtons) {
        this.name = name;
        this.token = token;
        this.arrText = arr;
        this.arrAnswer = arrAnswer;
        this.arrButtons = arrButtons;
    }



    public String getBotUsername() {
        return name;
    }

    public String getBotToken() {
        return token;
    }

    public String[] getArr() {
        return arrText;
    }

    public String[] getArrAnswer() {
        return arrAnswer;
    }

    public String[] getArrButtons() {
        return arrButtons;
    }


    public void onUpdateReceived(Update update) {

        SendMessage sendMessage = new SendMessage();
        Message message = update.getMessage();
        boolean callbackQuery = update.hasCallbackQuery();
        if (update.hasMessage()) {
            if (message != null && message.hasText()) {
                switch (message.getText()) {
                    case "/start":
                        sendMsg(update.getMessage().getChatId(), arrAnswer[0]);
                        break;
                    case "Помощь":
                        sendMsg(update.getMessage().getChatId(), arrAnswer[1]);
                        break;
                    case "Составить колесо":
                        sendMsg(update.getMessage().getChatId(), arrAnswer[2]);
                        try {
                            String text = arrText[count];
                            execute(sendInlineKeyBoardMessage(update.getMessage().getChatId(), text));

                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        sendMsg(update.getMessage().getChatId(), arrAnswer[3]);

                }
            }
        } else if (callbackQuery) {
            String str = update.getCallbackQuery().getData();
            System.out.println(str);
            callBackInformation.getCallBack(str);
            try {
                execute(new SendMessage().setText(update.getCallbackQuery().getData()).setChatId(update.getCallbackQuery().getMessage().getChatId()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            count++;
            if (count == 1 || count == 2 || count == 3 || count == 4 || count == 5 || count == 6 || count == 7) {
                try {
                    String text = arrText[count];
                    execute(sendInlineKeyBoardMessage(update.getCallbackQuery().getMessage().getChatId(), text));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                String str1 = callBackInformation.percentOfLifeBalance();
                sendMsg(update.getCallbackQuery().getMessage().getChatId(), arrAnswer[4] + str1 + arrAnswer[5] + arrAnswer[6]);

            }
        }
    }


    public synchronized void sendMsg(long chatId, String s) {

        SendMessage sendMessage = new SendMessage();
        setButtons(sendMessage);

        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public synchronized void setButtons(SendMessage sendMessage) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);


        List<KeyboardRow> keyboard = new ArrayList<>();


        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton(arrButtons[0]));
        keyboardFirstRow.add(new KeyboardButton(arrButtons[1]));
        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public  SendMessage sendInlineKeyBoardMessage(long chatId, String str) {


        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(arrButtons[2]).setCallbackData(arrButtons[2]));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(arrButtons[3]).setCallbackData(arrButtons[3]));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(arrButtons[4]).setCallbackData(arrButtons[4]));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(arrButtons[5]).setCallbackData(arrButtons[5]));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(arrButtons[6]).setCallbackData(arrButtons[6]));
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(arrButtons[7]).setCallbackData(arrButtons[7]));
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(arrButtons[8]).setCallbackData(arrButtons[8]));
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(arrButtons[9]).setCallbackData(arrButtons[9]));
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(arrButtons[10]).setCallbackData(arrButtons[10]));
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(arrButtons[11]).setCallbackData(arrButtons[11]));
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);

        return new SendMessage().setChatId(chatId).setText(str).setReplyMarkup(inlineKeyboardMarkup);
    }


}