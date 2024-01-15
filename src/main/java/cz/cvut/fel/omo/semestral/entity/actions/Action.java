package cz.cvut.fel.omo.semestral.entity.actions;


import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import lombok.Getter;

/**
 * Represents an action to be performed by a being in the smart home simulation.
 * This class encapsulates the type of action and any associated value necessary for executing the action.
 */
@Getter
public class Action {
    private final UserInputType type;
    private final Object value;

    /**
     * Constructs an Action with a specified type and value.
     *
     * @param type  The type of the action, defined in {@link UserInputType}.
     * @param value The value associated with the action. This could be a number, string, or any object
     *              relevant to the specific action.
     */
    public Action(UserInputType type, Object value) {
        this.type = type;
        this.value = value;
    }

}
