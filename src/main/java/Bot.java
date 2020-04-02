
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Bot extends TelegramLongPollingBot {
    private int count = 0;
    private String name;
    private String token;
    private String[] arrText;
    private String [] arrAnswer;
    private String [] arrQuestions;
    private Map<Long,TelegramUser> userData;



    private String [] arrButtons;
    CallBackInformation callBackInformation = new CallBackInformation();

    public Bot(String name, String token, String[] arr,String[] arrAnswer,String[]arrButtons,String[] arrQuestions) {
        this.name = name;
        this.token = token;
        this.arrText = arr;
        this.arrAnswer = arrAnswer;
        this.arrButtons = arrButtons;
        this.arrQuestions = arrQuestions;
        userData = new HashMap<>();
    }

    public String[] getArrQuestions() {
        return arrQuestions;
    }

    @Override
    public void onUpdateReceived(Update update) {
        TelegramUser user = getOrCreate(update);
        SendMessage sendMessage = new SendMessage();
        Message message = update.getMessage();
        boolean callbackQuery = update.hasCallbackQuery();
        if (update.hasMessage()) {
            if (message != null && message.hasText()) {
                switch (message.getText()) {
                    case "/start":
                        sendMsg(user.chatId, Icon.HAND.get() + arrAnswer[0]);
                        break;
                    case "Версия бота":
                        sendMsg(user.chatId, Icon.BOT.get() + Icon.BOT.get() + Icon.BOT.get() + Icon.BOT.get() + Icon.BOT.get());
                        sendMsg(user.chatId, arrAnswer[8]);
                        sendMsg(user.chatId, Icon.BOT.get() + Icon.BOT.get() + Icon.BOT.get() + Icon.BOT.get() + Icon.BOT.get());
                        break;
                    case "Помощь":
                        sendMsg(user.chatId, Icon.QUESTION.get() + Icon.QUESTION.get() + Icon.QUESTION.get() + Icon.QUESTION.get() + Icon.QUESTION.get());
                        sendMsg(user.chatId, arrAnswer[1]);
                        sendMsg(user.chatId, Icon.QUESTION.get() + Icon.QUESTION.get() + Icon.QUESTION.get() + Icon.QUESTION.get() + Icon.QUESTION.get());
                        break;
                    case "Составить колесо":
                        sendMsg(user.chatId, arrAnswer[2]);
                        sendMsg(user.chatId, Icon.CIRCLE.get() + Icon.CIRCLE.get() + Icon.CIRCLE.get() + Icon.CIRCLE.get() + Icon.CIRCLE.get() + Icon.CIRCLE.get());

                        user.state = UserState.inProgress;

                        try {
                            String text = arrQuestions[count];
                            execute(sendInlineKeyBoardMessage(user.chatId, text));

                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        sendMsg(user.chatId, Icon.WARNING.get() + arrAnswer[3] + Icon.WARNING.get());


                }
            }
        } else if (callbackQuery) {
            String str = update.getCallbackQuery().getData();
            System.out.println(str);
            callBackInformation.getCallBackArray(str);
            try {
                execute(new SendMessage().setText(str).setChatId(user.chatId));

            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            count++;
            if (count == 1 || count == 2 || count == 3 || count == 4 || count == 5 || count == 6 || count == 7) {
                try {
                    String text = arrQuestions[count];
                    execute(sendInlineKeyBoardMessage(user.chatId, text));

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                sendMsg(user.chatId, arrQuestions[8] + Icon.SAND.get());
                user.state = UserState.idle;
                count = 0;
                List<Integer> call = callBackInformation.getCallBack();
                Integer[] arr = new Integer[call.size() + 1];
                arr = call.toArray(arr);
                ImageCreate imageCreate = new ImageCreate(arrText, arr);
                imageCreate.createChartPanel();


                int numberForStrongSide = callBackInformation.findStrongSide();
                int numberForWeakSide = callBackInformation.findWeakSide();

                String str1 = callBackInformation.percentOfLifeBalance();

                if (numberForStrongSide == numberForWeakSide) {
                    sendMsg(user.chatId, Icon.CHECK.get() + arrAnswer[4] + str1 + arrAnswer[7]);

                }else{
                    sendMsg(user.chatId, Icon.CHECK.get() + arrAnswer[4] + str1 + arrAnswer[5] + arrText[numberForStrongSide] + arrAnswer[6] + arrText[numberForWeakSide]);
                    SendPhoto msg = new SendPhoto().setChatId(user.chatId).setPhoto( new File("path\to\file.png"));
                    try {
                        execute(msg);
                    }catch (TelegramApiException e){
                        e.printStackTrace();
                    }


                }

            }
        }
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

    public TelegramUser getOrCreate(Update update){
        long userId = 0;
        if (update.hasMessage()){
            userId = update.getMessage().getFrom().getId();

        }else if(update.hasCallbackQuery()){
            userId = update.getCallbackQuery().getFrom().getId();
        }
        TelegramUser result;
        if(userData.containsKey(userId)){
            result = userData.get(userId);
        }else{
            result = new TelegramUser();
            result.userId =userId;
            userData.put(userId,result);
        }
        if(update.hasMessage()){
            result.chatId = update.getMessage().getChatId();
        }
        return result;
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
        KeyboardRow keyboardSecondRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton(arrButtons[0]));
        keyboardFirstRow.add(new KeyboardButton(arrButtons[1]));

        keyboardSecondRow.add(arrButtons[12]);

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public  SendMessage sendInlineKeyBoardMessage(long chatId, String str) {


        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(Icon.ONE.get()).setCallbackData(arrButtons[2]));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(Icon.TWO.get()).setCallbackData(arrButtons[3]));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(Icon.THREE.get()).setCallbackData(arrButtons[4]));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(Icon.FOUR.get()).setCallbackData(arrButtons[5]));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(Icon.FIVE.get()).setCallbackData(arrButtons[6]));
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(Icon.SIX.get()).setCallbackData(arrButtons[7]));
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(Icon.SEVEN.get()).setCallbackData(arrButtons[8]));
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(Icon.EIGHT.get()).setCallbackData(arrButtons[9]));
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(Icon.NINE.get()).setCallbackData(arrButtons[10]));
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(Icon.TEN.get()).setCallbackData(arrButtons[11]));
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);

        return new SendMessage().setChatId(chatId).setText(str).setReplyMarkup(inlineKeyboardMarkup);
    }


}