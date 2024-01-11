package cz.cvut.fel.omo.semestral.entity.devices.appliances;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import lombok.Getter;

import java.util.UUID;

/**
 * Represents a Light in the smart home system. This class provides
 * basic functionalities to turn on and off the light. The state of the light
 * (on or off) is managed through specific commands. The light starts in an
 * 'off' state and can be toggled to 'on' or vice versa, reflecting its
 * operational status in the smart home environment.
 */
@Getter
public class Light extends Appliance {

    private final double powerConsumptionPerTick_IDLE = 0.5;
    private final double powerConsumptionPerTick_ON = 10;

    public Light(UUID serialNumber) {
        super(serialNumber, 100);
    }

    @Override
    public void executeCommand(DeviceCommand command) {
        switch (command) {
            case TURN_ON:
                turnOn();
                break;
            case TURN_OFF:
                setIdle();
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
            performNextAction();
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

