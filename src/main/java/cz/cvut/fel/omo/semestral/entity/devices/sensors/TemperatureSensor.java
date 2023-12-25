package cz.cvut.fel.omo.semestral.entity.devices.sensors;

import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import lombok.Getter;

@Getter
public class TemperatureSensor extends Sensor {
    private double currentTemperature;

    public TemperatureSensor() {
        super(DeviceState.OFF, 0, 0.0);
    }

    public void readTemperature(double temperature) {
        // This method simulates reading the temperature.
        if (this.currentTemperature != temperature) {
            this.currentTemperature = temperature;
            notifyObservers();
        }
    }
}

