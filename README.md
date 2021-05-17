# Project #2: Rummy card game and GUI 

Student: Miguel E. Cruz Molina (801-16-1956)

Course: CCOM 4029-001 

Instructor: Dr. Patricia Ordóñez Franco

Date: 5-16-2020

Version of Java used: Java SE Development Kit 15.0.2

## Introductory description
This project consists of a series of Java programs that aim to function collectively as a simple implementation of a version of the Rummy card game between two or more players. Said implementation is accompanied by a Graphical User Interface (GUI), which allows the user to play a Rummy round against an automated player. The project is more broadly (i.e., from an academic perspective and as an intellectual pursuit) intended to illustrate a diverse array of concepts, mechanisms and linguistic and programming conventions from the Object-Oriented Analysis and Design, exemplified here by Java, itself a high-level O-O language. More concretely, my work for this assignment, and the evidence thereof, can be divided into three relatively distinct parts: the design document for this project, submitted to the instructor in PDF form at the end of April, 2021; the 18 different Java files containing all 11 classes and 7 interfaces used in the development of the game; and an additional text file (`p2-output.txt`) submitted as evidence of those files' functionality, illustrating the output generated during the game by two automatized players. Without further ado, the following sections of this document delve into the structure and functionality of the second of these parts, which comprises the core of this project and repository. 

## The game's rules
With the intent of eliminating redundancy and providing useful and unambiguous context for subsequent sections, I've taken the liberty of quoting the Dr. Ordóñez description of the game's rules and mechanics in their entirety:

> These rules are a slight adaptation of standard Rummy rules. The objective of this game is to make melds (sets and runs) of similar cards and lay them on the table until all cards in your hand have been disposed of. A set is three or more cards of the same rank, such as three aces or four sevens. A run is three or more sequential cards of the same suit, such as the three-four-five of clubs or the jack-queen-king of diamonds. The ace is always low.
> Each player is dealt nine cards from the stock pile (Deck). The next card is turned face-up to initiate the discard pile (Stack). The deck is placed face-down on the table. In each turn, a player draws one card from either the Deck or Stack. They may optionally lay sets, runs on the table or place the card on Stack. They may also optionally lay cards that fit in sets or runs already on the table. The turn ends with a discard. If a player is able to lay all remaining cards on the table at the end of a turn, the discard is optional.
> The game is over when one player is out of cards or the stock is exhausted. At this point, players count the points remaining in their hands. Aces are worth one, face cards are worth ten, and all other cards are worth their face value. Lowest value hand wins. Ties are possible.

The following section discusses the contents of each java file at length, and how they relate to these rules and to the object constraints specified by the professor in the rest of the instructions document.

## Project files
As mentioned above, the coding aspect of the project is comprised of 18 Java files, 11 of which are classes, with the remaining 7 being interface files. The initial template deviced for some of these files (`Card.java`, `CardInterface.java`, `Deck.java`, `DeckInterface.java`, `Hand.java`, `HandInterface.java`, `Set.java`, `SetInterface.java`, and early versions of classes `Table`, `SetPanel` and `HandPanel`, all contained in a single java file) was designed and written by Dr. Oróñez and Dr. John K. Estell. The other 10 were primarily designed by me, although following the standards set by the project authors' in regards to general structure and careful documentation. The contents of the 18 files are thus:

* **`Card.java` and `CardInterface.java`**: This class and corresponding interface describe and implement the cards in the game. Each card has a suit value (`C` for clubs, `D` for diamongs, `H` for hearts, `S` for spades) and a rank value (`A` for ace, `T`, `J`, `Q` and `K` for ten, jack, queen and king respectively, and each digit from 2 to 9 for itself). It also counts with an image (in the form of an `ImageIcon` object), pre-assigned by the instructors, and with methods to get and set the card's fields, as well as the index of the suits and ranks included in a predefined array, and to express the card as a `String` and compare it to other cards. Special attention was put into excluding any static fields and methods from the interface, to allow it to be potentially used for cards with different ranks and suits to the French version.

* **`Deck.java` and `DeckInterface.java`**: This class and interface represent the deck in the game, from which all players are dealt their cards throughout the game. The deck structure itself is implemented through a `LinkedList` object. The deck class contains methods for: filling it with all possible combinations of two arrays of ranks and suit; shuffling; adding a Card (to the bottom or to a specific index) to it; picking a card from it (dealt from the top, removed from the button or from a specific index); peeking the card at its top; emptying it out, and then filling it again; expressing it as a `String`; and checking whether or not it's empty, and how many cards it has. It's important to note that the deck eminently works as a queue, and that some of these methods were added for uniformity and consistency moreso than for practical in-game reasons.

* **`Pile.java` and `PileInterface.java`**: The `Pile` class, as its name suggests, implements the discard pile in the game, to which all players discard a card per turn and from which they may add one card to their hand instead of being dealt one from the deck. The main difference between the `Deck` and the `Pile` classes, which share most if not all of their methods, is that, whereas the former is implemented as a queue, the latter is basically a stack, as cards can be addedor dealt from it only from its top. This is especially important because, as the project's object requirements instruct, this class not only implements `PileInterface`, but it also extends a Generic class, the one in **`MyStack.java`**, which is wholly original and includes methods for pushing and popping (and peeking, in the case of the one on the top) at elements, and printing, clearing and verifying the emptyness of a `LinkedList` with the stack's contents.

* **`Hand.java`** and **`HandInterface.java`**: Perhaps the most important class in the game, the most complex and the most heavily modified, each object of `Hand` simulates and implements the individual functionality of a player's hand and the player themself. The hand of cards itself is implemented with an `ArrayList` object, and includes many overridden methods for adding, finding, getting the index of, checking if they're included, removing and replacing cards, as well as sets and runs. It also comes equipped with a sorting method, a method for evaluating the amount of points accumulated by the hand's propietor, and other unique methods for getting and setting its capacity and truncating it, to name a few.

* **`Set.java`**, **`SetInterface.java`**, **`Run.java`** and **`RunInterface.java`**: 










