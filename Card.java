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
     * Compares two cards for the purposes of sorting.
     * Cards are ordered by their
     * rank value.
     * @param otherCardObject the other card
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
     *
     */
    public boolean equals(Object otherCardObject) {

        Card otherCard = (Card) otherCardObject;

        return getSuitIndex(suitValue) == getSuitIndex(otherCard.getSuit())
            && getRankIndex(rankValue) == getRankIndex(otherCard.getRank());
    }
}
