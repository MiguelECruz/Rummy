/*
Project Rummy card game: Interface DeckInterface
- Student/programmer:   Miguel E. Cruz Molina
- Student number:       801-16-1956
- Course and section:   CCOM 4029-001
- Instructor:           Dr. Patricia Ordóñez
- Date of last edit:    5/18/2021

Summary: This interface includes headers of the instance methods
  necessary for implementing a working Deck class (which represents
  a deck of cards in a card game).
*/

package proj2;

import java.util.*;

public interface DeckInterface {

    /**
     * Creates an empty deck of cards.
     */
    // public void Deck();

    /**
     * Fills the deck with cards taken as pairs from
     *   a limited range of suits and ranks.
     */
    public void fillDeck(char [] suits, char [] ranks);

    /**
     * Shuffles the cards present in the deck.
     */
    public void shuffleDeck();

    /**
     * This method is used to add Card objects to the deck.
     *   The Deck is completely empty when it is initialized.
     */
    public void addCard(Card card);

    /**
     * This method is used to add a specific Card object to the deck
     *   in the same way as the conventional addCard() method, but inserts
     *   said card in a specific numbered position in the deck (counting
     *   from the top of the deck).
     */
    public void addCard(int position, Card card);

    /**
     * Removes first card on a deck, equivalent to flipping the Card off of a deck that is facing down.
     * @return <code>null</code> if there are no cards left on the deck. Otherwise returns top Card.
     */
    public Card dealCard();

    /**
     * Removes last card placed on a deck, equivalent removing card from deck that is facing up.
     * @return <code>null</code> if there are no cards left on the deck. Otherwise removes bottom Card.
     */
    public Card removeCard();

    /**
     * Removes card placed on the deck that is in the given numbered position, counting from
     *   the first card to the last (and assuming the whole deck is facing down).
     * @return <code>null</code> if the given position for the card is out of bounds.
     */
    public Card removeCard(int position);

    /**
     * Removes card with a specific value if it's already in the deck.
     * @return <code>null</code> if the given position for the card is out of bounds.
     */
    public boolean removeCard(Card card);

    /**
     *  Returns value of bottom Card in a deck that is facing down, without removing it.
     */
    public Card peek();

    /**
     * Returns number of cards presently on the deck.
     * @return int
     */
    public int getSizeOfDeck();

    /**
     * Checks whether or not the deck is empty.
     * @return <code>true</code> if there are no cards left to be dealt from the deck.
     */
    public boolean isEmpty();

    /**
     * Restores the deck to being empty and ready to add Cards to.
     */
    public void restoreDeck();

    /**
     * First restores the deck to being empty, then systematically adds
     *   cards taken as pairs from a limited range of suits and ranks,
     *   by calling the fillDeck() method.
     */
    public void restoreDeck(char [] suits, char [] ranks);

}

