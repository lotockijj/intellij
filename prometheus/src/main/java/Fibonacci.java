/**
 * Created by Роман Лотоцький on 13.05.2017.
 */
public class Fibonacci {

    public int fibonacci(int n)  {
        if(n == 0)
            return 0;
        else if(n == 1)
            return 1;
        else
            return fibonacci(n - 1) + fibonacci(n - 2);
    }

}
