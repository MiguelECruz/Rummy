/*
Project Rummy card game: Interface MeldInterface
- Student/programmer:   Miguel E. Cruz Molina
- Student number:       801-16-1956
- Course and section:   CCOM 4029-001
- Instructor:           Dr. Patricia Ordóñez
- Date of last edit:    5/18/2021

Summary: This interface includes headers of the instance methods
  which are uniquely necessary for implementing both the Set
  and Run classes, which implement sets and runs, respectively,
  the two kinds of melds in a card game. Additional methods
  necessary for a meld's implementation are inherited from
  HandInterface, as they're also shared by the Hand class.
*/

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
