/*
Project Rummy card game: Interface SetInterface
- Student/programmer:   Miguel E. Cruz Molina
- Student number:       801-16-1956
- Course and section:   CCOM 4029-001
- Instructor:           Dr. Patricia Ordóñez
- Date of last edit:    5/18/2021

Summary: This interface includes headers of the instance methods
  which are uniquely necessary for implementing a working Set
  class (which represents a set of cards in a card game).
  Other methods necessary for the set's implementation are
  inherited from MeldInterface, if they're uniquely shared
  by the Set and Run classes, or from HandInterface, if they're
  also shared by the Hand class.
*/

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
