public class TelegramUser {
    public long userId;
    public long chatId;

    UserState state = UserState.idle;
    CallBackInformation callBackInformation = new CallBackInformation();

}
