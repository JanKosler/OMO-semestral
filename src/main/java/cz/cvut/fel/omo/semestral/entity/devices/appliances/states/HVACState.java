package cz.cvut.fel.omo.semestral.entity.devices.appliances.states;

import cz.cvut.fel.omo.semestral.entity.devices.appliances.HVAC;

/**
 * Interface representing the state of an HVAC (Heating, Ventilation, and Air Conditioning) system.
 * This interface defines methods for turning the HVAC system on and off, with each concrete state
 * implementing these methods to provide state-specific behavior. States might include heating, cooling,
 * ventilation, or being turned off. The state pattern allows the HVAC system to change its behavior dynamically
 * based on its current state.
 */
public interface HVACState {

    /**
     * Turns the HVAC system on, initiating the specific behavior associated with the current state.
     *
     * @param heater The HVAC system whose state is being activated.
     */
    void turnOn(HVAC heater);

    /**
     * Turns the HVAC system off, stopping any active processes associated with the current state.
     *
     * @param heater The HVAC system whose state is being deactivated.
     */
    void turnOff(HVAC heater);

    /**
     * Returns the power consumption of the HVAC system per tick.
     *
     * @return The power consumption of the HVAC system per tick.
     */
    double getPowerConsumptionPerTick();

    /**
     * Returns the wear of the HVAC system per tick.
     *
     * @return The wear of the HVAC system per tick.
     */
    int getWearPerTick();

    /**
     * Returns the temperature change of the environment per tick.
     *
     * @return The temperature change of the environment per tick.
     */
    double getTempChangePerTick();

}

