package week5;

import org.junit.Test;

/**
 * Created by Роман Лотоцький on 14.05.2017.
 */
public class OddTest {
    @Test
    public void test1() throws Exception {
        Odd odd = new Odd();
        odd.test(1, 2);
    }

    @Test
    public void test2() throws Exception {
        Odd.test(10.0, 10);
    }

    @Test
    public void test3() throws Exception {
        Odd.test(10, 10.0);
    }

}