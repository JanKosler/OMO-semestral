package cz.cvut.fel.omo.semestral.entity.devices.appliances.states;

import cz.cvut.fel.omo.semestral.entity.devices.appliances.HVAC;

public interface HVACState {
    void turnOn(HVAC heater);
    void turnOff(HVAC heater);

}

