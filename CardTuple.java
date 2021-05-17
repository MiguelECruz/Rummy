package proj2;

public class CardTuple extends MyTuple <Card, Card> {

    /**
     * Creates a tuple with the two given cards as its elements.
     * @param card1 first card in the tuple.
     * @param card2 second card in the tuple
     */
    public CardTuple(Card card1, Card card2) {
        super(card1, card2);
    }

    /**
     * Creates an empty tuple for two Card objects.
     */
    public CardTuple() {
        super();
    }

}
