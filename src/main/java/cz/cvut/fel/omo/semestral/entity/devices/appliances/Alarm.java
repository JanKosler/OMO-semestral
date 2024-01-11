package cz.cvut.fel.omo.semestral.entity.devices.appliances;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import lombok.Getter;

import java.util.UUID;

/**
 * Represents an Alarm system in the smart home environment. This class models
 * the behavior of a security alarm, including the ability to be armed and disarmed.
 */
@Getter
public class Alarm extends Appliance {

    private boolean isArmed;
    private final double powerConsumptionPerTick_IDLE = 5;
    private final double powerConsumptionPerTick_ACTIVE = 15;

    public Alarm(UUID serialNumber) {
        super(serialNumber, 100);
        this.isArmed = false; // Alarm is disarmed by default
    }

    @Override
    public void executeCommand(DeviceCommand command) {
        switch (command) {
            case ARM_ALARM:
                armAlarm();
                break;
            case DISARM_ALARM:
                disarmAlarm();
                break;
            default:
                System.out.println("Command not recognized for Alarm.");
                break;
        }
    }

    @Override
    public void onTick() {
        DeviceState currentState = this.getState();
        if(currentState != DeviceState.OFF && currentState!= DeviceState.MALFUNCTION) {
            performNextAction();
            if (currentState == DeviceState.IDLE || currentState == DeviceState.ON) {
                updatePowerConsumption(powerConsumptionPerTick_IDLE);
                updateWear(1);
            } else if ( currentState == DeviceState.ACTIVE) {
                updatePowerConsumption(powerConsumptionPerTick_ACTIVE);
                updateWear(5);
            }
            checkIfBroken();
        }
    }

    private void armAlarm() {
        if (!isArmed) {
            this.isArmed = true;
            this.setState(DeviceState.ACTIVE);
        }
    }

    private void disarmAlarm() {
        if (isArmed) {
            this.isArmed = false;
            this.setState(DeviceState.IDLE);
        }
    }
}

