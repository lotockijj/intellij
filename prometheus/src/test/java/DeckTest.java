import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Роман Лотоцький on 13.05.2017.
 */
public class DeckTest {

    @Test
    public void shuffle() throws Exception {
        Deck myDeck = new Deck();
        //myDeck.showCards();
        Assert.assertEquals(36, myDeck.deck.length);
        myDeck.shuffle();
        //myDeck.showCards();
    }

    @Test
    public void order() throws Exception {
        Deck myDeck = new Deck();
        //System.out.println("\nTest sorting");
        //System.out.println("Shuffling");
        myDeck.shuffle();
        //myDeck.showCards();
        //System.out.println("After sorting: ");
        myDeck.order();
        //myDeck.showCards();
        String firstCard = myDeck.deck[0].getSuit().getName() + myDeck.deck[0].getRank().getName();
        String heartsAce = "HEARTSAce";
        boolean isFirstCardHeartsAce = firstCard.equals(heartsAce);
        Assert.assertTrue(isFirstCardHeartsAce);
    }

    @Test
    public void hasNext() throws Exception {
        Deck decks = new Deck();
        Assert.assertTrue(decks.hasNext());
        for (int i = 0; i < 36; i++) {
            decks.drawOne();
        }
        Assert.assertFalse(decks.hasNext());
    }

    @Test
    public void drawOne() throws Exception {
        Deck myDeck = new Deck();
        Card card = myDeck.drawOne();
        String strCard = card.getSuit().getName() + card.getRank().getName();
        String frickStringCard = "HEARTSAce";
        boolean isFirstCardHeartsAce = strCard.equals(frickStringCard);
        Assert.assertTrue(isFirstCardHeartsAce);
        Assert.assertEquals(35, myDeck.deck.length);

        card = myDeck.drawOne();
        strCard = card.getSuit().getName() + card.getRank().getName();
        frickStringCard = "HEARTSKing";
        isFirstCardHeartsAce = strCard.equals(frickStringCard);
        Assert.assertTrue(isFirstCardHeartsAce);
        Assert.assertEquals(34, myDeck.deck.length);
    }

}