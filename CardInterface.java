package proj2;

import javax.swing.*;

public interface CardInterface extends Comparable {

    final static char [] suit = {'c','d','h','s'};
    final static char [] rank = {'a','2','3','4','5','6','7','8','9','t','j','q','k'};
    final public static String directory = "cards/";

    /**
     * Creates a new playing card by initializing the three instance data members of this class
     * @param suit: the suit value of this card.
     * @param rank: the rank value of this card.
     * @param cardFace: the face image of this card.
     */
    // public Card(char suit, char rank, ImageIcon cardFace );

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
    public int compareTo( Object otherCardObject );

}