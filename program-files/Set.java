/*
Project Rummy card game: Class Set
- Student/programmer:   Miguel E. Cruz Molina
- Student number:       801-16-1956
- Course and section:   CCOM 4029-001
- Instructor:           Dr. Patricia Ordóñez
- Date of last edit:    5/18/2021

Summary: This files defines public class "Set", each instance of
  which is used to represent a set of cards in a card game, where
  a set is understood to be three of more cards of the same rank
  and consequently different suits. The set itself is implemented
  through a LinkedList of Card objects, which works as a stack,
  hence why this class extends the MyStack class. Each set has
  a specific rank and a capacity, and all sets share a default
  capacity, the number of different suits from which cards of
  the same rank can be selected. Finally, the class has 26
  methods: 2 constructors, 10 for adding, finding, removing and
  replacing cards, 2 setters for the set's rank and its index,
  3 for its size and number of cards, one for evaluating its
  value ("evaluateTo()"), another for comparing that value to
  another hand's ("compareTo()"), one for expressing it as a
  String ("toString()"), one for clearing it ("restoreHand()"),
  one for truncating it ("truncateHand()"), and instance and
  static getters and setters for its capacity and the class's
  default capacity.
*/

package proj2;

import java.util.*;

public class Set extends MyStack <Card> implements SetInterface {

    char rank;
    protected LinkedList <Card> set = super.stack;
    private int setCapacity;

    static private int defaultSetCapacity = Card.suit.length;

    /**
     * Creates new set hand with enough space for the specified
     *   number of cards of the specified rank.
     * */
    public Set(char rank, int capacity) {
        super();
        this.setHandCapacity(capacity);
        this.rank = rank;
    }

    /**
     * Calls previous constructor, ensuring new set's capacity is
     *   equivalent to the default for the entire class (normally,
     *   4, or the length of the suit array from the Card class).
     * */
    public Set(char rank) {
        this(rank, defaultSetCapacity);
    }

    /**
     * Adds a card to this hand, provided that the card has the same
     *   rank as the one the set hand was allocated for.
     * @param card card to be added to the current hand.
     */
    public void addCard(Card card) {
        if (this.rank == card.getRank())
            super.push(card);
    }

    /**
     * Adds a card to the set's hand at a specific index,
     *   handling an IndexOutOfBounds exception if it occurs.
     * @param index position at which the card will be added to.
     * @param card card to be added to the set at the specified index.
     * */
    public void addCard(int index, Card card) {
        if (this.rank == card.getRank())
            set.add(index, card);
    }

    /**
     * Obtains the card stored at the specified location in the set.
     *   Does not remove the card from the set.
     * @param index position of card to be accessed.
     * @return the card of interest, or the null reference if the index is out of
     *   bounds.
     */
    public Card getCard(int index) {
        return set.get(index);
    }

    /**
     * Removes the specified card from the current set.
     * @param card the card to be removed.
     * @return the card removed from the hand, or null if the card
     *   was not present in the hand.
     */
    public Card removeCard(Card card) {
        int index = this.findCard(card);
        if (index < 0)
            return null;
        else
            return set.remove(index);
    }

    /**
     * Removes the card from the set found at the specified index.
     * @param index position of the card to be removed in the set ArrayList.
     * @return the card removed from the hand, or the null reference if
     * the index is out of bounds.
     */
    public Card removeCard(int index) {
        return set.remove(index);
    }

    /**
     * Determines whether or not the hand contains the specified card.
     * @param card the card being searched for in the hand.
     * @return <code>true</code> if the card is present in the hand.
     */
    public boolean containsCard(Card card) {
        return set.contains(card);
    }

    /**
     * Determines whether or not the set's hand contains a card
     *   with the specified rank and suit.
     * @param rank the rank of the card being searched for in the set.
     * @param suit the suit of the card being searched for in the set.
     * @return <code>true</code> if the card is present in the set.
     */
    public boolean containsCard(char rank, char suit) {
        return findCard(rank, suit) >= 0;
    }

    /**
     * Searches for the first instance of the specified card in the set.
     * @param card card being searched for.
     * @return position index of card if found, or <code>-1</code> if not found.
     */
    public int findCard(Card card) {
        if (this.containsCard(card))
            return set.indexOf(card);
        return -1;
    }

    /**
     * Searches for the first instance of a card with the specified rank and suit.
     * @param rank the rank of the card being searched for.
     * @param suit the suit of the card being searched for.
     * @return position index of card if found, or <code>-1</code> if not found.
     */
    public int findCard(char rank, char suit) {
        for (int i = 0; i < set.size(); i++) {
            if (this.getCard(i).getRank() == rank
                    && this.getCard(i).getSuit() == suit)
                return i;
        }
        return -1;
    }

    /**
     * Replaces the specified card with another card.  Only the first
     *   instance of the targeted card is replaced.  No action occurs if
     *   the targeted card is not present in the hand.
     * @param oldCard is the card to be found and replaced in the set's hand.
     * @param replacementCard is the card to replace oldCard with.
     * @return <code>true</code> if the replacement occurs.
     * [This method is merely added to Set as it needs to implement
     *   all methods in the HandInterface. It isn't really intended
     *   to be used during any part of the game.]
     */
    public boolean replaceCard(Card oldCard, Card replacementCard) {
        int location = this.findCard(oldCard);
        if (location < 0)
            return false;
        set.set(location, replacementCard);
        return true;
    }

    /**
     * Returns rank of the set.
     * @return char representing the set's rank as defined in Card class.
     */
    public char getRank() {
        return rank;
    }

    /**
     * Returns the index of the rank of the set.
     @return int corresponding to index of rank as defined in Card class.
     */
    public int getRankIndex() {
        return Card.getRankIndex(rank);
    }

    /**
     * Sorts all the cards currently in the set (according to their suit).
     *   The sort is performed according to the order specified in the
     *   {@link Card} class.
     */
    public void sort() {
        Collections.sort(set);
    }

    /**
     * The number of cards held in the set.
     * @return number of cards currently held in the set.
     */
    public int getNumberOfCards() {
        return set.size();
    }

    /**
     * Checks to see if all the cards of that rank have been
     *   added to the set (it has reached full capacity).
     * @return <code>true</code> if the set is full.
     */
    public boolean isFull() {
        return set.size() == defaultSetCapacity;
    }

    /**
     * Checks to see if the set is empty.
     * @return <code>true</code> if the set is empty.
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     *  Evaluates the hand and returns its accumulated point amount or equivalent value.
     *    In this version of Rummy, each numbered card is worth its displayed pip value
     *    (ace = 1, two = 2, etc.) in points, with face cards worth ten points each.
     *    The value of a hand is equal to the summation of the points of all the cards
     *    held in the hand.
     *  @return an integer corresponding to the rating of the hand.
     */
    public int evaluateHand() {
        int value = 0;

        int cardValue = getRankIndex() + 1;
        if (cardValue > 10)
            cardValue = 10;
        value = getNumberOfCards() * cardValue;

        return value;
    }

    /**
     *  Compares two sets' hands.
     *  @param otherHandObject the hand being compared.
     *  @return < 0 if this set has a lesser accumulated point amount than the other hand,
     *    0 if the accumulated point amounts of the two sets are the same, or
     *    > 0 if this set's value is greater than the other set.
     */
    public int compareTo(Object otherHandObject) {
        Set otherSet = (Set) otherHandObject;
        return getRankIndex() - otherSet.getRankIndex();
    }

    /**
     * Returns a description of the set.
     * @return a list of cards held in the set's hand.
     */
    public String toString() {
        return set.toString();
    }

    /**
     * Removes all the cards from the set, leaving an empty set hand.
     */
    public void restoreHand() {
        set.clear();
    }

    /**
     * Returns the set's permitted/allocated capacity as established by
     *   the programmer and stored in the instance's setCapacity field.
     * @return value of private instance field setCapacity.
     * */
    public int getHandCapacity() {
        return this.setCapacity;
    }

    /**
     * Sets (and thus changes) the default maximum capacity of the set hand.
     *   Any card whose index falls outside of that capacity is removed from the set.
     * @param newCapacity is the number to which the hand's capacity will be changed,
     *   and should be a a positive integer number.
     * */
    public void setHandCapacity(int newCapacity) {
        this.setCapacity = newCapacity;
        this.truncateHand(newCapacity);
    }

    /**
     * Truncates the set's hand by removing any card for which its index falls
     *   outside of the indicated maximum capacity.
     */
    public void truncateHand(int maxCapacity) {
        if (set.size() > maxCapacity)
            this.set.removeIf(c -> this.findCard(c) >= maxCapacity);
    }

    /**
     * Returns the default capacity established by the programmer
     *   and defined as a static private field of the whole class.
     * @return value of static private field defaultCapacity.
     * */
    public static int getDefaultSetCapacity() {
        return Set.defaultSetCapacity;
    }

    /**
     * Sets (and thus changes) the default capacity of the entire class,
     *   affecting the procedure and effect of constructor Set(char rank,
     *   int capacity) for any set built with it after the change takes place.
     * @param newDefSetCapacity is the number to which the defaultSetCapacity
     *   static private field of the class will be changed, and should be a
     *   a positive integer number.
     * */
    public static void setDefaultSetCapacity(int newDefSetCapacity) {
        Set.defaultSetCapacity = newDefSetCapacity;
    }
}
