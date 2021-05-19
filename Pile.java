/*
Project Rummy card game: Class Deck
- Student/programmer:   Miguel E. Cruz Molina
- Student number:       801-16-1956
- Course and section:   CCOM 4029-001
- Instructor:           Dr. Patricia Ordóñez
- Date of last edit:    5/18/2021

Summary: This files defines public class "Pile", each instance of
  which is used to represent a discard pile of playing cards. Since
  the pile needs to work as a stack data structure, this is done
  by extending the MyStack class and using the inherited LinkedList
  for storing Card objects. The class counts with one constructor
  and 14 methods, all of them instance-bound: one for starting the
  pile by adding a card from a Deck object ("startPile()"), one for
  shuffling the pile ("shufflePile()"), 2 for adding (overloaded as
  "addCard()") and 3 for removing ("dealCard()" and 2 overloaded
  versions of "removeCard()") cards, 3 for peeking at the cards at
  the extremes of the pile, 2 related to the deck's amount of cards
  ("getSizeOfpile()" and "isEmpty()"), one for expressing it as a
  String ("toString()") and 2 for clearing it out, with or without
  starting it with a card dealt from a deck again (overloaded as
  "restorePile()").
*/

package proj2;

import java.util.*;

public class Pile extends MyStack <Card> implements PileInterface {

    /**
     * Creates an empty pile of cards.
     */
    public Pile() {
        super();
    }

    /**
     * Starts the pile of cards by adding a single card taken (dealt)
     *   directly from the top of a given deck (defined as a Deck object).
     */
    public void startPile(Deck deck) {
        if (!deck.isEmpty())
            addCard(deck.dealCard());
    }

    /**
     * Shuffles the cards present in the pile.
     */
    public void shufflePile() {
        Collections.shuffle(super.stack);
    }

    /**
     * This method is used to add Card objects to the pile stack.
     *   The Pile is completely empty when it is initialized.
     */
    public void addCard(Card card) {
        super.push(card);
    }

    /**
     * This method is used to add a specific Card object to the pile
     *   in the same way as the conventional addCard() method, but inserts
     *   said card in a specific numbered position in the pile (counting
     *   from the top of the stack).
     */
    public void addCard(int position, Card card) {

        boolean validPosition = false;
        while (!validPosition) {
            try {
                super.stack.add(position, card);
                validPosition = true;

            } catch (IndexOutOfBoundsException e) {

                if (position < 0) {
                    System.out.println("\nYou entered a negative position in the pile to add");
                    System.out.println("the card to. Please enter a valid (positive integer)");
                    System.out.print("position. ");
                }

                else if (position > super.stack.size())
                    super.stack.add(card);
            }
        }
    }

    /**
     * Removes top card of the pile, equivalent to flipping the Card off of the pile that is facing up.
     * @return <code>null</code> if there are no cards left on the pile. Otherwise returns top Card.
     */
    public Card dealCard() {
        return super.pop();
    }

    /**
     * Removes card placed on the pile that is in a given numbered position,
     *   counting from the card at the top of the pile's stack.
     * @return <code>null</code> if the given position for the card is out of bounds.
     */
    public Card removeCard(int position) {
        try {
            return super.stack.remove(position);

        } catch (IndexOutOfBoundsException e) {

            if (position < 0)
                return dealCard();

            else if (position > super.stack.size())
                return super.stack.removeLast();

            return dealCard();
        }
    }

    /**
     * Removes card with a specific value if it's already in the pile's stack.
     * @return <code>null</code> if the given position for the card is out of bounds.
     */
    public boolean removeCard(Card card) {
        return super.stack.remove(card);
    }

    /**
     * Returns value of top card in the pile's stack, without removing it.
     * @return <code>null</code> if the pile is empty.
     */
    public Card peek() {
        return super.top();
    }

    /**
     * As with peek(), returns value of top card in the pile's stack.
     * @return <code>null</code> if the pile is empty.
     */
    public Card peekTopCard() {
        return super.top();
    }

    /**
     * Returns value of bottom card in the pile's stack, without removing it.
     * @return <code>null</code> if the pile is empty.
     */
    public Card peekBottomCard() {
        return super.stack.peekLast();
    }

    /**
     * Returns number of cards presently on the pile.
     * @return int
     */
    public int getSizeOfPile() {
        return super.stack.size();
    }

    /**
     * Checks whether or not the pile is empty.
     * @return <code>true</code> if there are no cards left to be dealt from the pile.
     */
    public boolean isEmpty() {
        return super.isEmpty();
    }

    /**
     * Returns a description of the pile.
     * @return a list of all the cards in the pile (top card first, bottom card last).
     */
    public String toString() {
        return super.toString();
    }

    /**
     * Restores the pile to being empty and ready to add Cards to.
     */
    public void restorePile() {
        super.clear();
    }

    /**
     * First restores the pile to being empty, then adds a single card taken
     *   directly from a specific deck (defined as a Deck object).
     */
    public void restorePile(Deck deck) {
        restorePile();
        startPile(deck);
    }

}


