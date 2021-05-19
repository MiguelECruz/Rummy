/*
Project Rummy card game: Class MyStack
- Student/programmer:   Miguel E. Cruz Molina
- Student number:       801-16-1956
- Course and section:   CCOM 4029-001
- Instructor:           Dr. Patricia Ordóñez
- Date of last edit:    5/18/2021

Summary: This class specifically builds a tuple of
  two playing cards, using the MyTuple and Card classes.
  It only counts with constructors, and every other
  action is managed by the MyTuple class.
*/

package proj2;

public class CardTuple extends MyTuple <Card, Card> {

    /**
     * Creates a tuple with the two given cards as its elements.
     * @param card1 first card in the tuple.
     * @param card2 second card in the tuple
     */
    public CardTuple(Card card1, Card card2) {
        super(card1, card2);
    }

    /**
     * Creates an empty tuple for two Card objects.
     */
    public CardTuple() {
        super();
    }

}
