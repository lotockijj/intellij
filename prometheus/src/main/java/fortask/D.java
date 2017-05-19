package fortask;

/**
 * Created by Роман Лотоцький on 13.05.2017.
 */
enum D
{
    A, B, C;

    private D() {
        System.out.print("*");
    }

    public static void main(String[] args) {
        Enum enu = D.B;
    }
}