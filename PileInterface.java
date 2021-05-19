/*
Project Rummy card game: Interface DeckInterface
- Student/programmer:   Miguel E. Cruz Molina
- Student number:       801-16-1956
- Course and section:   CCOM 4029-001
- Instructor:           Dr. Patricia Ordóñez
- Date of last edit:    5/18/2021

Summary: This interface includes headers of the instance methods
  necessary for implementing a working Pile class (which represents
  a discard pile in a card game).
*/

package proj2;

import java.util.*;

public interface PileInterface {

    /**
     * Fills the deck with cards taken as pairs from
     *   a limited range of suits and decks
     */
    // public void fillDeck(char [] suits, char [] ranks);

    /**
     * Starts the pile of cards by adding a single card taken (dealt)
     *   directly from the top of a given deck (defined as a Deck object).
     */
    public void startPile(Deck deck);

    /**
     * Shuffles the cards present in the pile.
     */
    public void shufflePile();

    /**
     * This method is used to add Card objects to the pile stack.
     *   The Pile is completely empty when it is initialized.
     */
    public void addCard(Card card);

    /**
     * This method is used to add a specific Card object to the pile
     *   in the same way as the conventional addCard() method, but inserts
     *   said card in a specific numbered position in the pile (counting
     *   from the top of the stack).
     */
    public void addCard(int position, Card card);

    /**
     * Removes top card of the pile, equivalent to flipping the Card off of the pile that is facing up.
     * @return <code>null</code> if there are no cards left on the pile. Otherwise returns top Card.
     */
    public Card dealCard();

    /**
     * Removes card placed on the pile that is in a given numbered position,
     *   counting from the card at the top of the pile's stack.
     * @return <code>null</code> if the given position for the card is out of bounds.
     */
    public Card removeCard(int position);

    /**
     * Removes card with a specific value if it's already in the pile's stack.
     * @return <code>null</code> if the given position for the card is out of bounds.
     */
    public boolean removeCard(Card card);

    /**
     * Returns value of top card in the pile's stack, without removing it.
     * @return <code>null</code> if the pile is empty.
     */
    public Card peek();

    /**
     * As with peek(), returns value of top card in the pile's stack.
     * @return <code>null</code> if the pile is empty.
     */
    public Card peekTopCard();

    /**
     * Returns value of bottom card in the pile's stack, without removing it.
     * @return <code>null</code> if the pile is empty.
     */
    public Card peekBottomCard();

    /**
     * Returns number of cards presently on the pile.
     * @return int
     */
    public int getSizeOfPile();

    /**
     * Checks whether or not the pile is empty.
     * @return <code>true</code> if there are no cards left to be dealt from the pile.
     */
    public boolean isEmpty();

    /**
     * Returns a description of the pile.
     * @return a list of all the cards in the pile (top card first, bottom card last).
     */
    public String toString();

    /**
     * Restores the pile to being empty and ready to add Cards to.
     */
    public void restorePile();

    /**
     * First restores the pile to being empty, then adds a single card taken
     *   directly from a specific deck (defined as a Deck object).
     */
    public void restorePile(Deck deck);

}
