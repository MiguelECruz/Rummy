# Project #2: Rummy card game and GUI 

Student: Miguel E. Cruz Molina (801-16-1956)

Course: CCOM 4029-001 

Instructor: Dr. Patricia Ordóñez Franco

Date: 5-16-2020

Version of Java used: Java SE Development Kit 15.0.2

## Introductory description
This project consists of a series of Java programs that aim to function collectively as a simple implementation of a version of the Rummy card game between two or more players. Said implementation is accompanied by a Graphical User Interface (GUI), which allows the user to play a Rummy round against an automated player. The project is more broadly (i.e., from an academic perspective, and also as an intellectual pursuit) intended to illustrate a diverse array of concepts, mechanisms and linguistic and programming conventions from Object-Oriented Analysis and Design, exemplified here by Java, itself a high-level O-O language. More concretely, my work for this assignment, and the evidence thereof, can be divided into three relatively distinct parts: the design document for this project, submitted to the instructor in PDF form at the end of April, 2021; the 18 different Java files containing all 11 classes and 7 interfaces used in the development of the game; and an additional text file (`p2-output.txt`) submitted as evidence of those files' functionality, illustrating the output generated during the game by two automatized players. Without further ado, the following sections of this document delve into the structure and functionality of the second of these parts, which comprises the core of this project and repository. 

## The game's rules
With the intent of eliminating redundancy and providing useful and unambiguous context for subsequent sections, I've taken the liberty of quoting the Dr. Ordóñez description of the game's rules and mechanics in its entirety:

> These rules are a slight adaptation of standard Rummy rules. The objective of this game is to make melds (sets and runs) of similar cards and lay them on the table until all cards in your hand have been disposed of. A set is three or more cards of the same rank, such as three aces or four sevens. A run is three or more sequential cards of the same suit, such as the three-four-five of clubs or the jack-queen-king of diamonds. The ace is always low.
> Each player is dealt nine cards from the stock pile (Deck). The next card is turned face-up to initiate the discard pile (Stack). The deck is placed face-down on the table. In each turn, a player draws one card from either the Deck or Stack. They may optionally lay sets, runs on the table or place the card on Stack. They may also optionally lay cards that fit in sets or runs already on the table. The turn ends with a discard. If a player is able to lay all remaining cards on the table at the end of a turn, the discard is optional.
> The game is over when one player is out of cards or the stock is exhausted. At this point, players count the points remaining in their hands. Aces are worth one, face cards are worth ten, and all other cards are worth their face value. Lowest value hand wins. Ties are possible.

The following section discusses the contents of each java file at length, and how they relate to these rules and to the object constraints specified by the professor in the rest of the instructions document.

## Project files
As mentioned above, the coding aspect of the project is comprised of 18 Java files, 11 of which are classes, with the remaining 7 being interface files. The initial template devised for some of these files (`Card.java`, `CardInterface.java`, `Deck.java`, `DeckInterface.java`, `Hand.java`, `HandInterface.java`, `Set.java`, `SetInterface.java`, and early versions of classes `Table`, `SetPanel` and `HandPanel`, all contained in a single java file) was designed and written by Dr. Ordóñez and Dr. John K. Estell. The other 10 were primarily designed by me, although following the standards set by the project authors' in regards to general structure and careful documentation. The contents of the 18 files are thus:

* **`Card.java` and `CardInterface.java`**: This class and corresponding interface describe and implement the cards in the game. Each card has a suit value (`C` for clubs, `D` for diamonds, `H` for hearts, `S` for spades) and a rank value (`A` for ace, `T`, `J`, `Q` and `K` for ten, jack, queen and king respectively, and each digit from 2 to 9 for itself). It also counts with an image (in the form of an `ImageIcon` object), pre-assigned by the instructors, and with methods to get and set the card's fields, as well as the index of the suits and ranks included in a predefined array, and to express the card as a `String` and compare it to other cards. Special attention was put into excluding any static fields and methods from the interface, to allow it to be potentially used for cards with different ranks and suits to the French-suited card pack.

* **`Deck.java` and `DeckInterface.java`**: This class and interface represent the deck in the game, from which all players are dealt their cards throughout the game. The deck structure itself is implemented through a `LinkedList` object. The deck class contains methods for: filling it with all possible combinations of two arrays of ranks and suit; shuffling it; adding a card (to the bottom or to a specific index) to it; picking a card (dealt from the top, removed from the button or from a specific index) from it; peeking the card at its top; emptying it out, and then filling it again; expressing it as a `String`; and checking whether or not it's empty, and how many cards it has. It's important to note that the deck eminently works as a queue, and that some of these methods were added for uniformity and consistency rather than for practical in-game reasons.

* **`Pile.java` and `PileInterface.java`**: The `Pile` class, as its name suggests, implements the discard pile in the game, to which all players discard a card per turn and from which they may add one card to their hand instead of being dealt one from the deck. The main difference between the `Deck` and the `Pile` classes, which share most if not all of their methods, is that, whereas the former is implemented as a queue, the latter is basically a stack, as cards can be added or dealt from it only from its top. This is especially important because, as the project's object requirements instruct, this class not only implements `PileInterface`, but it also extends a Generic class, the one in **`MyStack.java`**, which is wholly original and includes methods for pushing and popping (and peeking at, in the case of the one on the top) elements, and for printing, clearing and verifying the emptiness of a `LinkedList` with the stack's contents.

* **`Hand.java`** and **`HandInterface.java`**: Perhaps the most important class in the game, the most complex and the most heavily modified, each object of `Hand` simulates and implements the individual functionality of a player's hand and the player themself. The hand of cards itself is implemented with an `ArrayList` object, and includes many overridden methods for adding, finding, getting the index of, checking if they're included, removing and replacing cards, as well as sets and runs. It also comes equipped with a sorting method, a method for evaluating the amount of points accumulated by the hand's proprietor, and other unique methods for getting and setting the hand's capacity and truncating it, to name a few.

* **`Set.java`**, **`SetInterface.java`**, **`Run.java`** and **`RunInterface.java`**: These two pairs of files implement the sets and runs in the game, respectively. These two categories, known as melds, help accelerate the resolution of the game by providing each player with alternative ways to discard their cards. In addition to `Set` also extending the `MyStack` class, a project requirement, both classes have enough specific similarities (there needs to be a method to check if the set or run is full) to justify creating a new interface, **`MeldInterface.java`**, of which `SetInterface.java` and `RunInterface.java` are both subclasses. Because both classes also share a lot of methods with the `Hand` class, `MeldInterface` has been set to be a subclass of `HandInterface` as well. This means that some method headers from `HandInterface` had to be removed, in the interest of only including descriptions of methods which could be practically used by both `Hand` and meld objects. Further modifications could improve the ways these methods are distributed among all files.

* **`RummyTable.java`**: Originally called, `Table.java`, this is the file that implements the game's GUI. It also creates the game's deck, pile and player hands, fills and shuffles the deck, and fills and sorts the player hands. This class follows the Singleton design pattern, as established in the resources discussed in class: only one instance of `RummyTable` is ever created, and this instance is used by the proper gameplay class (`Proj2`) to manage all the elements and procedures related to the game. 

* **`CardTuple.java`** and **`MyTuple.java`**: Part of the design of the mechanisms for checking what the first and last cards of a run are (taking into consideration that a run can be added to on both sides) originally involved saving both cards as values in an ordered pair. As a consequence, since Java doesn't count with a tuple class in the Collections Framework, I decided to create my own version of a Generic tuple class `MyTuple`, and build another class, `CardTuple`, which constructs a `MyTuple` object for two cards.

* **`Proj2.java`**: The code in this file comprises the game mechanism itself, starting with retrieving the table instance from `RummyTable` and then following the game procedure. Being by far the most complex file in the system, the inner working of this procedure will be described in more detail in the next section. 

# Project compilation and main game procedure

The game can be activated simply by running the `main` method in the `Proj2.java`. As the file is presently configured, the GUI is set to be visible and is fully functional, but it has no practical implications for the game, since **the gameplay itself has only been implemented for two automatized players**, which play against each other, and whose moves and turns are printed as text. An example of this output is shown in the `p2-output.txt` file in this repository. Further changes will be made to the `Proj2.java` and `RummyTable.java`files to allow the user to play against an automatized player, and to be able to use the GUI during said game, but this work is incomplete so far.

As mentioned above, the `main` method starts by directly retrieving the unique instance of `RummyTable`, thus generating the game's deck, pile and player hands, and activating it's own constructor, which carries out the game procedure itself. Said procedure can be enumerated in the following steps:

1. Print an introduction to the game, which welcomes the user and explains which conditions lead to the end of the game (one player has to discard all of their cards, or have the least amount of points by the end if the deck is exhausted first).
2. Print the initial contents of each player's hand.
3. Enter a loop which simulates each turn and move in the game until either ending condition has been reached.
4. Determine who the winner was and print it. This is done by first checking if the hand of the last player whose turn it was before the move loop ended is empty or not. 
    1. If it is, then it's evident that they managed to discard all of their remaining cards in that turn, and thus they are the winner. 
    2. If it isn't, then the game ended because all of the cards in the deck were dealt. The total amount of points accumulated by each player is calculated and displayed, and the player with the least amount of points wins, unless there's a tie. In all possible cases, the result is printed out at the end.

In the turn loop, for each iteration (a move by the player whose turn it is at that moment), the player (in reality a `Hand` object),

1. Displays their ID in the game and the contents of their hand.
2. Peeks at the card at the top of the discard pile.
3. Algorithmically decides, based on the value of that card, whether to be dealt a card from the deck or from the pile, and adds the selected card to their hand. This is done through an `addCard` method in the file.
4. Checks for melds in the hand (runs first, since they can be potentially added to throughout the game until their respective suit is completed) and "lays them down" on the table (removes them from the hand and adds them to an array of sets or runs created by the class). This is done through methods `removeRun` and `removeSet` of the `Hand` class.
5. Removes cards from the hand and adds them to a corresponding run or set if such an appropriate meld has already been laid down. This is done with methods `laidToRuns` and `laidToSets`, both implemented in the `Proj2` class.
6. Selects and discards a remaining card from the hand, if it isn't yet empty. This is done through method `removeCard`.
7. Displays the resulting, modified hand, having presumably added a new card and removed a few more during the last few steps.
8. Passes the turn to the next player, by incrementing a `turn` variable.

This steps fundamentally make up the core of the game and are enough to implement automatized players with relative ease. They, however, do not guarantee said players will play smartly, or make sensical decisions regarding the processes of selecting piles to add cards from, or cards to be discarded from the hand. To improve this aspect of the gameplay, and entertaining potential real-life users with the opportunity of playing against an antagonist with a relative amount of skill at this version of Rummy, I followed the instructor's recommendation of implementing 4 additional heuristics as part of steps #3 and #6 of the turn loop:

* **Heuristic #1**: *Always discard the card with the highest rank*. This and similar procedures implemented for laying down melds guarantee that cards with higher ranks (and a higher point value) get weeded out from the hand before cards with lower point values, thus, hopefully, decreasing the hand's total value in case the deck gets emptied out before any of the players has a chance to discard all their cards.

* **Heuristic #2**: *Always consider adding each new card from the pile instead of dealing it from the deck*. A turn spent adding a card from the pile is a turn spent preventing the game from ending by deck exhaustion. This consequently buys the player another opportunity to either decrease their hand's total point count or get the card they need to empty it out. To prevent entering infinite loops (e.g., the king of spades, `KS`, gets discarded, and each player successively adds it and discards it from their hand for all eternity), an additional mechanism was added so that the player can only add a card from the stack if it isn't the one they discarded on their last turn.

* **Heuristic #3**: *Add a card from the pile only if there are cards of equal or greater rank in the player's hand*. Again, just as Heuristic #1, this is done in the interest of preemptively weeding out cards with higher point values. Additionally, in combination with Heuristic #2, it betters the chances of finding the card or cards necessary for laying down a set or a run during the mid- and endgames, since, on one hand, because as the population of cards of a hand within a limited range of ranks set by the pile's top card increases, the probability of finding a meld to lay down among them does too, and on the other hand, adding an unusable card to then immediately discard it is wasting a turn, better spent by dealing a potentially better card from the deck. This rule has one exception, discussed in Heuristic #4.

* **Heuristic #4**: *Also add the top card from the pile if the card with its same suit and the rank that's just once removed from the top card's is already in the hand*. This gives the player another opportunity to discard a run, concretely the one started by their highest ranked card (e.g. '6C'), the Pile's top card ('7C'), and the remaining card ('5C'), if they have it. If they don't, then the turn is wasted, a potential risk, but not a grave one.

# Extra credit specifications

The following tasks and mechanisms were implemented from among the extra credit items that are listed in the instructions document:

* Modified the table simulated by the `RummyTable` class so that the game began with one card added to the pile from the deck.
* Included implementations of runs in the game, by including files `Run.java` and `RunInterface.java`.
* Made both `SetInterface` and `RunInterface` subclasses of another interface `MeldInterface`.
* Implemented 4 heuristics for improving performance of automated play (described above). 

# Collaboration disclaimer and acknowledgements

As specified in the instructions document, this project is an open assignment, which means that collaboration between classmates and colleagues was encouraged by the instructor during all parts of the project's development. I, however, **did not collaborate with any other person** at any stage of this work, nor did I provide significant help to any classmate, barring answering some general questions about restrictions that were already established in the instruction document during unrelated group dynamics in synchronic class sessions. 
I would, however, like to take this section as an opportunity to acknowledge and thank Dr. Ordóñez for her continuous disposition in clearing up doubts concerning this and other class assignments, her consideration in regards to adjusting many development restrictions and deadlines, and her encouragement of each student's creative impulses, which became a big source of motivation during the elaboration of this project. I would also like to thank all of the classmates who took time out of their undoubtedly busy routines to make fundamental and interesting questions and suggestions in both the synchronized class context and through the Piazza forum of this course, especially the anonymous poster who suggested changing the preexisting GUI button labels to more apt alternatives.

# Deadline disclaimer

The core of this project was uploaded to this repository on Monday, March 17th, 2021, and the final edit to the main files was done on the night of Tuesday, March 18th, 2021. Both dates are one and two days after the final due date of this project, which was last Sunday, March 15th, 2021. I did have most of the project files on a functional state by the end of the established deadline, but decided against adding them to the repository it as I found that the implementation of the game procedure in the `Proj2.java` file required more work. I wanted to notify this to be as upfront as possible with the instructor, which, as has been said before, has been continously considerate with regards to project deadlines during this course.

# References 

In the development of this project, the following references were used:

* Instructions and explanations provided by the course's professor, instructor and author of the preexisting Java files, Dr. Patricia Ordóñez Franco. 
* *Design Pattern - Observer Pattern* (2021). Design Patterns Tutorial, Tutorial Points. <https://www.tutorialspoint.com/design_pattern/observer_pattern.htm>
* Oracle documentation about Java classes and methods, including:
    * LinkedList (2021). Java™ Platform, Standard Edition 7. <https://docs.oracle.com/javase/7/docs/api/overview-summary.html>
    * ArrayList (2021). Java™ Platform, Standard Edition 8. <https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html#ensureCapacity-int->
    * Generic Types (2021). The Java™ Tutorials. <https://docs.oracle.com/javase/tutorial/java/generics/types.html>
