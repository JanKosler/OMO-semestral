package cz.cvut.fel.omo.semestral.entity.devices.appliances.states;

import cz.cvut.fel.omo.semestral.entity.devices.appliances.HVAC;

public class HeatingState implements HVACState {
    @Override
    public void turnOn(HVAC hvac) {
        System.out.println("HVAC is already ON and heating.");
    }

    @Override
    public void turnOff(HVAC hvac) {
        hvac.setState(new OffState());
    }

}
