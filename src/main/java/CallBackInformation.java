import java.util.ArrayList;
import java.util.List;

public class CallBackInformation {

    public List<Integer> callBack = new ArrayList<>();

    public  void getCallBack(String str){
        Integer number = Integer.parseInt(str);
        callBack.add(number);
    }

    public int findStrongSide(){
        int max = 0;
        int count = 0;
        for (int i = 0; i <callBack.size();i++){
            if(max < callBack.get(i)){
                max = callBack.get(i);
                count = i;
            }
        }

        return count;
    }

    public  int findWeakSide(){
        int min = callBack.get(0);
        int count = 0;
        for(int i = 0;i <callBack.size();i++){
            if(min > callBack.get(i)){
                min = callBack.get(i);
                count = i;
            }

        }

        return count;
    }


    public  String percentOfLifeBalance(){
        double sum = 0;
        for (Integer i:callBack) {
            sum +=i;
        }
        sum = (sum/80) *100;
        int result = (int) sum;
        System.out.println(result);
        String str = new StringBuilder().append(result).append("%. ").toString();
        callBack.clear();
        return str;
    }

}

