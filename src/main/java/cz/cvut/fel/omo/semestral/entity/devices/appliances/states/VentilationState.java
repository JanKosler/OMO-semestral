package cz.cvut.fel.omo.semestral.entity.devices.appliances.states;

import cz.cvut.fel.omo.semestral.entity.devices.appliances.HVAC;

public class VentilationState implements HVACState {
    @Override
    public void turnOn(HVAC heater) {
        System.out.println("HVAC is already ON and ventilating.");
    }

    @Override
    public void turnOff(HVAC heater) {
        heater.setState(new OffState());
        System.out.println("HVAC is now OFF.");
    }

}

