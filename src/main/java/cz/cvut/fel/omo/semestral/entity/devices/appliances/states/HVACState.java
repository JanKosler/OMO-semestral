package cz.cvut.fel.omo.semestral.entity.devices.appliances.states;

import cz.cvut.fel.omo.semestral.entity.devices.appliances.HVAC;
import lombok.Getter;


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

    double getPowerConsumptionPerTick();
    int getWearPerTick();

    double getTempChangePerTick();

}

