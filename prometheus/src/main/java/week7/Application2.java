package week7;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Роман Лотоцький on 19.05.2017.
 */
public class Application2 {

    public static void main(String[] args) throws IOException {
        List<String> list = new LinkedList<>();
        for (int i = 0; i < args.length; i++) {
            list.add(args[i]);
        }
        System.out.println(parse(list));
    }

    public static double parse(List list) {
        double returnValue = 0;
        String operators = "+-*/";

        Stack<String> stack = new Stack<String>();

        for (int i = 0; i < list.size(); i++) {
            if (!operators.contains(list.get(i).toString())) { //push to stack if it is a number
                stack.push(list.get(i).toString());
            } else {//pop numbers from stack if it is an operator
                double a = Double.valueOf(stack.pop());
                double b = Double.valueOf(stack.pop());
                switch (list.get(i).toString()) {
                    case "+":
                        stack.push(String.valueOf(a + b));
                        break;
                    case "-":
                        stack.push(String.valueOf(b - a));
                        break;
                    case "*":
                        stack.push(String.valueOf(a * b));
                        break;
                    case "/":
                        stack.push(String.valueOf(b / a));
                        break;
                }
            }
        }

        returnValue = Double.valueOf(stack.pop());

        return returnValue;
    }
}
