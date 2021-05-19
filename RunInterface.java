package proj2;

import java.util.Collections;

public interface RunInterface extends MeldInterface {

    /**
     * Returns suit of the run.
     * @return char representing the run's suit as defined in Card class.
     */
    public char getSuit();

    /**
     * Returns the index of the suit of the run.
     @return int corresponding to index of suit as defined in Card class.
     */
    public int getSuitIndex();

    /**
     * Adds the received card as the first card in the run.
     * @param card card to be added as first.
     */
    public void addAsFirst(Card card);
    /**
     * Adds the received card as the last card in the run.
     * @param card card to be added as last..
     */
    public void addAsLast(Card card);

    /**
     * Returns the value of the first card in the run.
     * @return the first card, without removing it.
     */
    public Card getFirstCard();

    /**
     * Returns the value of the last card in the run.
     * @return the last card, without removing it.
     */
    public Card getLastCard();

}
