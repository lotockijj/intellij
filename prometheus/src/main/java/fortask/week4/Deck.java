package fortask.week4;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Роман Лотоцький on 13.05.2017.
 */
public class Deck {
    Card[] decks;

    public Deck(){
        decks = new Card[Suit.values().length*Rank.values().length];
        int k = 0;
        for (Suit suit: Suit.values()) {
            for (Rank rank: Rank.values()) {
                decks[k++] = new Card(suit, rank);
            }
        }
    }

    public void shuffle() {
        int n = decks.length;
        for (int i = 0; i < n; i++) {
            int r = i + (int) (Math.random() * (n-i)); // between i and n-1
            Card tmp = decks[i];    // swap
            decks[i] = decks[r];
            decks[r] = tmp;
        }
    }

    public void order() {
        Arrays.sort(decks, new Comparator<Card>() {
            public int compare(Card o1, Card o2) {
                int flag = Arrays.asList(Suit.values()).indexOf(o1.getSuit())
                        - Arrays.asList(Suit.values()).indexOf(o2.getSuit());
                if(flag == 0){
                    flag = Arrays.asList(Rank.values()).indexOf(o1.getRank())
                            - Arrays.asList(Rank.values()).indexOf(o2.getRank());
                }
                return flag;
            }
        });
    }

    public boolean hasNext() {
        if(decks.length != 0){
            return true;
        }
        return false;
    }

    public Card drawOne() {
        Card card = decks[0];
        Card[] result = new Card[decks.length - 1];
        System.arraycopy(decks, 1, result, 0, result.length);
        decks = result;
        if(decks.length == 0){
            return null;
        }
        return card;
    }

    public  void showCards(){
        int count = 0;
        for (int i = 0; i < decks.length; i++) {
            System.out.print(decks[i]);
            count++;
            if(count == 9){
                System.out.println();
                count = 0;
            }
        }
    }
}
