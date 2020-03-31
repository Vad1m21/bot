import com.vdurmont.emoji.EmojiParser;

public enum Icon {
    CHECK(":white_check_mark:"),
    QUESTION(":question:"),
    HAND(":hand:"),
    ONE(":one:"),
    TWO(":two:"),
    THREE(":three:"),
    FOUR(":four:"),
    FIVE(":five:"),
    SIX(":six:"),
    SEVEN(":seven:"),
    EIGHT(":eight:"),
    NINE(":nine:"),
    TEN(":keycap_ten:"),
    SAND(":hourglass_flowing_sand:"),
    WARNING(":warning:"),
    CIRCLE(":white_circle:"),
    BOT(":robot_face:");




    private String value;

    public String get() {
        return EmojiParser.parseToUnicode(value);
    }

    Icon(String value) {
        this.value = value;
    }
}
