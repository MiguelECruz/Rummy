package proj2;

import java.util.Collections;

public interface MeldInterface extends HandInterface {

    /**
     * Checks to see if all the cards of that suit have been
     *   added to the run (it has reached full capacity).
     * @return <code>true</code> if the run is full.
     */
    public boolean isFull();

}
