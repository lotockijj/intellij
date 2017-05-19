package week5.week54;

/**
 * Created by Роман Лотоцький on 18.05.2017.
 */
public class ExitClass implements Command {
    String exit;

    public ExitClass(String exit){
        this.exit = exit;
    }

    public void execute() {
        System.out.println("GoodBuy!");
    }
}
