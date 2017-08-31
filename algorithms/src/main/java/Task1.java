/**
 * Created by Роман Лотоцький on 01.07.2017.
 */
import de.vandermeer.asciitable.AsciiTable;

import java.util.Scanner;

/**
 * Write a program that reads in lines from standard input with each line containing a name
 * and two integers and then uses printf() to print a table with a column of
 * the names, the integers, and the result of dividing the first by the second, accurate to
 * three decimal places. You could use a program like this to tabulate batting averages for
 * baseball players or grades for students.
 */
public class Task1 {

    public void readFromFile(){

        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();
        int firstNumber = Integer.parseInt(scanner.next());
        int secondNumber = Integer.parseInt(scanner.next());
        double result = (double)firstNumber/secondNumber;
        String[] str = {name, String.valueOf(firstNumber),
            String.valueOf(secondNumber), String.valueOf(String.format("%.3f", result))};
        String[] header = {"name", "first number", "second number", "result"};
        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRow();
        asciiTable.addRow(header);
        asciiTable.addRule();
        asciiTable.addRow(str);
        asciiTable.addRule();
        String rend = asciiTable.render();
        System.out.println(rend);
    }

    public static void main(String[] args) {
        Task1 task1 = new Task1();
        task1.readFromFile();
    }

}
