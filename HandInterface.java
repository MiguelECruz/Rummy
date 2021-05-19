/*
Project Rummy card game: Interface HandInterface
- Student/programmer:   Miguel E. Cruz Molina
- Student number:       801-16-1956
- Course and section:   CCOM 4029-001
- Instructor:           Dr. Patricia Ordóñez
- Template author:      John K. Estell (5/8/2003) and Dr. Ordóñez
- Date of last edit:    5/18/2021

Summary: This interface includes headers of the most basic
  instance methods necessary for implementing a working Hand
  class (which represents a player's hand of cards in a card
  game). Since the meld classes Set and Run both share a lot
  of functionality with the implementation of a hand, this
  class is extended by MeldInterface". It also extends the
  Comparable interface, to guarantee that methods for comparing
  Hands, Sets and Runs among themselves will be implemented.
*/

package proj2;

import java.util.Arrays;

public interface HandInterface extends Comparable {

    /**
     * Adds a card to this hand.
     * @param card card to be added to the current hand.
     */
    public void addCard(Card card);

    /**
     * Adds a card to the hand at a specific index.
     * @param index position at which the card will be added to.
     * @param card card to be added to the hand at the specified index.
     * */
    public void addCard(int index, Card card);

    /**
     * Obtains the card stored at the specified location in the hand. Does not
     *   remove the card from the hand.
     * @param index position of the card to be accessed.
     * @return the card of interest, or the null reference if the index is out of
     *   bounds.
     */
    public Card getCard(int index);

    /**
     * Removes the specified card from the current hand.
     * @param card the card to be removed.
     * @return the card removed from the hand, or null if the card
     *   was not present in the hand.
     */
    public Card removeCard(Card card);

    /**
     * Removes the card at the specified index from the hand.
     * @param index position of the card to be removed.
     * @return the card removed from the hand, or the null reference if
     *   the index is out of bounds.
     */
    public Card removeCard(int index);

    /**
     * Determines whether or not the hand contains the specified card.
     * @param card the card being searched for in the hand.
     * @return <code>true</code> if the card is present in the hand.
     */
    public boolean containsCard(Card card);

    /**
     * Determines whether or not the hand contains a card
     *   with the specified rank and suit.
     * @param rank the rank of the card being searched for in the hand.
     * @param suit the suit of the card being searched for in the hand.
     * @return <code>true</code> if the card is present in the hand.
     */
    public boolean containsCard(char rank, char suit);

    /**
     * Searches for the first instance of the specified card in the hand.
     * @param card card being searched for.
     * @return position index of card if found, or <code>-1</code> if not found.
     */
    public int findCard(Card card);

    /**
     * Searches for the first instance of a card with the rank and suit.
     * @param rank the rank of the card being searched for.
     * @param suit the suit of the card being searched for.
     * @return position index of card if found, or <code>-1</code> if not found.
     */
    public int findCard(char rank, char suit);

    /**
     * Replaces the specified card with another card.  Only the first
     *   instance of the targeted card is replaced.  No action occurs if
     *   the targeted card is not present in the hand.
     * @param oldCard is the card to be found and replaced in the hand.
     * @param replacementCard is the card to replace oldCard with.
     * @return <code>true</code> if the replacement occurs.
     */
    public boolean replaceCard(Card oldCard, Card replacementCard);

    /**
     * Searches for the first instance of a set (3 or 4 Cards of the same rank) in the hand.
     * @return Card [] of cards in set found in deck, or <code>-null </code> if not found.
     */
    // public Card [] findSet();

    /**
     * Searches for the first set in the hand identified from a particular list of ranks.
     * @return Card [] of cards in found set, or <code>-null </code> if not found.
     */
    // public Card [] findSet(char [] ranks);

    /**
     * Searches for the first instance of a run (3 or 4 Cards of successive ranks
     *   and the same suit) in the hand.
     * @return Card [] of cards in set found in deck, or <code>-null </code> if not found.
     */
    // public Card [] findRun();

    /**
     * Searches for the first run in the hand identified from a particular list of suits.
     * @return Card [] of cards in found set, or <code>-null </code> if not found.
     */
    // public Card [] findRun(char [] suits);

    /**
     * Removes first instance of a set of any rank found in hand.
     */
    // public void removeSet();

    /**
     * Removes first set in the hand identified from a particular list of ranks.
     */
    // public void removeSet(char [] ranks);

    /**
     * Removes first instance of a run of any suit found in hand.
     */

    // public void removeRun();

    /**
     * Removes first run in the hand identified from a particular list of suits.
     */
    // public void removeRun(char [] suits);

    /**
     * Sorts all the cards in the hand. The sort is performed according
     *   to the order specified in the {@link Card} class.
     */
    public void sort();

    /**
     * The number of cards held in the hand.
     * @return number of cards currently held in the hand.
     */
    public int getNumberOfCards();

    /**
     * Checks to see if the hand is empty.
     * @return <code>true</code> if the hand is empty.
     */
    public boolean isEmpty();

    /**
     *  Evaluates the hand and returns its accumulated point amount or equivalent value.
     *  @return an integer corresponding to the rating of the hand.
     */
    public int evaluateHand();

    /**
     *  Compares two hands.
     *  @param otherHandObject the hand being compared.
     *  @return < 0 if this hand has a lesser accumulated point amount than the other hand,
     *    0 if the accumulated point amounts of the two hands are the same, or
     *    > 0 if this hand's value is greater than the other hand.
     */
    public int compareTo(Object otherHandObject);

    /**
     * Returns a description of the hand.
     * @return a list of cards held in the hand.
     */
    public String toString();

    /**
     * Removes all the cards from the hand, leaving an empty hand.
     */
    public void restoreHand();

    /**
     * Returns the hand's permitted/allocated capacity as established by
     *   the programmer and stored in the instance's handCapacity field.
     * @return value of private instance field handCapacity.
     * */
    public int getHandCapacity();

    /**
     * Sets (and thus changes) the default maximum capacity of the hand.
     *   Any card whose index falls outside of that capacity is removed from the hand.
     * @param newCapacity is the number to which the hand's capacity will be changed,
     *   and should be a a positive integer number.
     * */
    public void setHandCapacity(int newCapacity);

    /**
     * Truncates the hand by removing any card for which its index falls
     *   outside of the indicated maximum capacity.
     */
    public void truncateHand(int maxCapacity);

}