package cz.cvut.fel.omo.semestral.entity.devices.appliances;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * Represents a Light in the smart home system. This class provides
 * basic functionalities to turn on and off the light. The state of the light
 * (on or off) is managed through specific commands. The light starts in an
 * 'off' state and can be toggled to 'on' or vice versa, reflecting its
 * operational status in the smart home environment.
 */
@Getter
@Slf4j
public class Light extends Appliance {

    private final double powerConsumptionPerTick_IDLE = 0.5;
    private final double powerConsumptionPerTick_ON = 10;

    public Light(UUID serialNumber) {
        super(serialNumber, 500);
    }

    @Override
    public void executeCommand(DeviceCommand command) {
        switch (command) {
            case TURN_ON:
                turnOn();
                log.info("Light: Turned on.");
                break;
            case TURN_OFF:
                setIdle();
                log.info("Light: Turned off.");
                break;
            default:
                System.out.println("Command not recognized for Light.");
                break;
        }
    }

    @Override
    public void onTick() {
        DeviceState currentState = this.getState();
        if(currentState != DeviceState.OFF && currentState!= DeviceState.MALFUNCTION) {
            performAllActions();
            if (currentState == DeviceState.IDLE) {
                updatePowerConsumption(powerConsumptionPerTick_IDLE);
                updateWear(1);
            } else if (currentState == DeviceState.ON) {
                updatePowerConsumption(powerConsumptionPerTick_ON);
                updateWear(5);
            }
            checkIfBroken();
        }
    }

}

