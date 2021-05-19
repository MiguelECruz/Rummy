/*
Project Rummy card game: Class RummyTabl
- Student/programmer:   Miguel E. Cruz Molina
- Student number:       801-16-1956
- Course and section:   CCOM 4029-001
- Instructor:           Dr. Patricia Ordóñez
- Date of last edit:    5/18/2021

Summary: This file creates a GUI for a version of the Rummy Card
  game. The GUI is implemented through classes RummyTable, HandPanel
  and SetPanel. This version of the game assumes that a 52 French-
  suited card deck is being used, with 13 possible sets and 12
  possible runs. The GUI effectivelysimulates a playing table,
  as the "RummyTable" name suggests, and starts the game by
  creating the game's deck (through class Deck), discard pile
  (through Pile), spaces for the runs and sets (implemented via
  classes Set, Run and SetPanel), and player hands (using classes
  Hand and HandPanel). The RummyTable class follows the Observer
  design pattern by exclusively being able to create a single instance.
*/

package proj2;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 *	This GUI assumes that you are using a 52 card deck and that you have 13 sets in the deck.
 *	The GUI is simulating a playing table
 *	@author Patti Ordoñez
 */

public class RummyTable extends JFrame implements ActionListener {

    final static int numDealtCards = Hand.getDefaultCapacity();
    final static int defaultNumberOfPlayers = 2;

    // Unique instance of RummyTable class:
    private static RummyTable table = new RummyTable();

    // GUI JPanels for players:
    JPanel playerPanel1;
    JPanel playerPanel2;

    // GUI JPanel and JLabels for deck and pile:
    JPanel deckPiles;
    JLabel deckLabel;
    JLabel pileLabel;

    JList p1HandPile;
    JList p2HandPile;

    Deck deck;
    Pile pile;

    Hand [] players;
    Hand [] melds;

    SetPanel [] setPanels = new SetPanel[Card.rank.length];

    HandPanel p1Panel;
    HandPanel p2Panel;

    JLabel topOfStack;
    JLabel deckPile;

    JButton p1Stack;
    JButton p2Stack;

    JButton p1Deck;
    JButton p2Deck;

    JButton p1Lay;
    JButton p2Lay;

    JButton p1LayOnStack;
    JButton p2LayOnStack;

//    private void deal(Card [] cards)
//    {
//        for(int i = 0; i < cards.length; i++)
//            cards[i] = deck.dealCard();
//    }

    /**
     * Creates a new hand initialized to the appropiate capacity
     *   for each player in the game.
     * @param players the array for storing the hands
     *   of all players.
     */
    private void createPlayers(Hand [] players) {
        for(int i = 0; i < players.length; i++)
            players[i] = new Hand(numDealtCards);
    }

    /**
     * Fills each player's hands with cards dealt from the game's
     *   deck, alternating among all hands before finishing.
     * @param players the players' hand's array
     */
    private void dealToPlayers(Hand [] players)
    {
        int [] capacities = new int[players.length];

        int maxCapacity = -1;

        for(int i = 0; i < players.length; i++) {
            capacities[i] = players[i].getHandCapacity();
            if (capacities[i] > maxCapacity)
                maxCapacity = capacities[i];
        }

        Card dealtCard;

        for(int j = 0; j < maxCapacity; j++) {
            for(Hand player: players)
                if (player.getHandCapacity() >= j) {
                    dealtCard = deck.dealCard();
                    if (dealtCard != null)
                        player.addCard(dealtCard);}
        }
    }

    /**
     * Creates and manages the table for the Rummy game.
     */
    private RummyTable() {

        // CALL GUI CONSTRUCTOR AND SET LAYOUT AND SIZE:

        super("The Card Game of the Century");

        setLayout(new BorderLayout());
        setSize(1200,700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // CREATE, FILL AND SHUFFLE DECK:

        deck = new Deck();
        deck.fillDeck(Card.suit, Card.rank);
        deck.shuffleDeck();

        // CREATE PILE:

        pile = new Pile();

        // CREATE top PANEL WITH setPanels 0 - 3:

        JPanel top = new JPanel();

        for (int i = 0; i < Card.rank.length; i++)
            setPanels[i] = new SetPanel(Card.getRankIndex(Card.rank[i]));

        top.add(setPanels[0]);
        top.add(setPanels[1]);
        top.add(setPanels[2]);
        top.add(setPanels[3]);

        // CREATE PANEL FOR PLAYER #1 AND ADDING top TO IT:

        playerPanel1 = new JPanel();
        playerPanel1.add(top);

        // ADD PANEL FOR PLAYER #2 TO NORTH OF LAYOUT:

        add(playerPanel1, BorderLayout.NORTH);

        // CREATE bottom PANEL WITH setPanels 4 - 8:

        JPanel bottom = new JPanel();

        bottom.add(setPanels[4]);
        bottom.add(setPanels[5]);
        bottom.add(setPanels[6]);
        bottom.add(setPanels[7]);
        bottom.add(setPanels[8]);

        // CREATE PANEL FOR PLAYER #2, ADDING bottom TO IT:

        playerPanel2 = new JPanel();
        playerPanel2.add(bottom);

        // ADD PANEL FOR PLAYER #2 TO SOUTH OF LAYOUT:

        add(playerPanel2, BorderLayout.SOUTH);

        // CREATE middle PANEL WITH 1 ROW AND 3 COLUMNS:

        JPanel middle = new JPanel(new GridLayout(1,3));

        // CREATE BUTTONS FOR PLAYER #1'S PANEL:

        p1Stack = new JButton("Stack");             p1Stack.addActionListener(this);
        p1Deck = new JButton("Deck");               p1Deck.addActionListener(this);
        p1Lay = new JButton("Lay");                 p1Lay.addActionListener(this);
        p1LayOnStack = new JButton("LayOnStack");   p1LayOnStack.addActionListener(this);

        // CREATE HANDS FOR BOTH PLAYERS, FILL THEM AND SORT THEM:

        players = new Hand[defaultNumberOfPlayers];
        createPlayers(players);
        dealToPlayers(players);


        players[0].sort();
        players[1].sort();

        // ADD CARDS OF PLAYER #1'S HAND TO JList OBJECT:

        p1HandPile = new JList(players[0].hand);

        // ADD PLAYER #1'S HandPanel TO middle PANEL:

        p1Panel = new HandPanel("Player 1", p1HandPile, p1Stack, p1Deck, p1Lay, p1LayOnStack);
        middle.add(p1Panel);

        // CREATE deckPiles PANEL FOR DECK AND PILE:

        deckPiles = new JPanel();
        deckPiles.setLayout(new BoxLayout(deckPiles, BoxLayout.Y_AXIS));
        deckPiles.add(Box.createGlue());

        // CREATE left PANEL:

        JPanel left = new JPanel();
        left.setAlignmentY(Component.CENTER_ALIGNMENT);

        // CREATE pileLabel AND ADD IT TO left PANEL:

        pileLabel = new JLabel("Stack");
        pileLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        left.add(pileLabel);

        // CREATE topOfStack LABEL AND ADD IT TO left PANEL:

        topOfStack = new JLabel();
        topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));
        topOfStack.setAlignmentY(Component.CENTER_ALIGNMENT);
        left.add(topOfStack);

        // ADD left PANEL TO deckPiles PANEL:

        deckPiles.add(left);
        deckPiles.add(Box.createGlue());

        // CREATE right PANEL:

        JPanel right = new JPanel();
        right.setAlignmentY(Component.CENTER_ALIGNMENT);

        // CREATE deckLabel AND ADD IT TO right PANEL:

        deckLabel = new JLabel("Deck");
        deckLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        right.add(deckLabel);

        // CREATE deckPile LABEL AND ADD IT TO right PANEL:

        deckPile = new JLabel();
        deckPile.setIcon(new ImageIcon(Card.directory + "b.gif"));
        deckPile.setAlignmentY(Component.CENTER_ALIGNMENT);
        right.add(deckPile);

        // ADD right PANEL TO deckPiles PANEL:

        deckPiles.add(right);
        deckPiles.add(Box.createGlue());

        // ADD deckPiles PANEL TO middle PANEL:

        middle.add(deckPiles);

        // CREATING BUTTONS FOR PLAYER #2'S PANEL:

        p2Stack = new JButton("Stack");             p2Stack.addActionListener(this);
        p2Deck = new JButton("Deck");               p2Deck.addActionListener(this);
        p2Lay = new JButton("Lay");                 p2Lay.addActionListener(this);
        p2LayOnStack = new JButton("LayOnStack");   p2LayOnStack.addActionListener(this);

        // ADD CARDS OF PLAYER #2'S HAND TO JList OBJECT:

        p2HandPile = new JList(players[1].hand);

        // ADD PLAYER #2'S HandPanel TO middle PANEL:

        p2Panel = new HandPanel("Player 2", p2HandPile, p2Stack, p2Deck, p2Lay, p2LayOnStack);
        middle.add(p2Panel);

        // ADD middle PANEL TO CENTER OF LAYOUT:

        add(middle, BorderLayout.CENTER);

        // CREATE leftBorder PANEL WITH 2 ROWS AND 1 COLUMN:

        JPanel leftBorder = new JPanel(new GridLayout(2,1));

        // ADD setPanels 9 - 10 TO leftBorder PANEL:

        setPanels[9].setLayout(new BoxLayout(setPanels[9], BoxLayout.Y_AXIS));
        setPanels[10].setLayout(new BoxLayout(setPanels[10], BoxLayout.Y_AXIS));
        leftBorder.add(setPanels[9]);
        leftBorder.add(setPanels[10]);

        // ADD leftBorder PANEL TO WEST OF LAYOUT:

        add(leftBorder, BorderLayout.WEST);

        // CREATE rightBorder PANEL WITH 2 ROWS AND 1 COLUMN:

        JPanel rightBorder = new JPanel(new GridLayout(2,1));

        // ADD setPanels 11 - 12 TO leftBorder PANEL:

        setPanels[11].setLayout(new BoxLayout(setPanels[11], BoxLayout.Y_AXIS));
        setPanels[12].setLayout(new BoxLayout(setPanels[12], BoxLayout.Y_AXIS));
        rightBorder.add(setPanels[11]);
        rightBorder.add(setPanels[12]);

        // ADD rightBorder PANEL TO WEST OF LAYOUT:

        add(rightBorder, BorderLayout.EAST);
    }

    /**
     * Gets the single instance of a game table that
     *   can be created through this class.
     * @return the class's unique instance.
     */
    public static RummyTable getTable() {
        return table;
    }

    /**
     * Listens and interprets actions done through
     *   the GUI created by the table.
     * @param e the action.
     */
    public void actionPerformed(ActionEvent e)
    {
        // GET SOURCE OF THE ACTION:

        Object src = e.getSource();

        // IF IT WAS ONE OF THE DECK BUTTONS, DEAL CARD AND ADD TO
        //   CORRESPONDING PLAYER'S HAND.

        if(p1Deck == src || p2Deck == src){

            Card card = deck.dealCard();

            if (card != null){
                if(src == p1Deck)
                    players[0].addCard(card);
                else
                    players[1].addCard(card);
            }
            if(deck.getSizeOfDeck() == 0)
                deckPile.setIcon(new ImageIcon(Card.directory + "blank.gif"));
        }

        // IF IT WAS ONE OF THE STACK BUTTONS, REMOVE TOP CARD FROM STACK
        //   AND ADD IT TO CORRESPONDING PLAYER'S CARD.

        if(p1Stack == src || p2Stack == src){

            Card card = pile.dealCard();

            if(card != null){

                Card topCard = pile.peek();
                if (topCard != null)
                    topOfStack.setIcon(topCard.getCardImage());
                else
                    topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));

                if(p1Stack == src)
                    players[0].addCard(card);
                else
                    players[1].addCard(card);
            }
        }

        // IF IT WAS PLAYER #1'S LAY-SET BUTTON, REMOVE
        //   ALL CARDS(?) FROM PLAYER #1'S HAND.

        if(p1Lay == src){
            int [] cards = p1HandPile.getSelectedIndices();
            if (cards != null)
                for(int i = 0; i < cards.length; i++)
                {
                    Card card = players[0].getCard(cards[i]);
                    layCard(card); players[0].removeCard(card);
                }
        }

        // IF IT WAS PLAYER #2'S LAY-SET BUTTON, REMOVE
        //   ALL CARDS(?) FROM PLAYER #2'S HAND.

        if(p2Lay == src){
            int [] cards = p2HandPile.getSelectedIndices();
            if (cards != null)
                for(int i = 0; i < cards.length; i++)
                {
                    Card card = players[1].getCard(cards[i]);
                    layCard(card); players[1].removeCard(card);
                }
        }

        // IF IT WAS PLAYER #1'S LAY-ON-STACK BUTTON, REMOVE CARD
        //   FROM HAND AND ADD IT TO STACK.

        if(p1LayOnStack == src){
            int [] cards  = p1HandPile.getSelectedIndices();
            if (cards.length == 1)
            {
                Card card = players[0].getCard(cards[0]);
                pile.addCard(card);
                players[0].removeCard(card);
                topOfStack.setIcon(card.getCardImage());
            }
        }

        // IF IT WAS PLAYER #2'S LAY-ON-STACK BUTTON, REMOVE CARD
        //   FROM HAND AND ADD IT TO STACK.

        if(p2LayOnStack == src){
            int [] cards  = p2HandPile.getSelectedIndices();
            if (cards.length == 1)
            {
                Card card = players[1].getCard(cards[0]);
                pile.addCard(card);
                players[1].removeCard(card);
                topOfStack.setIcon(card.getCardImage());
            }
        }
    }

    /**
     * Updates the GUI with images of the cards
     *   laid from a player's hand.
     * @param card the card being laid.
     */
    void layCard(Card card)
    {
        char rank = card.getRank();
        char suit = card.getSuit();
        int suitIndex =  Card.getSuitIndex(suit);
        int rankIndex =  Card.getRankIndex(rank);
        //setPanels[rankIndex].array[suitIndex].setText(card.toString());
        setPanels[rankIndex].array[suitIndex].setIcon(card.getCardImage());
    }
}

class HandPanel extends JPanel {

    public HandPanel(String name, JList hand, JButton stack, JButton deck, JButton lay, JButton layOnStack)
    {
        //model = hand.createSelectionModel();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));       // add(Box.createGlue());
        JLabel label = new JLabel(name);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);        add(label);         // add(Box.createGlue());
        stack.setAlignmentX(Component.CENTER_ALIGNMENT);        add(stack);         // add(Box.createGlue());
        deck.setAlignmentX(Component.CENTER_ALIGNMENT);         add(deck);          // add(Box.createGlue());
        lay.setAlignmentX(Component.CENTER_ALIGNMENT);          add(lay);           // add(Box.createGlue());
        layOnStack.setAlignmentX(Component.CENTER_ALIGNMENT);   add(layOnStack);    add(Box.createGlue());
                                                                add(hand);          add(Box.createGlue());
    }


}

class SetPanel extends JPanel {

    private Set data;
    JButton [] array = new JButton[Card.suit.length];

    public SetPanel(int index)
    {
        super();
        data = new Set(Card.rank[index]);

        for(int i = 0; i < array.length; i++){
            array[i] = new JButton("Card " + Card.rank[index] + Card.suit[i]);
            add(array[i]);
        }
    }
}

//class PlayerController {
//
//    Hand playerHand;
//    JList <Card> playerList;
//
//    public PlayerController(Hand player, JList <Card> playerList) {
//
//        this.playerHand = player;
//        this.playerList = playerList;
//
//        this.playerList = new JList(player.hand.toArray());
//    }
//
//    public void addCard(Card card) {
//        ;
//    }
//
//    public void addCard()
//
//
//}

