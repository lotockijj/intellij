package fortask.week4;

/**
 * Created by Роман Лотоцький on 13.05.2017.
 */
public class Card {

    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank){
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit(){
        return suit;
    }

    public Rank getRank(){
        return rank;
    }

    @Override
    public String toString(){
        return suit + " " + rank + " || ";
    }
}
