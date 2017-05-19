package week5.week54;

/**
 * Created by Роман Лотоцький on 18.05.2017.
 */
public class Application {

    public static void main(String[] args) {

        Command command = null;

        if ((args == null) || (args.length == 0)){
            throw new IllegalArgumentException();
        } else if(args[0].equals("help")){
            command = new HelpClass(args[0]);
        } else if(args[0].equals("echo") && args[1] != null){
            command = new EchoClass(args);
        } else if(args[0].equals("date") && args[1].equals("now")) {
            command = new DateNowClass(null);
        } else if(args[0].equals("exit")){
            command = new ExitClass("Exit");
        } else {
            System.out.println("Error");
        }
        if(command != null) {
             command.execute();
        }
        System.out.println("abc".charAt(1));
        System.out.println("abc".length());
        char chars[] = {'a', 'b', 'c'};
        String str = new String(chars);
        System.out.println(str);
        String str1="Hello";
        String str2="World";
        String str3=str1+str2;
        String str4="HelloWorld";
        System.out.println(str3==str4);
        str = str1.concat(str2);
        System.out.println(str);

    }
}
