package week5.week54;

import java.util.Date;

/**
 * Created by Роман Лотоцький on 18.05.2017.
 */
public class DateNowClass implements Command {
    Date date;

    public DateNowClass(Date date){
        this.date = date;
    }

    public void execute() {
        System.out.println(System.currentTimeMillis());
    }
}
