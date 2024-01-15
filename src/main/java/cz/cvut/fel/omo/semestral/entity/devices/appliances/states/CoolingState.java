package cz.cvut.fel.omo.semestral.entity.devices.appliances.states;

import cz.cvut.fel.omo.semestral.entity.devices.appliances.HVAC;
import lombok.Getter;

/**
 * Represents the cooling state of an HVAC system.
 * In this state, the HVAC system is actively cooling the environment.
 */
@Getter
public class CoolingState implements HVACState {
    /** The power consumption of the HVAC system per tick */
    private final double powerConsumptionPerTick = 40;
    /** The wear of the HVAC system per tick */
    private final int wearPerTick = 10;
    /** The temperature change of the environment per tick */
    private final double tempChangePerTick = -0.5;

    /**
     * Maintains the current state when the HVAC is already in cooling mode.
     * If the system is already on and cooling, no further action is taken.
     *
     * @param heater The HVAC system that is in cooling mode.
     */
    @Override
    public void turnOn(HVAC heater) {
        System.out.println("HVAC is already ON and cooling.");
    }

    /**
     * Transitions the HVAC system from cooling mode to the off state.
     * This method changes the state of the HVAC system to OffState, indicating
     * that the cooling process is stopped.
     *
     * @param heater The HVAC system that needs to be turned off.
     */
    @Override
    public void turnOff(HVAC heater) {
        heater.setState(new OffState());
        System.out.println("HVAC is now OFF.");
    }

}

