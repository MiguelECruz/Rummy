package proj2;

import java.util.Collections;

public interface SetInterface extends MeldInterface {

    /**
     * Returns rank of the set.
     * @return char representing the set's rank as defined in Card class.
     */
    public char getRank();

    /**
     * Returns the index of the rank of the set.
     @return int corresponding to index of rank as defined in Card class.
     */
    public int getRankIndex();

}
