import org.junit.Test;

/**
 * Created by Роман Лотоцький on 13.05.2017.
 */
public class FibonacciTest {
    @Test
    public void fibonacci() throws Exception {
        Fibonacci fibonacci = new Fibonacci();
        System.out.println(fibonacci.fibonacci(3));
        System.out.println(fibonacci.fibonacci(4));
        System.out.println(fibonacci.fibonacci(5));
        System.out.println(fibonacci.fibonacci(6));
    }

}