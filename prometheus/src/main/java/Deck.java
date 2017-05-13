import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Роман Лотоцький on 12.05.2017.
 */
public class Deck {

    Card[] deck;

    public Deck(){
        deck = new Card[Suit.values.length*Rank.values.length];
        int k = 0;
        for (int i = 0; i < Suit.values.length; i++) {
            for (int j = 0; j < Rank.values.length; j++) {
                deck[k++] = new Card(Rank.values[j], Suit.values[i]);
            }
        }
    }

    //Перемішує колоду у випадковому порядку
    public void shuffle() {
        int n = deck.length;
        for (int i = 0; i < n; i++) {
            int r = i + (int) (Math.random() * (n-i)); // between i and n-1
            Card tmp = deck[i];    // swap
            deck[i] = deck[r];
            deck[r] = tmp;
        }
    }
    /* * Впорядкування колоди за мастями та значеннями
    * Порядок сотрування:
    * Спочатку всі карти з мастю HEARTS, потім DIAMONDS, CLUBS, SPADES
    * для кожної масті порядок наступний: Ace,King,Queen,Jack,10,9,8,7,6
    * Наприклад
    * HEARTS Ace
    * HEARTS King
    * HEARTS Queen
    * HEARTS Jack
    * HEARTS 10
    * HEARTS 9
    * HEARTS 8
    * HEARTS 7
    * HEARTS 6
    * І так далі для DIAMONDS, CLUBS, SPADES */
    public void order() {
        Arrays.sort(deck, new Comparator<Card>() {
                    public int compare(Card o1, Card o2) {
                        int flag = Arrays.asList(Suit.values).indexOf(o1.getSuit())
                                - Arrays.asList(Suit.values).indexOf(o2.getSuit());
                        if(flag == 0){
                            flag = Arrays.asList(Rank.values).indexOf(o1.getRank())
                                    - Arrays.asList(Rank.values).indexOf(o2.getRank());
                        }
                        return flag;
                    }
        });
    }

    //Повертає true у випадку коли в колоді ще доступні карти
    public boolean hasNext() {
        if(deck.length != 0){
            return true;
        }
        return false;
    }

    //"Виймає" одну карту з колоди, коли буде видано всі 36 карт повертає null
    //Карти виймаються з "вершини" колоди. Наприклад перший виклик видасть SPADES 6 потім
    //SPADES 7, ..., CLUBS 6, ..., CLUBS Ace і так далі до HEARTS Ace
    public Card drawOne() {
        Card card = deck[0];
        Card[] result = new Card[deck.length - 1];
        System.arraycopy(deck, 1, result, 0, result.length);
        deck = result;
        if(deck.length == 0){
            return null;
        }
        return card;
    }

    public  void showCards(){
        int count = 0;
        for (int i = 0; i < deck.length; i++) {
            System.out.print(deck[i].getSuit().getName() + " "
                    + deck[i].getRank().getName() + " || ");
            count++;
            if(count == 9){
                System.out.println();
                count = 0;
            }
        }
    }
}