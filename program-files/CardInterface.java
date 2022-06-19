/*
Project Rummy card game: Interface CardInterface
- Student/programmer:   Miguel E. Cruz Molina
- Student number:       801-16-1956
- Course and section:   CCOM 4029-001
- Instructor:           Dr. Patricia Ordóñez
- Template author:      John K. Estell and Dr. Ordóñez
- Date of last edit:    5/18/2021

Summary: This interface includes headers of the instance methods
  necessary for implementing a working Card class (which represents
  a card in a card game). The interface extends Comparable, as cards
  must be able to be compared among themselves, to facilitate sorting.
*/

package proj2;

import javax.swing.*;

public interface CardInterface extends Comparable {

    /**
     * Creates a new playing card by initializing the three instance data members of this class
     * @param suit: the suit value of this card.
     * @param rank: the rank value of this card.
     * @param cardFace: the face image of this card.
     */
    // public Card(char suit, char rank, ImageIcon cardFace);

    /**
     * Creates a new playing card initialized with the ImageIcon.
     * @param suit: the suit value of this card.
     * @param rank: the rank value of this card.
     */
    // public Card(char suit, char rank);

    /**
     * Returns the Index of the Suit in the defined static array suit.
     * @param suit: the suit value of this card.
     */
    // public static int getSuitIndex(char suit);

    /**
     * Returns the Index of the rank in the defined static array rank.
     * @param rank: the rank value of this card.
     */
    // public static int getRankIndex(char rank);

    /**
     * Returns a String with the card's face image address in the file
     *   system, produced by concatenating the name of the images directory
     *   with the String returned by the toString method of Card and ".gif"
     */
    public String getImageFile();

    /**
     * Returns the suit of the card.
     * @return a char representing the suit value of the card.
     */
    public char getSuit();

    /**
     * Returns the rank of the card.
     * @return a char representing the rank value of the card.
     */
    public char getRank();

    /**
     * Returns the graphic image of the card.
     * @return an icon containing the graphic image of the card.
     */
    public ImageIcon getCardImage();

    /**
     * Returns a two character String with the card's rank being represented by the first
     *   char (taken from the card class's rank array) and its suit by the second char
     *   (taken from the class's suit array).
     * @return the name of the card.
     */
    public String toString();

    /**
     * Compares two cards for the purposes of sorting. Cards should be ordered by
     *   their suit index as defined in the card class's static suit array, and then
     *   by their rank index as defined in that same class's rank array.
     * @param otherCardObject: the other card
     * @return a negative integer, zero, or a positive integer if this card is
     *   less than, equal to, or greater than the referenced card, respectively.
     */
    public int compareTo(Object otherCardObject);

    /**
     * Compares two cards and returns 'true' if they are equal.
     * @param otherCardObject the other card.
     * @return
     */
    public boolean equals(Object otherCardObject);

    /**
     * Compares two cards and returns 'true' if they have the same suit and
     *   (by comparing the indexes of their suit values).
     * @param otherCardObject the other card.
     * @return
     */
    public boolean sameSuit(Object otherCardObject);

    /**
     * Compares two cards and returns 'true' if they have the same rank and
     *   (by comparing the indexes of their rank values).
     * @param otherCardObject the other card.
     * @return
     */
    public boolean sameRank(Object otherCardObject);

    /**
     * Returns the difference in the ranks of two cards.
     * @param otherCardObject the other card.
     * @return the index of this card's rank
     *   minus the index of the other one's rank.
     */
    public int offsetRank(Object otherCardObject);


}