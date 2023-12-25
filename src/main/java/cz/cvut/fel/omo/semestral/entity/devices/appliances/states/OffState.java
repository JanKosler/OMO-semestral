package cz.cvut.fel.omo.semestral.entity.devices.appliances.states;

import cz.cvut.fel.omo.semestral.entity.devices.appliances.HVAC;

public class OffState implements HVACState {
    @Override
    public void turnOn(HVAC hvac) {
        // Optionally set a default state when turned on
        hvac.setState(new HeatingState());
    }

    @Override
    public void turnOff(HVAC hvac) {
        System.out.println("HVAC is already OFF.");
    }

}
