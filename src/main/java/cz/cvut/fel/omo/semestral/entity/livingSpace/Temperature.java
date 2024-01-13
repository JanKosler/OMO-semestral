package cz.cvut.fel.omo.semestral.entity.livingSpace;

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
