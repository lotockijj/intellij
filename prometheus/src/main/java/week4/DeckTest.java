package week4;

import fortask.week4.Deck;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Роман Лотоцький on 13.05.2017.
 */
public class DeckTest {

    @Test
    public void showCards() throws Exception {
        Deck deck = new Deck();
        deck.showCards();
        Assert.assertEquals(36, deck.decks.length);
    }

    @Test
    public void shuffle() throws Exception {
        Deck deck = new Deck();
        deck.shuffle();
        System.out.println("Deck after shuffling: ");
        deck.showCards();
    }

    @Test
    public void order() throws Exception {
        Deck deck = new Deck();
        deck.shuffle();
        System.out.println("DECK AFTER SHUFFLING: ");
        deck.showCards();
        deck.order();
        System.out.println("AFTER ORDER : ");
        deck.showCards();
    }

    @Test
    public void hasNext() throws Exception {
        Deck deck = new Deck();
        Assert.assertEquals(true, deck.hasNext());
        for (int i = 0; i < 36; i++) {
            deck.drawOne();
        }
        System.out.println("LENGTH IS: " + deck.decks.length);
        Assert.assertEquals(false, deck.hasNext());
    }

    @Test
    public void drawOne() throws Exception {

    }


}