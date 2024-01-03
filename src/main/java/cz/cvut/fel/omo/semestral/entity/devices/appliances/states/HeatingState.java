package cz.cvut.fel.omo.semestral.entity.devices.appliances.states;

import cz.cvut.fel.omo.semestral.entity.devices.appliances.HVAC;

/**
 * Represents the heating state of an HVAC system.
 * In this state, the HVAC system is actively heating the environment.
 */
public class HeatingState implements HVACState {
    /**
     * Maintains the current state when the HVAC is already in heating mode.
     * If the system is already on and heating, no action is taken.
     *
     * @param hvac The HVAC system that is in heating mode.
     */
    @Override
    public void turnOn(HVAC hvac) {
        System.out.println("HVAC is already ON and heating.");
    }

    /**
     * Transitions the HVAC system from heating mode to the off state.
     * This method changes the state of the HVAC system to OffState, indicating
     * that the heating process is stopped.
     *
     * @param hvac The HVAC system that needs to be turned off.
     */
    @Override
    public void turnOff(HVAC hvac) {
        hvac.setState(new OffState());
    }

}
