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
  possible runs. The GUI effectively simulates a playing table,
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

    // Deck and pile used during the game:

    Deck deck;
    Pile pile;

    // GUI JPanel and JLabels for deck and pile:

    JPanel deckPiles;

    JLabel deckLabel;
    JLabel pileLabel;

    JLabel topOfPile;
    JLabel deckPile;

    // Array of player's hands:

    Hand [] players;

    // Arrays of (1.) panels for players, (2.) lists of players' hands,
    //   and (3.) buttons of each player's panel:

    HandPanel [] playerPanels = new HandPanel[defaultNumberOfPlayers];

    JList [] playerLists = new JList[defaultNumberOfPlayers];

    JButton [] pileButtons = new JButton[defaultNumberOfPlayers];
    JButton [] deckButtons = new JButton[defaultNumberOfPlayers];
    JButton [] runButtons = new JButton[defaultNumberOfPlayers];
    JButton [] setButtons = new JButton[defaultNumberOfPlayers];
    JButton [] layToRunButtons = new JButton[defaultNumberOfPlayers];
    JButton [] layToSetButtons = new JButton[defaultNumberOfPlayers];
    JButton [] discardButtons = new JButton[defaultNumberOfPlayers];

    // Boolean flag to notify that a player's turn has ended:

    boolean turnCompleted = false;

    // Linked lists of sets and runs laid down throughout the game:

    java.util.LinkedList <Run> laidRuns = new LinkedList();
    java.util.LinkedList <Set> laidSets = new LinkedList();
    int [] runOffset = new int[Card.suit.length];

    // Arrays of panels for sets and runs:

    SetPanel [] setPanels = new SetPanel[Card.rank.length];
    RunPanel [] runPanels = new RunPanel[((Card.rank.length + 1)/4)*Card.suit.length];

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
     * Creates a different HandPanel object for each player.
     * @param players the array of players in the game.
     */
    private void createHandPanels(Hand [] players)
    {
        JLabel playerLabel;
        for (int i = 0; i < players.length; i++) {

            // Generate the appropiate label for the panel:

            playerLabel = new JLabel("Player #" + (i + 1));

            // Create a list with the player's hand:

            playerLists[i] = new JList(players[i].hand);

            // Create buttons for each action that the player can do:

            pileButtons[i] = new JButton("Deal card from pile");
            pileButtons[i].addActionListener(this);

            deckButtons[i] = new JButton("Deal card from deck");
            deckButtons[i].addActionListener(this);

            runButtons[i] = new JButton("Lay down run in table");
            runButtons[i].addActionListener(this);

            setButtons[i] = new JButton("Lay down set in table");
            setButtons[i].addActionListener(this);

            layToRunButtons[i] = new JButton("Add card to run");
            layToRunButtons[i].addActionListener(this);

            layToSetButtons[i] = new JButton("Add card to set");
            layToSetButtons[i].addActionListener(this);

            discardButtons[i] = new JButton("Discard card");
            discardButtons[i].addActionListener(this);

            // Create new HandPanel object for the player:

            playerPanels[i] = new HandPanel(playerLabel, playerLists[i], pileButtons[i],
                    deckButtons[i], runButtons[i], setButtons[i], layToRunButtons[i],
                    layToSetButtons[i], discardButtons[i]);
        }
    }

    /**
     * Creates and manages the table for the Rummy game.
     */
    private RummyTable() {

        // Call GUI constructor and set layout and size:

        super("The Card Game of the Century");

        setLayout(new BorderLayout());
        setSize(1200,700);

        // The constructor should end once the GUI window is closed:

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Create, fill and shuffle the game's deck:

        deck = new Deck();
        deck.fillDeck(Card.suit, Card.rank);
        deck.shuffleDeck();

        // Create the game's pile and add the first card from the deck to it:

        pile = new Pile();
        pile.startPile(deck);

        // Create hands for both players, fill them and sort them:

        players = new Hand[defaultNumberOfPlayers];

        createPlayers(players);
        dealToPlayers(players);

        players[0].sort();
        players[1].sort();

        // Determine how many rows of set panels and runs have to be added,
        //   and construct corresponding JPanels:

        int numSets = Card.rank.length;

        int rowsSets = numSets/4;
        if (numSets % 4 != 0)
            rowsSets++;

        JPanel setPanel = new JPanel(new GridLayout(rowsSets, 1));

        int numRuns = ((Card.rank.length + 1)/4)*Card.suit.length;

        int rowsRuns = numRuns/6;
        if (numRuns % 6 != 0)
            rowsRuns++;

        JPanel runPanel = new JPanel(new GridLayout(rowsRuns, 1));

        // In a loop, create a JPanel for each 4 sets, then create and add
        //   a SetPanel for each set to it, and add the JPanel to meldPanel:

        JPanel setRow; JLabel setLabel;
        for (int i = 0, k = 0; i < rowsSets; i++) {
            setRow = new JPanel();
            for (int j = 0; j < 4 && k < numSets; j++, k++) {
                setPanels[k] = new SetPanel(Card.getRankIndex(Card.rank[k]));
                setLabel = new JLabel("Set of rank: " + Card.rank[k]);
                setRow.add(setLabel); setRow.add(setPanels[k]);
            }
            setPanel.add(setRow);
        }

        // In a loop, create a JPanel for each 6 runs, then create and add
        //   a RunPanel for each run to it, and add the JPanel to meldPanel:

        JPanel runRow; JLabel runLabel;
        for (int i = 0, k = 0; i < rowsRuns; i++) {
            runRow = new JPanel();
            for (int j = 0, h = 0; j < 6 && k < numRuns; h++, k++, j++) {
                h %= Card.suit.length;
                runPanels[k] = new RunPanel(Card.getSuitIndex(Card.suit[h]));
                runLabel = new JLabel("Run #" + (k + 1));
                runRow.add(runLabel); runRow.add(runPanels[k]);
            }
            runPanel.add(runRow);
        }

        // Add the set and run panels to the SOUTH and NORTH sections of the layout, respectively:

        add(setPanel, BorderLayout.SOUTH);
        add(runPanel, BorderLayout.NORTH);

        // For each player, generate a different HandPanel object:

        createHandPanels(players);

        // Create middle panel with 1 row and 3 columns:

        JPanel middle = new JPanel(new GridLayout(1,3));

        // Add player #1's panel to the middle panel:

        middle.add(playerPanels[0]);

        // Create deckPiles panel for deck and pile:

        deckPiles = new JPanel();
        deckPiles.setLayout(new BoxLayout(deckPiles, BoxLayout.Y_AXIS));
        deckPiles.add(Box.createGlue());

        // Create left panel and add label for pile:

        JPanel left = new JPanel();
        left.setAlignmentY(Component.CENTER_ALIGNMENT);

        pileLabel = new JLabel("Pile");
        pileLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        left.add(pileLabel);

        // Create image for pile and add it to the left panel:

        topOfPile = new JLabel();
        topOfPile.setIcon(pile.peekTopCard().getCardImage());
        topOfPile.setAlignmentY(Component.CENTER_ALIGNMENT);
        left.add(topOfPile);

        // Add left panel to the deckPiles panel:

        deckPiles.add(left);
        deckPiles.add(Box.createGlue());

        // Create right panel and add label for deck:

        JPanel right = new JPanel();
        right.setAlignmentY(Component.CENTER_ALIGNMENT);

        deckLabel = new JLabel("Deck");
        deckLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        right.add(deckLabel);

        // Create image for pile and add it to the left panel:

        deckPile = new JLabel();
        deckPile.setIcon(new ImageIcon(Card.directory + "b.gif"));
        deckPile.setAlignmentY(Component.CENTER_ALIGNMENT);
        right.add(deckPile);

        // Add left panel to the deckPiles panel:

        deckPiles.add(right);
        deckPiles.add(Box.createGlue());

        // Add deckPiles panel to middle panel:

        middle.add(deckPiles);

        // Add player #2's panel to the middle panel:

        middle.add(playerPanels[1]);

        // Add middle panel to the CENTER zone of the layout:

        add(middle, BorderLayout.CENTER);
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
    public void actionPerformed(ActionEvent e) {
        // Get source of the action:

        Object src = e.getSource();

        // Determine which one of the player's sent it:

        Hand player = null;
        int x = 0;
        for (int i = 0; i < players.length; i++)
            if (deckButtons[i] == src
                    || pileButtons[i] == src
                    || setButtons[i] == src
                    || runButtons[i] == src
                    || layToRunButtons[i] == src
                    || layToSetButtons[i] == src
                    || discardButtons[i] == src) {
                player = players[i];
                x = i;
            }

        // Print player's "turn" and current hand:

        System.out.println("\nPlayer #" + (x + 1) + "'s turn: ");
        System.out.println("\tHand at the start: " + player);

        // If it was sent from a deck button, deal card and add to
        //   corresponding player's hand.

        if (deckButtons[x] == src) {
            if (player.getNumberOfCards() <= 9) {
                Card card = deck.dealCard();
                if (card != null) {
                    player.addCard(card);
                    System.out.println("\tAdded card to the hand from the deck: " + card);
                }
                else System.out.println("\tThe deck is empty!");
                if (deck.getSizeOfDeck() == 0)
                    deckPile.setIcon(new ImageIcon(Card.directory + "blank.gif"));
            }
        }

        // If it was one of the pile buttons, remove the top card from
        //   the pile and add it to corresponding player's card.

        if (pileButtons[x] == src) {
            if (player.getNumberOfCards() <= 9) {
                Card card = pile.dealCard();
                if (card != null) {
                    Card topCard = pile.peek();
                    if (topCard != null)
                        topOfPile.setIcon(topCard.getCardImage());
                    else
                        topOfPile.setIcon(new ImageIcon(Card.directory + "blank.gif"));
                    player.addCard(card);
                    System.out.println("\tAdded card to the hand from the pile: " + card + "\n");
                }
                else System.out.println("\tThe pile is empty!");
            }
        }

        // If it was one of the lay-run buttons, remove the selected cards
        //    from the player's hand and add them to their corresponding set.

        if (runButtons[x] == src) {
            int [] cards = playerLists[x].getSelectedIndices();
            if (cards != null) {
                Run run = layRun(player, cards);
                if (run != null) {
                    for (int i = 0; i < run.getNumberOfCards(); i++) {
                        player.removeCard(run.getCard(i));
                    }
                    System.out.println("\tLaid run of suit " + run.getSuit() + ": " + run);
                }
            }
        }

        // If it was one of the lay-set buttons, remove the selected cards
        //   from the player's hand and add them to their corresponding set.

        if (setButtons[x] == src) {
            int [] cards = playerLists[x].getSelectedIndices();
            if (cards != null) {
                Set set = laySet(player, cards);
                if (set != null) {
                    for (int i = 0; i < set.getNumberOfCards(); i++) {
                        player.removeCard(set.getCard(i));
                    }
                    System.out.println("\tLaid set of rank " + set.getRank() + ": " + set);
                }
            }
        }

        // If it was one of the lay-to-run buttons, make sure that the selected
        //   card fits in one of the runs that has been laid down before, and add
        //   the card to it.

        if (layToRunButtons[x] == src) {
            int[] cards = playerLists[x].getSelectedIndices();
            if (cards.length == 1) {
                Card card = player.getCard(cards[0]);
                if (layToRun(card))
                    player.removeCard(card);
                    System.out.println("\tAdded card " + card + " to run of suit "
                                                       + card.getSuit() + ".");
            }
        }

        // If it was one of the lay-to-set buttons, make sure that the selected
        //   card fits in one of the sets that has been laid down before, and add
        //   the card to it.

        if (layToSetButtons[x] == src) {
            int[] cards = playerLists[x].getSelectedIndices();
            if (cards.length == 1) {
                Card card = player.getCard(cards[0]);
                if (layToSet(card))
                    player.removeCard(card);
                System.out.println("\tAdded card " + card + " to set of rank "
                        + card.getSuit() + ".");
            }
        }

        // If it was one of the discard buttons, remove the selected card
        //    from the player's hand and add it to the pile.

        if (discardButtons[x] == src) {
            int[] cards = playerLists[x].getSelectedIndices();
            if (cards.length == 1) {
                Card card = player.getCard(cards[0]);
                pile.addCard(card);
                player.removeCard(card);
                topOfPile.setIcon(card.getCardImage());
                System.out.println("\tDiscarded card to the pile: " + card);
                if (player.isEmpty()) System.out.println("\tHand now: " + player);
                else System.out.println("\tHand is now empty!");
                turnCompleted = true;
            }
        }
    }

    /**
     * Checks if a given combination of cards in a hand produces
     *   a set, and, if it does, "lays it down" by updating the
     *   corresponding GUI images and adding it to the list
     *   of laid down sets.
     * @param hand the hand from which the cards will be extracted.
     * @param cards the indexes of the cards to be evaluated.
     * @return whether or not the given cards produce a set.
     */
    private Set laySet(Hand hand, int [] cards)
    {
        if (cards.length >= 3) {

            Hand possibleSet = new Hand(cards.length);
            char[] ranks = new char[cards.length];

            Card card = null;
            for (int i = 0; i < cards.length; i++) {
                card = hand.getCard(cards[i]);
                ranks[i] = card.getRank();
                possibleSet.addCard(card);
            }

            Set set = possibleSet.removeSet(ranks, Card.suit);

            if (set != null && set.getNumberOfCards() == cards.length) {
                setPanels[set.getRankIndex()].data = set;
                int suit;
                for (int i = 0; i < set.getNumberOfCards(); i++) {
                    card = set.getCard(i); suit = Card.getSuitIndex(card.getSuit());
                    setPanels[set.getRankIndex()].array[suit].setIcon(card.getCardImage());
                }
                laidSets.add(set);
                return set;
            }
        }
        return null;
    }

    /**
     * Checks if a given combination of cards in a hand produces
     *   a run, and, if it does, "lays it down" by updating the
     *   corresponding GUI images and adding it to the list
     *   of laid down runs.
     * @param hand the hand from which the cards will be extracted.
     * @param cards the indexes of the cards to be evaluated.
     * @return whether or not the given cards produce a run.
     */
    private Run layRun(Hand hand, int [] cards)
    {
        if (cards.length >= 3) {

            Hand possibleRun = new Hand(cards.length);
            char[] suits = new char[cards.length];

            Card card = null;
            for (int i = 0; i < cards.length; i++) {
                card = hand.getCard(cards[i]);
                suits[i] = card.getRank();
                possibleRun.addCard(card);
            }

            Run run = possibleRun.removeRun();

            if (run != null && run.getNumberOfCards() == cards.length) {
                runPanels[run.getSuitIndex()].data = run;
                card = run.getFirstCard();
                runPanels[run.getSuitIndex() + runOffset[run.getSuitIndex()]].
                        array[0].setIcon(card.getCardImage());
                card = run.getLastCard();
                runPanels[run.getSuitIndex() + runOffset[run.getSuitIndex()]].
                        array[1].setIcon(card.getCardImage());
                laidRuns.add(run);
                runOffset[run.getSuitIndex()] += 4;
                return run;
            }
        }
        return null;
    }

    /**
     * Lays the selected card to a run, if there exists a run to
     *   which it can be added.
     * @param card the card to be laid down to the run.
     * @return if it was added to a run or not.
     */
    private boolean layToRun(Card card)
    {
        if (!laidRuns.isEmpty()) {
            Run run;
            for (int i = 0; i < laidRuns.size(); i++) {
                run = laidRuns.get(i);
                if (card.sameSuit(run.getFirstCard())
                    && card.offsetRank(run.getFirstCard()) == -1) {
                    run.addAsFirst(card);
                    runPanels[run.getSuitIndex()].array[0].setIcon(card.getCardImage());
                    return true;
                }
                if (card.sameSuit(run.getLastCard())
                    && card.offsetRank(run.getLastCard()) == 1) {
                    run.addAsLast(card);
                    runPanels[run.getSuitIndex()].array[1].setIcon(card.getCardImage());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Lays the selected card to a set, if there exists a set to
     *   which it can be added.
     * @param card the card to be laid down to the set.
     * @return if it was added to a set or not.
     */
    private boolean layToSet(Card card)
    {
        if (!laidSets.isEmpty()) {
            Set set;
            for (int i = 0; i < laidSets.size(); i++) {
                set = laidSets.get(i);
                if (set.getNumberOfCards() == 3
                    && card.sameRank(set.getCard(0))) {
                    set.addCard(card);
                    set.sort();
                    setPanels[set.getRankIndex()].array[set.findCard(card)].setIcon(card.getCardImage());
                    return true;
                }
            }
        }
        return false;
    }
}

class HandPanel extends JPanel {

    public HandPanel(JLabel name, JList hand, JButton pile, JButton deck,
                     JButton run, JButton set, JButton layRun, JButton laySet,
                     JButton discard) {

        // setLayout(new GridLayout(, 2));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(Box.createGlue());
        JPanel handList = new JPanel();
        handList.setLayout(new BoxLayout(handList, BoxLayout.X_AXIS));
        handList.add(Box.createGlue());
        handList.add(name);
        handList.add(Box.createGlue());
        hand.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        hand.setVisibleRowCount(1);
        handList.add(hand);
        handList.add(Box.createGlue());
        this.add(handList);
        handList.setAlignmentY(Component.CENTER_ALIGNMENT);
        handList.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(Box.createGlue());

        JPanel handButtons = new JPanel();
        handButtons.setLayout(new GridLayout(4, 2));

        pile.setAlignmentX(Component.CENTER_ALIGNMENT);         handButtons.add(pile);
        deck.setAlignmentX(Component.CENTER_ALIGNMENT);         handButtons.add(deck);
        run.setAlignmentX(Component.CENTER_ALIGNMENT);          handButtons.add(run);
        set.setAlignmentX(Component.CENTER_ALIGNMENT);          handButtons.add(set);
        layRun.setAlignmentX(Component.CENTER_ALIGNMENT);       handButtons.add(layRun);
        laySet.setAlignmentX(Component.CENTER_ALIGNMENT);       handButtons.add(laySet);
        discard.setAlignmentX(Component.CENTER_ALIGNMENT);      handButtons.add(discard);

        this.add(handButtons);
    }
}

class SetPanel extends JPanel {

    protected Set data;
    JLabel [] array = new JLabel[Card.suit.length];

    public SetPanel(int index)
    {
        super();
        data = new Set(Card.rank[index]);

        for(int i = 0; i < array.length; i++){
            array[i] = new JLabel();
            array[i].setIcon(new ImageIcon(Card.directory + "blank.gif"));
            add(array[i]);
        }
    }
}

class RunPanel extends JPanel {

    protected Run data;
    JLabel [] array = new JLabel[2];

    public RunPanel(int index)
    {
        super();
        data = new Run(Card.suit[index]);

        for(int i = 0; i < array.length; i++){
            array[i] = new JLabel();
            array[i].setIcon(new ImageIcon(Card.directory + "blank.gif"));
            add(array[i]);
        }
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }
}
