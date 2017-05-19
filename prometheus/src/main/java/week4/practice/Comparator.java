package week4.practice;

/**
 * Created by Роман Лотоцький on 14.05.2017.
 */
public class Comparator {

    public int compare(int a, int b){
        if (a>b) {
            return 1;
        } else if (a == b){
            return 0;
        } else {
            return -1;
        }
    }
}
