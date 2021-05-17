package proj2;

import java.util.*;

public class Run implements RunInterface {

    char suit;
    int suitIndex;
    protected ArrayList <Card> run = new ArrayList();
    private int runCapacity;

    static private int defaultRunCapacity = Card.rank.length;

    /**
     * Creates new run hand with enough space for the specified 
     *   number of cards of the specified suit.
     * */
    public Run(char suit, int capacity) {
        run.ensureCapacity(capacity);
        this.setHandCapacity(capacity);
        suitIndex = Card.getSuitIndex(suit);
        this.suit = suit;
    }

    /**
     * Calls previous constructor, ensuring new run's capacity is
     *   equivalent to the default for the entire class (normally, 
     *   4, or the length of the rank array from the Card class).
     * */
    public Run(char suit) {
        this(suit, defaultRunCapacity);
    }

    /**
     * Adds a card to this hand, provided that the card has the same
     *   suit as the one the run hand was allocated for.
     * @param card card to be added to the current hand.
     */
    public void addCard(Card card) {
        if (this.suit == card.getSuit())
            run.add(card);
    }

    /**
     * Adds a card to the run's hand at a specific index,
     *   handling an IndexOutOfBounds exception if it occurs.
     * @param index position at which the card will be added to.
     * @param card card to be added to the run at the specified index.
     * */
    public void addCard(int index, Card card) {
        run.add(index, card);
    }

    /**
     * Obtains the card stored at the specified location in the run.
     *   Does not remove the card from the run.
     * @param index position of card to be accessed.
     * @return the card of interest, or the null reference if the index is out of
     *   bounds.
     */
    public Card getCard(int index) {
        return run.get(index);
    }

    /**
     * Removes the specified card from the current run.
     * @param card the card to be removed.
     * @return the card removed from the hand, or null if the card
     *   was not present in the hand.
     */
    public Card removeCard(Card card) {
        int index = this.findCard(card);
        if (index < 0)
            return null;
        else
            return run.remove(index);
    }

    /**
     * Removes the card from the run found at the specified index.
     * @param index position of the card to be removed in the run ArrayList.
     * @return the card removed from the hand, or the null reference if
     * the index is out of bounds.
     */
    public Card removeCard(int index) {
        return run.remove(index);
    }

    /**
     * Determines whether or not the hand contains the specified card.
     * @param card the card being searched for in the hand.
     * @return <code>true</code> if the card is present in the hand.
     */
    public boolean containsCard(Card card) {
        return run.contains(card);
    }

    /**
     * Determines whether or not the run's hand contains a card
     *   with the specified rank and suit.
     * @param rank the rank of the card being searched for in the run.
     * @param suit the suit of the card being searched for in the run.
 * @return <code>true</code> if the card is present in the run.
     */
    public boolean containsCard(char rank, char suit) {
        return findCard(rank, suit) >= 0;
    }

    /**
     * Searches for the first instance of the specified card in the run.
     * @param card card being searched for.
     * @return position index of card if found, or <code>-1</code> if not found.
     */
    public int findCard(Card card) {
        if (this.containsCard(card))
            return run.indexOf(card);
        return -1;
    }

    /**
     * Searches for the first instance of a card with the specified rank and suit.
     * @param rank the rank of the card being searched for.
     * @param suit the suit of the card being searched for.
     * @return position index of card if found, or <code>-1</code> if not found.
     */
    public int findCard(char rank, char suit) {
        for (int i = 0; i < run.size(); i++) {
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
     * @param oldCard is the card to be found and replaced in the run's hand.
     * @param replacementCard is the card to replace oldCard with.
     * @return <code>true</code> if the replacement occurs.
     * [This method is merely added to run as run needs to implement
     *   all methods in the HandInterface. It isn't really intended
     *   to be used during any part of the game.]
     */
    public boolean replaceCard(Card oldCard, Card replacementCard) {
        int location = this.findCard(oldCard);
        if (location < 0)
            return false;
        run.set(location, replacementCard);
        return true;
    }

    /**
     * Returns suit of the run.
     * @return char representing the run's suit as defined in Card class.
     */
    public char getSuit() {
        return suit;
    }

    /**
     * Returns the index of the suit of the run.
     @return int corresponding to index of suit as defined in Card class.
     */
    public int getSuitIndex() {
        return suitIndex;
    }

    /**
     * Sorts all the cards currently in the run (according to their rank). 
     *   The sort is performed according to the order specified in the 
     *   {@link Card} class.
     */
    public void sort() {
        Collections.sort(run);
    }

    /**
     * The number of cards held in the run.
     * @return number of cards currently held in the run.
     */
    public int getNumberOfCards() {
        return run.size();
    }

    /**
     * Checks to see if all the cards of that suit have been
     *   added to the run (it has reached full capacity).
     * @return <code>true</code> if the run is full.
     */
    public boolean isFull() {
        return run.size() == defaultRunCapacity;
    }

    /**
     * Checks to see if the run is empty.
     * @return <code>true</code> if the run is empty.
     */
    public boolean isEmpty() {
        return run.isEmpty();
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

        for (int i = 0; i < this.getNumberOfCards(); i++) {
            Card c = this.getCard(i);
            int cardValue = Card.getRankIndex(c.getRank()) - Card.getRankIndex('a') + 1;
            // ^not sure why "Card.getRankIndex('a')" is necessary here.
            if ( cardValue > 10 )
                cardValue = 10;
            value += cardValue;
        }

        return value;
    }

    /**
     *  Compares two runs' hands.
     *  @param otherHandObject the hand being compared.
     *  @return < 0 if this run has a lesser accumulated point amount than the other hand,
     *    0 if the accumulated point amounts of the two runs are the same, or
     *    > 0 if this run's value is greater than the other run.
     */
    public int compareTo(Object otherHandObject) {
        Run otherHand = (Run) otherHandObject;
        return this.evaluateHand() - otherHand.evaluateHand();
    }

    /**
     * Returns a description of the run.
     * @return a list of cards held in the run's hand.
     */
    public String toString() {
        return run.toString();
    }

    /**
     * Removes all the cards from the run, leaving an empty run hand.
     */
    public void restoreHand() {
        run.clear();
    }

    /**
     * Returns the run's permitted/allocated capacity as established by
     *   the programmer and stored in the instance's runCapacity field.
     * @return value of private instance field runCapacity.
     * */
    public int getHandCapacity() {
        return this.runCapacity;
    }

    /**
     * Sets (and thus changes) the default maximum capacity of the run hand.
     *   Any card whose index falls outside of that capacity is removed from the run.
     * @param newCapacity is the number to which the hand's capacity will be changed,
     *   and should be a a positive integer number.
     * */
    public void setHandCapacity(int newCapacity) {
        this.runCapacity = newCapacity;
        this.truncateHand(newCapacity);
    }

    /**
     * Truncates the run's hand by removing any card for which its index falls
     *   outside of the indicated maximum capacity.
     */
    public void truncateHand(int maxCapacity) {
        if (run.size() > maxCapacity)
            this.run.removeIf(c -> this.findCard(c) >= maxCapacity);
    }

    /**
     * Returns the default capacity established by the programmer
     *   and defined as a static private field of the whole class.
     * @return value of static private field defaultCapacity.
     * */
    public static int getDefaultRunCapacity() {
        return Run.defaultRunCapacity;
    }

    /**
     * Sets (and thus changes) the default capacity of the entire class,
     *   affecting the procedure and effect of constructor Run(char suit, 
     *   int capacity) for any run built with it after the change takes place.
     * @param newDefRunCapacity is the number to which the defaultRunCapacity
     *   static private field of the class will be changed, and should be a
     *   a positive integer number.
     * */
    public static void setDefaultRunCapacity(int newDefRunCapacity) {
        Run.defaultRunCapacity = newDefRunCapacity;
    }
}
