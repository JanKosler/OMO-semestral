package cz.cvut.fel.omo.semestral.common.enums;

/**
 * Enumerates the types of user inputs or actions that can occur in the smart home simulation.
 * This includes both direct interactions with devices and general actions performed by beings.
 */
public enum UserInputType {
    /** Represents a command to adjust the HVAC system's temperature. */
    HVAC_TEMPERATURE,

    /** Represents a command to switch lights on or off. */
    LIGHT_SWITCH,

    /** Represents a command to control the gate. */
    GATE_CONTROL,

    /** Represents a command to arm or disarm the alarm system. */
    ALARM_DISARM,

    /** Represents a command to change the TV channel. */
    TV_CHANNEL,

    /** Represents a command to adjust the TV volume. */
    TV_VOLUME,

    /** Represents a command to turn the TV on or off. */
    TV_POWER,

    /** Represents a command to adjust the refrigerator's temperature. */
    FRIDGE_TEMPERATURE,

    // Being-specific actions
    /** Represents an action for a being to change rooms. */
    B_CHANGEROOM,

    B_REPAIR,

    B_STARTSPORT,
    B_STOPSPORT

}
