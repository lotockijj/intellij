package week5.week54;

/**
 * Created by Роман Лотоцький on 18.05.2017.
 */
public class HelpClass implements Command {
    String str = "help";

    public HelpClass(String str){
        this.str = str;
    }

    public void execute() {
        System.out.println("Help executed");
    }
}
