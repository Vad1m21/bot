import java.util.ArrayList;
import java.util.List;

public class CallBackInformation {

    public static void getCallBack(String str){
        List<Integer> callBack = new ArrayList<>();
        Integer number = Integer.parseInt(str);
        callBack.add(number);



    }


    public static Integer percentOfLifeBalance(List<Integer> callBack){
        Integer sum = 0;
        for (Integer i:callBack) {
            sum+=i;
        }

        return sum;
    }
}
