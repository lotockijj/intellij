package week5.week54;

/**
 * Created by Роман Лотоцький on 18.05.2017.
 */
public class EchoClass implements Command {
    String str2 = "";

    public EchoClass(String ... str){
        for (int i = 1; i < str.length; i++) {
            str2 += str[i] + " ";
        }
    }

    public void execute() {
        System.out.println(str2);
    }
}
