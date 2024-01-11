package cz.cvut.fel.omo.semestral.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Temperature {
    private double temperature;

    public void adjustTemperature(double adjustment) {
        temperature += adjustment;
    }
}
