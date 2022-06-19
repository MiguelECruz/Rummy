/*
Project Rummy card game: Class Card
- Student/programmer:   Miguel E. Cruz Molina
- Student number:       801-16-1956
- Course and section:   CCOM 4029-001
- Instructor:           Dr. Patricia Ordóñez
- Template author:      John K. Estell (5/8/2003) and Dr. Ordóñez
- Date of last edit:    5/18/2021

Summary: This files defines a public class called "Card", which
  is used to create instances that represent each one of the cards
  in the Rummy card game, or, potentially, any other card game that
  utilizes the French-suited card pack. Each Card object has a rank
  and a suit, selected from among two aptly named character arrays,
  and an ImageIcon object used to illustrate the card in a GUI.
  This class also counts with 2 constructors and 12 different
  instance methods: 6 instance getters ("getSuit()", getRank(),
  "getImageFile()", "getCardImage()"), a method for expressing the
  card as a string ("toString()", overridden from java.lang.Object),
  5 for comparing its values to other Card objects ("equal()",
  also overridden from java.lang.Object, "compareTo()", "equals()",
  "sameRank()", "sameSuit()" and "offsetRank()"). 2 static getters
  are also included, "getSuitIndex()" and "getSuitIndex()", for
  getting the indexes of particular suits and ranks in the suit
  and rank arrays.
*/

package proj2;

// Card.java - John K. Estell - 8 May 2003
// last modified: 23 February 2004
// Implementation of a playing card.  Uses classes Rank and Suit for
// expressing the card value.

import javax.swing.*;

/**
 * Representation of a single playing card. A card consists of a suit value
 * (e.g. hearts, spades, diamonds, clubs), a rank value (e.g. ace, 7, king), and an image of
 * the front of the card.  A card object is immutable; once instantiated, the
 * values cannot change.
 *
 * @author John K. Estell adapted by Patti Ordoñez
 * @version 1.0
 */

public class Card implements CardInterface {

    // instance variables for the card
    private char suitValue;
    private char rankValue;
    private ImageIcon cardImage;

    final static char [] suit = {'C','D','H','S'};
    final static char [] rank = {'A','2','3','4','5','6','7','8','9','T','J','Q','K'};

    static String directory = "cards/";

    /**
     * Creates a new playing card by initializing the three instance
     *   data members of this class.
     * @param suit: the suit value of this card.
     * @param rank: the rank value of this card.
     * @param cardFace: the face image of this card.
     */
    public Card(char suit, char rank, ImageIcon cardFace) {
        cardImage = cardFace;
        suitValue = suit;
        rankValue = rank;
    }

    /**
     * Creates a new playing card with two of its members initialized,
     *   and asks for the card's ImageIcon.
     * @param suit: the suit value of this card.
     * @param rank: the rank value of this card.
     */
    public Card(char suit, char rank) {
        suitValue = suit;
        rankValue = rank;
        cardImage = new ImageIcon(getImageFile());
//        new Card(suit, rank, cardImage);
    }

    /**
     * Returns the index the card's suit has in the defined static array suit.
     * @param suit: the suit value of this card.
     */
    public static int getSuitIndex(char suit) {

        switch(suit){
            case 'C':
                return 0;

            case 'D':
                return 1;

            case 'H':
                return 2;

            case 'S':
                return 3;
        }
        return -1;
    }

    /**
     * Returns the index the card's rank has in the defined static array rank.
     * @param rank: the rank value of this card.
     */
    public static int getRankIndex(char rank) {

        switch (rank){
            case 'A':
                return 0;
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return rank - '1';
            case 'T':
                return 9;
            case 'J':
                return 10;
            case 'Q':
                return 11;
            case 'K':
                return 12;
            default:
                return -1;
        }
    }

    /**
     * Returns a String with the card's face image address in the file
     *   system, produced by concatenating the name of the images directory
     *   with the String returned by the toString method of Card and ".gif"
     */
    public String getImageFile() {
        return directory + toString() + ".gif";
    }

    /**
     * Returns the suit of the card.
     * @return a Suit constant representing the suit value of the card.
     */
    public char getSuit() {
        return suitValue;
    }

    /**
     * Returns the rank of the card.
     * @return a Rank constant representing the rank value of the card.
     */
    public char getRank() {
        return rankValue;
    }

    /**
     * Returns the graphic image of the card.
     * @return an icon containing the graphic image of the card.
     */
    public ImageIcon getCardImage() {
        return cardImage;
    }

    /**
     * Returns a description of this card.
     * @return the name of the card.
     */
    public String toString() {
        return "" + getRank() + getSuit();
    }

    /**
     * Compares two cards for the purposes of sorting. Cards should be ordered by
     *   their suit index as defined in the card class's static suit array, and then
     *   by their rank index as defined in that same class's rank array.
     * @return a negative integer, zero, or a positive integer is this card is
     * less than, equal to, or greater than the referenced card.
     */
    public int compareTo(Object otherCardObject) {

        Card otherCard = (Card) otherCardObject;

        if (getSuitIndex(suitValue) - getSuitIndex(otherCard.suitValue) != 0)
            return getSuitIndex(suitValue) - getSuitIndex(otherCard.suitValue);

        else
            return getRankIndex(rankValue) - getRankIndex(otherCard.rankValue);
    }

    /**
     * Compares two cards and returns 'true' if they have the same suit and
     *   rank (by comparing the indexes of those values).
     * @param otherCardObject the other card.
     * @return whether they have the same rank and suit.
     */
    public boolean equals(Object otherCardObject) {
        if (otherCardObject != null) {
            return sameSuit(otherCardObject) && sameRank(otherCardObject);
        }
        return false;
    }

    /**
     * Compares two cards and returns 'true' if they have the same suit and
     *   (by comparing the indexes of their suit values).
     * @param otherCardObject the other card.
     * @return whether they have the same suit.
     */
    public boolean sameSuit(Object otherCardObject) {

        Card otherCard = (Card) otherCardObject;

        return getSuitIndex(suitValue) == getSuitIndex(otherCard.getSuit());
    }

    /**
     * Compares two cards and returns 'true' if they have the same rank and
     *   (by comparing the indexes of their rank values).
     * @param otherCardObject the other card.
     * @return whether they have the same rank.
     */
    public boolean sameRank(Object otherCardObject) {

        Card otherCard = (Card) otherCardObject;

        return getRankIndex(rankValue) == getRankIndex(otherCard.getRank());
    }

    /**
     * Returns the difference in the ranks of two cards.
     * @param otherCardObject the other card.
     * @return the index of this card's rank
     *   minus the index of the other one's rank.
     */
    public int offsetRank(Object otherCardObject) {

        Card otherCard = (Card) otherCardObject;

        return getRankIndex(rankValue) - getRankIndex(otherCard.getRank());
    }


}
