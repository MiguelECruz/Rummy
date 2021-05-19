/*
Project Rummy card game: Class MyTuple
- Student/programmer:   Miguel E. Cruz Molina
- Student number:       801-16-1956
- Course and section:   CCOM 4029-001
- Instructor:           Dr. Patricia Ordóñez
- Date of last edit:    5/18/2021

Summary: This Generic class implements a simple version of
  a tuple or ordered pair data structure, with the option of
  building a tuple with objects of two different classes.
  The tuple itself is implemented with two objects of the
  specified classes, and the class counts with 5 methods:
  2 constructors and the classics "isEmpty()", "clear()"
  and "toString()".
*/

package proj2;

public class MyTuple <O1, O2> {

    private O1 element1;
    private O2 element2;

    /**
     * Creates a new tuple with two elements of potentially different classes.
     */
    public MyTuple(O1 e1, O2 e2) {
        element1 = e1;
        element2 = e2;
    }

    /**
     * Creates an empty tuple.
     */
    public MyTuple() {
        this(null, null);
    }

    /**
     * Returns the value of the first element in the tuple.
     * @return the first element.
     */
    public O1 getFirstElement() {
        return element1;
    }

    /**
     * Returns the value of the second element in the tuple.
     * @return the first element.
     */
    public O2 getSecondElement() {
        return element2;
    }

    /**
     * Returns the value of the first element in the tuple.
     * @return the first element.
     */
    public void setFirstElement(O1 element) {
        element1 = element;
    }

    /**
     * Allows the programmer to change the second element of the tuple.
     */
    public void setSecondElement(O2 element) {
        element2 = element;
    }

    /**
     * Checks whether or not the tuple is empty.
     * @return <code>true</code> if there are no items left in the stack.
     */
    public boolean isEmpty() {
        return element1 == null && element2 == null;
    }

    /**
     * Clears out the stack, removing it to an empty state.
     */
    public void clear() {
        element1 = null;
        element2 = null;
    }

    /**
     * Returns a description of the stack.
     * @return a list of all the cards in the stack (bottom card first, top card last).
     */
    public String toString() {
        return "(" + element1.toString() + ", " + element2.toString() + ")";
    }
}
