package week6;
/**
 * (9 можливих балів)
 * Створити застосування, що упакуковуватиме рядки за допомогою алгоритму RLE.
 * У випадку Вашого застосування потрібно замінити послідовніть однакових букв
 * на букву за якою буде слідувати цифра від 2 до 9, що вкаже на кількість
 * повтору символів. Якщо символ зустрівся лише 1 раз то заміну робити не потрібно.
 * Регістр символів при заміні враховується. Наприклад, рядок:
 * Hhhhhhhhhhhhhheeeellooooo
 * Буде перетворено на:
 * Hh9h4e4l2o5
 * Зверніть увагу: якщо кількість повторів більше 9 то потрібно розбити
 * закодовану інформацію на дві групи. Наприклад:
 * hhhhhhhhhhhh
 * буде замінено на:
 * h9h3
 * Дані для кодування будуть надходити як аргументи командного рядка (лише один рядок).
 * Зверніть увагу на перевірку вхідних даних: на вході НЕ може бути null але може
 * бути пустий рядок. У випадку пустого рядку на вході Ваше застосування повинно
 * вивести на екран пустий рядок.
 */

/**
 * Created by Роман Лотоцький on 19.05.2017.
 *
 */
public class Application {

    public static void main(String[] args) {
        String str = args[0];
        StringBuilder stringB = new StringBuilder();
        int count = 1;
        for (int i = 0; i < str.length() - 1; i++) {
            if (str.charAt(i) == str.charAt(i + 1) || i == str.length() - 1) {
                count++;
            } else {
                stringB.append(str.charAt(i));
                if (count > 1) stringB.append(count);
                count = 1;
            }
            if (count == 9 && str.charAt(i) == str.charAt(i + 1)) {
                stringB.append(str.charAt(i));
                stringB.append(count);
                i++;
                count = 1;
            }
            if (i == str.length() - 2) {
                stringB.append(str.charAt(i));
                if (count > 1) stringB.append(count);
            }
            if (str.charAt(str.length() - 1) != str.charAt(str.length() - 2) && i == str.length() - 2) {
                stringB.append(str.charAt(str.length() - 1));
            }
        }
        System.out.println(str);
        System.out.println(stringB);

        String str2 = stringB.toString();
        StringBuilder stringB2 = new StringBuilder();
        for (int i = 0; i < str2.length(); i++) {
            if(Character.isLetter(str2.charAt(i))){
                stringB2.append(str2.charAt(i));
            } else if(Character.isDigit(str2.charAt(i))){
                int number = Character.getNumericValue(str2.charAt(i));
                for (int j = 0; j < number - 1; j++) {
                    stringB2.append(str2.charAt(i - 1));
                }
            }
        }
        System.out.println(stringB2);


    }
}
