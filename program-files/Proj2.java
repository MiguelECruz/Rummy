/*
Project Rummy card game: Class RummyTable
- Student/programmer:   Miguel E. Cruz Molina
- Student number:       801-16-1956
- Course and section:   CCOM 4029-001
- Instructor:           Dr. Patricia Ordóñez
- Date of last edit:    5/18/2021

Summary: This file defines public class Proj2, which creates a
  GUI for a version of the Rummy card game (using class RummyTable)
  and activates and follows the game procedure. For this purpose,
  it uses the deck, discard pile and player hands created by the
  RummyTable class. It then prints the procedure of the game,
  starting by relaying an introduction and a brief description
  of its ending conditions, and then by looping through the players
  using a turn mechanism until either the deck has been emptied
  out or one of the players has managed to remove all of their
  cards. Finally, it announces the winner of the game or if it
  ended in a tie.
*/

package proj2;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Proj2 {

    // Default number of players:

    int playerNumber = 2;

    // The game's table itself:

    RummyTable table;

    // Deck and pile used during the game:

    Deck deck;
    Pile pile;

    // Array of player hands:

    Hand [] players;

    // Array of boolean flags to identify automatized players:

    // boolean [] automatizedPlayer = new boolean[playerNumber];

    // Array of total points accumulated by each player:

    int [] points = new int[playerNumber];

    // Linked lists of laid down runs and sets:

    java.util.LinkedList <Run> laidRuns;
    java.util.LinkedList <Set> laidSets;

    // Array of cards that each player removed last:

    Card [] lastDiscarded = new Card[playerNumber];

    /**
     * Follows the procedure of the game.
     * @param args the arguments inputted by the person who wants to compile the game.
     * @param table the unique table instance of the RummyTable used in the game.
     * @throws InterruptedException if the game process is interrupted.
     */
    public Proj2(String [] args, RummyTable table) throws InterruptedException {

        // Establish which players are automatized and which aren't:

        // automatizedPlayer[1] = true;

        // Initialize table, deck, pile and player array, and connect meld linked
        //   list with the table's:

        this.table = table;
        this.deck = table.deck;
        this.pile = table.pile;
        this.players = table.players;
        this.laidRuns = table.laidRuns;
        this.laidSets = table.laidSets;

        // Change card capacity of each player's hand, to account for
        //   order of card addition and removal:

        for (int i = 0; i < playerNumber; i++)
            players[i].setHandCapacity(players[i].getHandCapacity() + 1);

        // Print introduction to the game:

        System.out.println("\nWelcome to Miguel's Rummy Table!"

                         + "\n\n" + playerNumber + " players battle it out for a chance at redemption,"
                         + "\nor something silly like that, who knows."

                         + "\n\nThe player that discards all of their cards first, or has the lowest"
                         + "\naccumulated amount of points in their hand by the end, wins."

                         + "\n\nLet the game commence!");

        // Print initial hands:

        for (int i = 0; i < playerNumber; i++) {
            System.out.print("\nPlayer #" + (i + 1) + "'s initial hand: " + players[i]);
        }

        // Establish starting variables for game procedure (turn mechanism,
        //   card, set and run variables, and flags for checking if the game
        //   has finished):

        int turn = 0; // Whose turn is it now?

        boolean emptyHand = false;          // Is the hand of the player who just completed their turn now empty?
        boolean emptyDeck = deck.isEmpty(); // Is the deck of the Rummy table itself now empty?

        Card card;  // Card variable for almost all cards.
        Card top;   // Card variable for card at the top of the pile.
        Set set;    // Set variable for all sets.
        Run run;    // Run variable for all runs.
        MyStack <Card> laidToSet;   // MyStack variable for cards added to sets.
        MyStack <Card> laidToRun;   // MyStack variable for cards added to runs.

        // Carry on and print the game procedure:

        System.out.print("\n");
        while (!emptyHand && !emptyDeck) {

            TimeUnit.SECONDS.sleep(3);

            // State whose turn is it and display initial hand:

            System.out.println("\nPlayer #" + (turn + 1) + "'s turn: ");
            System.out.println("\tHand at the start: " + players[turn]);

            // "Peek" at card at the top of the start:

            top = pile.peek();
            if (top != null)
                System.out.println("\tPeeked pile's top card: " + top);
            else
                System.out.println("\tThe discard pile is empty!");


            // Generate card to be added (i.e., pick card from the deck).
            //   add it to the player's hand and print its value:

            card = addCard(players[turn], turn);
            if (card != null) {
                System.out.print("\tAdded card to the hand ");
                if (card == top) System.out.print("from the pile: " + card + "\n");
                else System.out.print("from the deck: " + card + "\n");
            }

            // Search for runs in the player's hand, and remove the first
            //   run, if any are found:

            run = players[turn].removeRun();
            if (run != null) {
                System.out.println("\tLaid run of suit " + run.getSuit() + ": " + run);
                laidRuns.add(run);
                card = run.getFirstCard();
                table.runPanels[run.getSuitIndex() + table.runOffset[run.getSuitIndex()]].
                        array[0].setIcon(card.getCardImage());
                card = run.getLastCard();
                table.runPanels[run.getSuitIndex() + table.runOffset[run.getSuitIndex()]].
                        array[1].setIcon(card.getCardImage());
                laidRuns.add(run);
                table.runOffset[run.getSuitIndex()] += 4;
            } else
                System.out.println("\tFound no runs in the hand.");

            // Search for sets in the player's hand, and remove the first
            //   set, if any are found:

            set = players[turn].removeSet();
            if (set != null) {
                System.out.println("\tLaid set of rank " + set.getRank() + ": " + set);
                laidSets.add(set);
                int suit;
                for (int i = 0; i < set.getNumberOfCards(); i++) {
                    card = set.getCard(i); suit = Card.getSuitIndex(card.getSuit());
                    table.setPanels[set.getRankIndex()].array[suit].setIcon(card.getCardImage());
                }
            } else
                System.out.println("\tFound no sets in the hand.");

            // Search for cards that fit in pre-removed ("laid down") sets
            //   or runs, and add them to their corresponding melds.

            laidToRun = layToRun(players[turn]);
            while (!laidToRun.isEmpty()) {
                System.out.println("\tAdded card " + laidToRun.top()
                        + " to run of suit " + laidToRun.top().getSuit() + ".");
                laidToRun.pop();
            }

            laidToSet = layToSet(players[turn]);
            while (!laidToSet.isEmpty()) {
                System.out.println("\tAdded card " + laidToSet.top()
                        + " to set of rank " + laidToSet.top().getRank() + ".");
                laidToSet.pop();
            }

            // Select card from the player's hand that will be discarded,
            //   discard it, add it to the pile and print its value:

            card = removeCard(players[turn], turn);
            if (card != null) System.out.println("\tDiscarded card to the pile: " + card);
            if (!players[turn].isEmpty()) System.out.println("\tHand now: " + players[turn]);
            else System.out.println("\tHand is now empty!");

            // Update values of boolean flags for checking if the conditions
            //   for the game's termination have become true:

            emptyHand = players[turn].isEmpty();
            emptyDeck = deck.isEmpty();

            // Whose turn is next (if the game hasn't ended)?:

            turn = (turn + 1) % playerNumber;

        }

        // We assume the player whose turn was last was the winner
        //   (this is false only if the deck was emptied and there
        //   are no empty player hands):

        int winner = (turn + playerNumber - 1) % playerNumber;
            // (Added playerNumber to turn in order to avoid mistakes
            //   stemming from the way Java's modulo operator works
            //   over negative numbers.)

        // If the game ended because the deck was emptied out before any
        //   of the players could empty out their hand, evaluate each
        //   player's accumulated points, display said point and look
        //   for a tie:

        boolean tie = false;    // Flag for if the game has ended in a tie.

        if (!emptyHand) {

            System.out.println("\nThe deck was emptied before any player could discard"
                             + "\nall of their cards! Time to count each player's"
                             + "\ntotal points.\n");

            int minPoints = 90;     // Minimum accumulated amount of points registered so far
                                    //   (initialized to 90 == 9 card worth 10 points).

            for (int i = 0; i < playerNumber; i++) {

                points[i] = players[i].evaluateHand();

                System.out.println("Player #" + (i + 1)
                                 + "'s total points: " + points[i]);

                if (points[i] < minPoints) {
                    tie = false;
                    minPoints = points[i];
                    winner = i;
                }
                else if (points[i] == minPoints) {
                    tie = true;
                    winner = -1;
                }
            }
        }

        // Print all the runs and sets which were laid down during the game:

        // System.out.println("\nRuns laid down throughout the game:");
        // System.out.println(laidRuns);
        // System.out.println("Sets laid down throughout the game:");
        // System.out.println(laidSets);

        // Print who's the winner, or if the game ended in a tie:

        if (tie)
            System.out.println("\nIt's a tie!");
        else
            System.out.println("\nPlayer #" + (winner + 1) + " wins!");

    }

    /**
     * Adds a card dealt from the deck or from the pile onto the specified
     *   player's hand. Additional heuristics determine which one of the two
     *   the player ends up selecting the new card:
     *      1. Always consider selecting from the pile first.
     *      2. Add a card from the pile only if its rank is less or equal to
     *           the ranks of one other preexisting card in the hand.
     *      3. As an exception to (2.), add a card from the pile if there is
     *           a good chance it could form a run with the card that has the
     *           highest rank in the hand.
     * To avoid infinite loops while following these rules, players are
     *   forbidden from picking the top card of a pile if it's the same
     *   card they last discarded.
     * @param player the player's hand to which the card will be added.
     * @return the added card.
     */
    private Card addCard(Hand player, int index) {
        Card addedCard;
        if (!pile.isEmpty() && !pile.peek().equals(lastDiscarded[index])) {
            Card topCard = pile.peekTopCard();
            boolean greaterRank;
            boolean runPotential;
            for (int i = 0; i < player.getNumberOfCards(); i++) {
                greaterRank = player.getCard(i).offsetRank(topCard) > 0;
                runPotential = player.getCard(i).sameSuit(topCard)
                            && player.getCard(i).offsetRank(topCard) == -1;
                if (greaterRank || runPotential) {
                    addedCard = pile.dealCard();
                    player.addCard(addedCard);
                    return addedCard;
                }
            }
        }
        addedCard = deck.dealCard();
        player.addCard(addedCard);
        return addedCard;
    }

    /**
     * Remove a specific card from the specified player's hand.
     *   Following the logic of the game, this method selects the
     *   card with the highest rank and no run or set potential.
     * @param player the player's hand from which the card will be removed.
     * @return the removed card.
     */
    private Card removeCard(Hand player, int index) {
        if (!player.isEmpty()) {
            Card discardedCard = player.getCard(0);
            for (int i = 0; i < player.getNumberOfCards(); i++) {
                if (player.getCard(i).offsetRank(discardedCard) > 0) {
                    discardedCard = player.getCard(i);
                }
            }
            discardedCard = player.removeCard(discardedCard);
            table.pile.addCard(discardedCard);
            lastDiscarded[index] = discardedCard;
            return discardedCard;
        }
        return null;
    }

    /**
     * Searches through the given player's hand for cards that
     *   can be added to a preexisted set, and removes them
     *   from the hand.
     * @param player player the player's hand which will be searched.
     * @return the cards which were removed and will be subsequently
     *   added to their respective sets.
     */

    private MyStack <Card> layToSet(Hand player) {
        MyStack <Card> discardedCards = new MyStack<>();
        Card discardedCard;
        for (Set laidSet : laidSets)
            if (laidSet.getNumberOfCards() == 3)
                for (int j = 0; j < player.getNumberOfCards(); j++)
                    if (Card.getRankIndex(player.getCard(j).getRank()) == laidSet.getRankIndex()) {
                        discardedCard = player.removeCard(j);
                        laidSet.addCard(discardedCard);
                        table.setPanels[laidSet.getRankIndex()].
                                array[laidSet.findCard(discardedCard)].
                                setIcon(discardedCard.getCardImage());
                        discardedCards.push(discardedCard);
                    }
        return discardedCards;
    }

    /**
     * Searches through the given player's hand for cards that
     *   can be added to a preexisted run, and removes them
     *   from the hand.
     * @param player player the player's hand which will be searched.
     * @return the cards which were removed and will be subsequently
     *   added to their respective runs.
     */

    private MyStack <Card> layToRun(Hand player) {
        MyStack <Card> discardedCards = new MyStack<>();
        Card discardedCard;
        Card runFirstCard;
        Card runLastCard;
        for (Run laidRun : laidRuns) {
            runFirstCard = laidRun.getFirstCard();
            runLastCard = laidRun.getLastCard();
            for (int j = 0; j < player.getNumberOfCards(); j++) {
                discardedCard = player.getCard(j);
                if (discardedCard.sameSuit(runFirstCard)) {
                    boolean addAsFirst = discardedCard.offsetRank(runFirstCard) == -1;
                    boolean addAsLast = discardedCard.offsetRank(runLastCard) == 1;
                    if (addAsFirst || addAsLast) {
                        discardedCard = player.removeCard(j);
                        if (addAsFirst) {
                            laidRun.addAsFirst(discardedCard);
                            table.runPanels[laidRun.getSuitIndex()].
                                    array[0].setIcon(discardedCard.getCardImage());
                        }
                        else {
                            laidRun.addAsLast(discardedCard);
                            table.runPanels[laidRun.getSuitIndex()].
                                    array[1].setIcon(discardedCard.getCardImage());
                        }
                        discardedCards.push(discardedCard);
                    }
                }
            }
        }
        return discardedCards;
    }

    /**
     * Simulates the Rummy game by retrieving the table and
     *   creating an instance of the game's procedure with it.
     * @param args the arguments needed to run the game.
     */
    public static void main(String [] args) {
        try {
            RummyTable table = RummyTable.getTable();
            table.setVisible(false);

            Proj2 rummyGame = new Proj2(args, table);
        }
        catch (InterruptedException e) {
            System.out.println("Interrupted while running");
        }
    }
}
