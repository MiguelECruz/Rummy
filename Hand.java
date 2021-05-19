// Hand.java - John K. Estell - 8 May 2003
// last modified: 23 Febraury 2004
// Implementation of a abstract hand of playing cards.
// Uses the Card class.  Requires subclass for specifying
// the specifics of what constitutes the evaluation of a hand
// for the game being implemented.

package proj2;

import javax.swing.*;
import java.util.*;

/**
 * Represents the basic functionality of a hand of cards.
 * Extensions of this class will provide the
 * definition of what constitutes a hand for that game and how hands are compared
 * to one another by overriding the <code>compareTo</code> method.
 * @author John K. Estell
 * @version 1.0
 */
public class Hand implements HandInterface {

    // protected java.util.ArrayList <Card> hand = new ArrayList();
    protected javax.swing.DefaultListModel <Card> hand;
    private int handCapacity;

    private JList <Card> handJList = new JList <Card> ();

    private static int defaultCapacity = 9;

    /**
     * Creates new hand with enough space for specified capacity.
     * */
    public Hand(int capacity) {
        hand = new DefaultListModel<Card>();

        hand.ensureCapacity(capacity);
        this.setHandCapacity(capacity);
    }

    /**
     * Calls previous constructor, ensuring new hand's capacity is
     *   equivalent to the default for the entire class.
     * */
    public Hand() {
        this(defaultCapacity);
    }

    /**
     * Adds a card to this hand.
     * @param card card to be added to the current hand.
     */
    public void addCard(Card card) {
        hand.addElement(card);
        this.sort();
    }

    /**
     * Adds a card to the hand at a specific index, handling an
     *   IndexOutOfBounds exception if it occurs.
     * @param index position at which the card will be added to.
     * @param card card to be added to the hand at the specified index.
     * */
    public void addCard(int index, Card card) {
        hand.add(index, card);
        this.sort();
    }

    /**
     * Obtains the card stored at the specified location in the hand. Does not
     *   remove the card from the hand.
     * @param index position of the card to be accessed.
     * @return the card of interest, or the null reference if the index is out of
     *   bounds.
     */
    public Card getCard(int index) {
        return hand.get(index);
    }

    /**
     * Removes the specified card from the current hand.
     * @param card the card to be removed.
     * @return the card removed from the hand, or null if the card
     *   was not present in the hand.
     */
    public Card removeCard(Card card) {
        int index = this.findCard(card);
        if (index < 0)
            return null;
        else
            return hand.remove(index);
    }

    /**
     * Removes the card at the specified index from the hand.
     * @param index position of the card to be removed.
     * @return the card removed from the hand, or the null reference if
     *   the index is out of bounds.
     */
    public Card removeCard(int index) {
        return hand.remove(index);
    }

    /**
     * Determines whether or not the hand contains the specified card.
     * @param card the card being searched for in the hand.
     * @return <code>true</code> if the card is present in the hand.
     */
    public boolean containsCard(Card card) {
        return hand.contains(card);
    }

    /**
     * Determines whether or not the hand contains a card
     *   with the specified rank and suit.
     * @param rank the rank of the card being searched for in the hand.
     * @param suit the suit of the card being searched for in the hand.
     * @return <code>true</code> if the card is present in the hand.
     */
    public boolean containsCard(char rank, char suit) {
        return findCard(rank, suit) >= 0;
    }

    /**
     * Searches for the first instance of the specified card in the hand.
     * @param card card being searched for.
     * @return position index of card if found, or <code>-1</code> if not found.
     */
    public int findCard(Card card) {
        if (this.containsCard(card))
            return hand.indexOf(card);
        return -1;
    }

    /**
     * Searches for the first instance of a card with the specified rank and suit.
     * @param rank the rank of the card being searched for.
     * @param suit the suit of the card being searched for.
     * @return position index of card if found, or <code>-1</code> if not found.
     */
    public int findCard(char rank, char suit) {
        for (int i = 0; i < hand.getSize(); i++) {
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
     * @param oldCard is the card to be found and replaced in the hand.
     * @param replacementCard is the card to replace oldCard with.
     * @return <code>true</code> if the replacement occurs.
     */
    public boolean replaceCard(Card oldCard, Card replacementCard) {
        int location = this.findCard(oldCard);
        if (location < 0)
            return false;
        hand.set(location, replacementCard);
        this.sort();
        return true;
    }

    /**
     * Searches for the first instance of a set (3 or 4 Cards of the same rank) in the hand.
     * @return Card [] of cards in set found in deck, or <code>-null </code> if not found.
     */
    public Set findSet() {
        return findSet(Card.rank, Card.suit);
    }

    /**
     * Searches for the set of highest rank in the hand, identified from a particular list of ranks.
     * @return Card [] of cards in found set, or <code>-null </code> if not found.
     */
    public Set findSet(char [] ranks, char [] suits) {

        Set set;
        for (int i = ranks.length - 1; i >= 0; i--) {

            set = new Set(ranks[i]);
            for (int j = suits.length - 1; j >= 0; j--) {
                if (this.containsCard(ranks[i], suits[j]))
                    set.addCard(hand.elementAt(findCard(ranks[i], suits[j])));
            }

            if (set.getNumberOfCards() >= 3)
                return set;
        }
        return null;
    }

    /**
     * Searches for the first instance of a run (3 or 4 Cards of successive ranks
     *   and the same suit) in the hand. Ranks and suits are taken from the Card class.
     * @return Card [] of cards in set found in deck, or <code>-null </code> if not found.
     */
    public Run findRun() {
        if (this.getNumberOfCards() >= 3) {

            Run run = new Run(Card.suit[0]);

            Card prevCard;
            Card thisCard;
            boolean prevComparison;

            int count = 0;
            for (int i = getNumberOfCards() - 2; i >= 0; i--) {

                prevCard = this.getCard(i + 1);
                thisCard = this.getCard(i);

                prevComparison = (prevCard.sameSuit(thisCard)
                        && prevCard.offsetRank(thisCard) == 1);
                if (prevComparison)
                    count++;
                if (count == 2 && prevComparison) {
                    run = new Run(thisCard.getSuit());
                    run.addCard(prevCard);
                    run.addCard(getCard(i + 2));
                }
                if (count >= 2 && prevComparison) {
                    run.addCard(0, thisCard);
                    if (i == 0) return run;
                }
                else if (count >= 2)
                    return run;
                else if (!prevComparison)
                    count = 0;
            }
        }
        return null;
    }

    /**
     * Removes first instance of a set of any rank found in hand.
     */
    public Set removeSet() {
        return removeSet(Card.rank, Card.suit);
    }

    /**
     * Removes first set in the hand identified from a particular list of ranks.
     */
    public Set removeSet(char [] ranks, char [] suits) {
        Set set = this.findSet(ranks, suits);
        if (set != null) {
            for (int i = 0; i < set.getNumberOfCards(); i++) {
                this.removeCard(set.getCard(i));
            }
        }
        return set;
    }

    /**
     * Removes first run in the hand.
     */
    public Run removeRun() {
        Run run = this.findRun();
        if (run != null) {
            for (int i = 0; i < run.getNumberOfCards(); i++) {
                this.removeCard(run.getCard(i));
            }
        }
        return run;
    }

    /**
     * Sorts all the cards in the hand. The sort is performed according
     *   to the order specified in the {@link Card} class.
     */
    public void sort() {

        ArrayList <Card> handAsList = new ArrayList <Card> (this.getNumberOfCards());

        for (int i = 0; i < this.getNumberOfCards(); i++) {
            handAsList.add(this.getCard(i));
        }

        Collections.sort(handAsList);

        hand.clear();
        for(Card card : handAsList)
            hand.addElement(card);
    }

    /**
     * The number of cards held in the hand.
     * @return number of cards currently held in the hand.
     */
    public int getNumberOfCards() {
        return hand.size();
    }

    /**
     * Checks to see if the hand is empty.
     * @return <code>true</code> if the hand is empty.
     */
    public boolean isEmpty() {
        return hand.isEmpty();
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
            int cardValue = Card.getRankIndex(c.getRank()) + 1;
            if (cardValue > 10)
                cardValue = 10;
            value += cardValue;
        }

        return value;
    }

    /**
     *  Compares two hands.
     *  @param otherHandObject the hand being compared.
     *  @return < 0 if this hand has a lesser accumulated point amount than the other hand,
     *    0 if the accumulated point amounts of the two hands are the same, or
     *    > 0 if this hand's value is greater than the other hand.
     */
    public int compareTo(Object otherHandObject) {
        Hand otherHand = (Hand) otherHandObject;
        return this.evaluateHand() - otherHand.evaluateHand();
    }

    /**
     * Returns a description of the hand.
     * @return a list of cards held in the hand.
     */
    public String toString() {
        return hand.toString();
    }

    /**
     * Removes all the cards from the hand, leaving an empty hand.
     */
    public void restoreHand() {
        hand.clear();
    }

    /**
     * Returns the hand's permitted/allocated capacity as established by
     *   the programmer and stored in the instance's handCapacity field.
     * @return value of private instance field handCapacity.
     * */
    public int getHandCapacity() {
        return this.handCapacity;
    }

    /**
     * Sets (and thus changes) the default maximum capacity of the hand.
     *   Any card whose index falls outside of that capacity is removed from the hand.
     * @param newCapacity is the number to which the hand's capacity will be changed,
     *   and should be a a positive integer number.
     * */
    public void setHandCapacity(int newCapacity) {
        this.handCapacity = newCapacity;
        this.truncateHand(newCapacity);
    }

    /**
     * Truncates the hand by removing any card for which its index falls
     *   outside of the indicated maximum capacity.
     */
    public void truncateHand(int maxCapacity) {
        if (hand.size() > maxCapacity)
            this.hand.removeRange(maxCapacity, hand.size() - 1);
    }

    /**
     * Returns the default capacity established by the programmer
     *   and defined as a static private field of the whole class.
     * @return value of static private field defaultCapacity.
     * */
    public static int getDefaultCapacity() {
        return Hand.defaultCapacity;
    }

    /**
     * Sets (and thus changes) the default capacity of the entire class,
     *   affecting the procedure and effect of constructor Hand(int capacity)
     *   for any hand built after the change takes place.
     * @param newDefCapacity is the number to which the defaultCapacity static
     *   private field of the class will be changed, and should be a
     *   a positive integer number.
     * */
    public static void setDefaultCapacity(int newDefCapacity) {
        Hand.defaultCapacity = newDefCapacity;
    }

    /**
     * Links the hand's list to a JList used to represent the hand
     *   in the table's GUI, so that any change in the hand is also
     *   reflected in the linked JList.
     * @param list is the JList that represents the hand in the GUI.
     */
//    public void linkJList(JList list) {
//        this.handJList = list;
//        list = new JList(this.hand);
//    }
}
