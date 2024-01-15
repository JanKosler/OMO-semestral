package cz.cvut.fel.omo.semestral.entity.actions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
/**
 * Represents a record of an action performed at a specific time tick.
 * This class encapsulates the details of an action and the tick (time point)
 * at which the action occurred.
 */
public class ActionRecord {

    /**
     * The action performed.
     * This field holds an instance of the Action class, representing
     * the specific action that was carried out.
     */
    private final Action action;

    /**
     * The tick at which the action was performed.
     * This field holds the time point at which the action was performed.
     */
    private final int tick;

}
