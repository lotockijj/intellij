package week4.practice;

/**
 * Created by Роман Лотоцький on 14.05.2017.
 */
public class Sort {
    private static void sort(int[] array, Comparator comp) {
        for (int gap = array.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < array.length; i++) {
                int val = array[i];
                int j;
                for (j = i; j >= gap && comp.compare(array[j - gap], val) > 0; j -= gap) {
                    array[j] = array[j - gap];
                }
                array[j] = val;
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {1, 5, 2, 4, 10, 6, 0, 3, 10};
        Comparator comp = new Comparator();

        int[] arrayReverse = new int[array.length];
        sort(array, comp);
        for (int i = 0; i < array.length; i++) {
            arrayReverse[i] = array[array.length - i - 1];
        }
        array = arrayReverse;

        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
    }
}