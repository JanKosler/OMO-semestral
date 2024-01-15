package cz.cvut.fel.omo.semestral.entity.livingSpace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a temperature.
 */
@Getter
@Setter
@AllArgsConstructor
public class Temperature {
    /** Temperature in the room */
    private double temperature;
    /**
     * Adjusts the temperature in the room.
     * @param adjustment Temperature adjustment. Positive value increases the temperature, negative decreases it.
     */
    public void adjustTemperature(double adjustment) {
        temperature += adjustment;
    }
}
