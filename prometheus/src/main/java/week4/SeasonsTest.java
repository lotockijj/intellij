package week4;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Роман Лотоцький on 13.05.2017.
 */
public class SeasonsTest {

    @Test
    public void simpleTest(){
        Seasons[] seasons= new Seasons[2];

        for (int i = 0; i  < seasons.length; i++) {
            System.out.print(seasons[i]);
        }
        Assert.assertEquals(2, seasons.length);
    }

}