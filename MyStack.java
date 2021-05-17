package proj2;

import java.util.LinkedList;

public class MyStack <O> {

    protected java.util.LinkedList <O> stack;

    /**
     * Creates a new stack with a LinkedList object initialized as empty.
     */
    public MyStack() { stack = new LinkedList(); }

    /**
     * Adds a new item of class O to the top of the stack.
     * @param item the item being added.
     */
    public void push(O item) { stack.addFirst(item); }

    /**
     * Removes and returns the item at the top of the stack.
     * @return the top item in the stack.
     */
    public O pop() { return stack.removeFirst(); }

    /**
     * Returns the value of the item at the top of the stack,
     *   but doesn't remove it.
     * @return the value of the top item in the stack.
     */
    public O top() { return stack.peekFirst(); }

    /**
     * Checks whether or not the stack is empty.
     * @return <code>true</code> if there are no items left in the stack.
     */
    public boolean isEmpty() { return stack.isEmpty(); }

    /**
     * Clears out the stack, removing it to an empty state.
     */
    public void clear() { stack.clear(); }

    /**
     * Returns a description of the stack.
     * @return a list of all the cards in the stack (bottom card first, top card last).
     */
    public String toString() {
        return stack.toString();
    }
}
