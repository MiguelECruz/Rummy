package proj2;

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

    // Array of total points accumulated by each player:

    int [] points = new int[playerNumber];

    // Array of set flags (true only for each set that
    //   has been laid down so far):

    Set [] laidSets = new Set[Card.rank.length];

    // Array of cards that each player removed last:

    Card [] lastDiscarded = new Card[playerNumber];

    // Linked list of tuples with card limits of each run
    //   that has been laid down so far:

    java.util.LinkedList <CardTuple> laidRuns = new java.util.LinkedList <> ();

    public Proj2(String [] args, RummyTable table) {

        // Initialize table, deck, pile and player array:

        this.table = table;
        this.deck = table.deck;
        this.pile = table.pile;
        this.players = table.players;

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
                laidRuns.add(new CardTuple(run.getCard(0), run.getCard(run.getNumberOfCards() - 1)));
            }
            else
                System.out.println("\tFound no runs in the hand.");

            // Search for sets in the player's hand, and remove the first
            //   set, if any are found:

            set = players[turn].removeSet();
            if (set != null) {
                System.out.println("\tLaid set of rank " + set.getRank() + ": " + set);
                laidSets[Card.getRankIndex(set.getRank())] = set;
            }
            else
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
            if (card != null) System.out.println("\tDiscarded card to the hand: " + card);
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
        if (!pile.isEmpty() && (lastDiscarded != null || !pile.peek().equals(lastDiscarded[index]))) {
            Card topCard = pile.peekTopCard();
            boolean greaterRank;
            boolean runPotential;
            for (int i = 0; i < player.getNumberOfCards(); i++) {
                greaterRank = Card.getRankIndex(player.getCard(i).getRank())
                            > Card.getRankIndex(topCard.getRank());
                runPotential = Card.getRankIndex(player.getCard(i).getRank()) + 1
                            == Card.getRankIndex(topCard.getRank())
                            && Card.getSuitIndex(player.getCard(i).getSuit())
                            == Card.getSuitIndex(topCard.getSuit());
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
            Card discardedCard = null;
            int cardRank; int topRank = 0;
            for (int i = 0; i < player.getNumberOfCards(); i++) {
                cardRank = Card.getRankIndex(player.getCard(i).getRank());
                if (cardRank > topRank) {
                    topRank = cardRank;
                    discardedCard = player.getCard(i);
                }
            }
            if (topRank == 0) discardedCard = player.getCard(0);
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
        for (int i = laidSets.length - 1; i >= 0; i--)
            if (laidSets[i] != null && laidSets[i].getNumberOfCards() == 3)
                for (int j = 0; j < player.getNumberOfCards(); j++)
                    if (Card.getRankIndex(player.getCard(j).getRank()) == i) {
                        discardedCard = player.removeCard(j);
                        laidSets[i].addCard(discardedCard);
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
        for (int i = 0; i < laidRuns.size(); i++) {
            runFirstCard = laidRuns.get(i).element1;
            runLastCard  = laidRuns.get(i).element2;
            for (int j = 0; j < player.getNumberOfCards(); j++) {
                discardedCard = player.getCard(j);
                if (Card.getSuitIndex(discardedCard.getSuit())
                        == Card.getSuitIndex(runFirstCard.getSuit())) {
                    boolean addAsFirst = Card.getRankIndex(discardedCard.getRank())
                            == Card.getRankIndex(runFirstCard.getRank()) - 1;
                    boolean addAsLast  = Card.getRankIndex(discardedCard.getRank())
                            == Card.getRankIndex(runLastCard.getRank()) + 1;
                    if (addAsFirst || addAsLast) {
                        discardedCard = player.removeCard(j);
                        if (addAsFirst) laidRuns.get(i).element1 = discardedCard;
                        else laidRuns.get(i).element2 = discardedCard;
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

        RummyTable table = RummyTable.getTable();
        table.setVisible(false);

        Proj2 rummyGame = new Proj2(args, table);
    }
}
