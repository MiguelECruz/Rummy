/*
Project Rummy card game: Class Deck
- Student/programmer:   Miguel E. Cruz Molina
- Student number:       801-16-1956
- Course and section:   CCOM 4029-001
- Instructor:           Dr. Patricia Ordóñez
- Template author:      John K. Estell and Dr. Ordóñez
- Date of last edit:    5/18/2021

Summary: This files defines public class "Deck", each instance of
  which is used to represent a deck of playing cards, this through
  a LinkedList of card objects. The class counts with one constructor
  and 14 methods, all of them instance-bound: one for automatically
  filling the deck with all possible cards/combinations of suits
  and ranks from the Card class ("fillDeck()"), one for shuffling the
  deck ("shuffleDeck()"), 2 for adding (overloaded as "addCard()")
  and 4 for removing ("dealCard()" and 3 overloaded versions of
  "removeCard()") cards, one for peeking at the card at the bottom
  of the deck ("peek()"), two related to the deck's amount of cards
  ("getSizeOfDeck()" and "isEmpty()"), one for expressing it as a
  String ("toString()") and two for clearing it out, with or without
  filling it again (overloaded as "restoreDeck()").
*/

package proj2;

import java.util.*;

public class Deck implements DeckInterface{

    protected java.util.LinkedList <Card> deck;

    /**
     * Creates an empty deck of cards.
     */
    public Deck() {
        deck = new LinkedList();
    }

    /**
     * Fills the deck with cards taken as pairs from
     *   a limited range of suits and ranks.
     */
    public void fillDeck(char [] suits, char [] ranks) {
        Card card;
        for(int i = 0; i < suits.length; i++){
            for(int j = 0; j < ranks.length; j++){
                card = new Card(suits[i], ranks[j]);
                addCard(card);
            }
        }
    }

    /**
     * Shuffles the cards present in the deck.
     */
    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    /**
     * This method is used to add Card objects to the deck.
     *   The Deck is completely empty when it is initialized.
     */
    public void addCard(Card card) {
        deck.add(card);
    }

    /**
     * This method is used to add a specific Card object to the deck
     *   in the same way as the conventional addCard() method, but inserts
     *   said card in a specific numbered position in the deck.
     */
    public void addCard(int position, Card card) {

        boolean validPosition = false;
        while (!validPosition) {
            try {
                deck.add(position, card);
                validPosition = true;

            } catch (IndexOutOfBoundsException e) {

                if (position < 0) {
                    System.out.println("\nYou entered a negative position in the deck to add");
                    System.out.println("the card to. Please enter a valid (positive integer)");
                    System.out.print("position. ");
                }

                else if (position > deck.size())
                    deck.add(card);
            }
        }
    }

    /**
     * Removes first card on a deck, equivalent to flipping the Card off of a deck that is facing down.
     * @return <code>null</code> if there are no cards left on the deck. Otherwise returns top Card.
     */
    public Card dealCard() {
        if ( deck.size() == 0 )
            return null;
        else
            return deck.removeFirst();
    }

    /**
     * Removes last card placed on a deck, equivalent removing card from deck that is facing up.
     * @return <code>null</code> if there are no cards left on the deck. Otherwise removes bottom Card.
     */
    public Card removeCard() {
        if (deck.size() == 0)
            return null;
        else {
            return deck.removeLast( );
        }
    }

    /**
     * Removes card placed on the deck that is in the given numbered position, counting from
     *   the first card to the last (and assuming the whole deck is facing down).
     * @return <code>null</code> if the given position for the card is out of bounds.
     */
    public Card removeCard(int position) {

        try {
            return deck.remove(position);

        } catch (IndexOutOfBoundsException e) {

            if (position < 0)
                return removeCard();

            else if (position > deck.size())
                return dealCard();

            return removeCard();
        }
    }

    /**
     * Removes card with a specific value if it's already in the deck.
     * @return <code>null</code> if the given position for the card is out of bounds.
     */
    public boolean removeCard(Card card) { return deck.remove(card); }

    /**
     *  Returns value of bottom Card in a deck that is facing down, without removing it.
     */
    public Card peek() {
        if(deck.size() == 0)
            return null;
        else
            return deck.getLast();
    }

    /**
     * Returns number of cards presently on the deck.
     * @return int
     */
    public int getSizeOfDeck() {
        return deck.size();
    }

    /**
     * Checks whether or not the deck is empty.
     * @return <code>true</code> if there are no cards left to be dealt from the deck.
     */
    public boolean isEmpty() {
        return deck.size() == 0;
    }

    /**
     * Returns a description of the deck.
     * @return a list of all the cards in the deck (top card first, bottom card last).
     */
    public String toString() {
        return deck.toString();
    }

    /**
     * Restores the deck to being empty and ready to add Cards to.
     */
    public void restoreDeck() {
        deck.clear();
    }

    /**
     * First restores the deck to being empty, then systematically adds
     *   cards taken as pairs from a limited range of suits and ranks,
     *   by calling the fillDeck() method.
     */
    public void restoreDeck(char [] suits, char [] ranks) {
        restoreDeck();
        fillDeck(suits, ranks);
    }
}

