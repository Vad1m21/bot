import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonToken;
import sun.rmi.runtime.Log;

import java.io.*;
import java.util.Scanner;

public class FileLoad {

  public static File file = new File("BotConfig.json");

    public static Bot load() throws FileNotFoundException {
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(file);
        Scanner scanner = new Scanner(fileReader);
        String str = scanner.nextLine();
        Bot bot = gson.fromJson(str,Bot.class);

        return bot;
    }


}
