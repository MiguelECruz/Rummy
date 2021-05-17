package proj2;

import java.util.Collections;

public interface RunInterface extends MeldInterface {

    /**
     * Returns suit of the run.
     * @return char representing the run's suit as defined in Card class.
     */
    public char getSuit();

    /**
     * Returns the index of the suit of the run.
     @return int corresponding to index of suit as defined in Card class.
     */
    public int getSuitIndex();

}
