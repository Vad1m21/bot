import java.util.ArrayList;
import java.util.List;

public class CallBackInformation {

    public List<Integer> callBack = new ArrayList<>();

    public  void getCallBack(String str){
        Integer number = Integer.parseInt(str);
        callBack.add(number);
    }

    public  String percentOfLifeBalance(){
        double sum = 0;
        for (Integer i:callBack) {
            sum +=i;
        }
        sum = (sum/80) *100;
        int result = (int) sum;
        System.out.println(sum);
        System.out.println(result);
        String str = new StringBuilder().append(result).append("% .").toString();
        return str;
    }
}
