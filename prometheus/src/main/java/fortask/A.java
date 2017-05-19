package fortask;

/**
 * Created by Роман Лотоцький on 13.05.2017.
 */
public class A {

    static class InnerClass
    {
        int i;
    }

    public static void main(String[] args) {
        int a =  new A.InnerClass().i;
        System.out.println(a);
    }
}
