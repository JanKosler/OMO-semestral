package cz.cvut.fel.omo.semestral.entity.devices.appliances.states;

import cz.cvut.fel.omo.semestral.entity.devices.appliances.HVAC;

/**
 * Represents the off state of an HVAC system.
 * In this state, the HVAC system is not actively heating, cooling, or ventilating.
 */
public class OffState implements HVACState {

    /**
     * Transitions the HVAC system from the off state to a default operating state.
     * This implementation sets the HVAC system to the HeatingState when turned on.
     *
     * @param hvac The HVAC system that is being turned on.
     */
    @Override
    public void turnOn(HVAC hvac) {
        // Optionally set a default state when turned on
        hvac.setState(new HeatingState());
    }

    /**
     * Maintains the off state when an attempt to turn off the HVAC is made.
     * If the system is already off, no action is taken.
     *
     * @param hvac The HVAC system that is already off.
     */
    @Override
    public void turnOff(HVAC hvac) {
        System.out.println("HVAC is already OFF.");
    }

}
