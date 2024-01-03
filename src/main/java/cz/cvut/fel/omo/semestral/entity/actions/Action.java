package cz.cvut.fel.omo.semestral.entity.actions;


import cz.cvut.fel.omo.semestral.common.enums.UserInputType;

/**
 * Represents an action to be performed by a being in the smart home simulation.
 * This class encapsulates the type of action and any associated value necessary for executing the action.
 */
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

    /**
     * Retrieves the type of this action.
     *
     * @return The type of the action.
     */
    public UserInputType getType() {
        return type;
    }

    /**
     * Retrieves the value associated with this action.
     *
     * @return The value of the action, which could be of any object type.
     */
    public Object getValue() {
        return value;
    }

}
