package proj2;

public class MyTuple <O1, O2> {

    protected O1 element1 = null;
    protected O2 element2 = null;

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

//    /**
//     * Adds a new item of class O to the top of the stack.
//     * @param item the item being added.
//     */
//    public void push(O item) { stack.addFirst(item); }
//
//    /**
//     * Removes and returns the item at the top of the stack.
//     * @return the top item in the stack.
//     */
//    public O pop() { return stack.removeFirst(); }
//
//    /**
//     * Returns the value of the item at the top of the stack,
//     *   but doesn't remove it.
//     * @return the value of the top item in the stack.
//     */
//    public O top() { return stack.peekFirst(); }

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
