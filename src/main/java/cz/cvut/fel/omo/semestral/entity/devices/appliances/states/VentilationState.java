package cz.cvut.fel.omo.semestral.entity.devices.appliances.states;

import cz.cvut.fel.omo.semestral.entity.devices.appliances.HVAC;

/**
 * Represents the ventilation state of an HVAC system.
 * In this state, the HVAC system is actively ventilating the environment.
 */
public class VentilationState implements HVACState {

    /**
     * Maintains the current state when the HVAC is already in ventilation mode.
     * If the system is already on and ventilating, no further action is taken.
     *
     * @param heater The HVAC system that is in ventilation mode.
     */
    @Override
    public void turnOn(HVAC heater) {
        System.out.println("HVAC is already ON and ventilating.");
    }

    /**
     * Transitions the HVAC system from ventilation mode to the off state.
     * This method changes the state of the HVAC system to OffState, indicating
     * that the ventilation process is stopped.
     *
     * @param heater The HVAC system that needs to be turned off.
     */
    @Override
    public void turnOff(HVAC heater) {
        heater.setState(new OffState());
        System.out.println("HVAC is now OFF.");
    }

}

