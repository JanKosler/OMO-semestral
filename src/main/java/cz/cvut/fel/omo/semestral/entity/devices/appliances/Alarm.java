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
    /** Indicates whether the alarm is armed or not */
    private boolean isArmed;
    /** The power consumption of the alarm system per tick when idle */
    private final double powerConsumptionPerTick_IDLE = (double) 5 / 600;
    /** The power consumption of the alarm system per tick when active */
    private final double powerConsumptionPerTick_ACTIVE = (double) 15 / 600;

    /**
     * Constructs a new Alarm with the specified serial number.
     * The alarm is initialized with a default power level of 100 and is disarmed by default.
     *
     * @param serialNumber The unique identifier for this alarm device.
     */
    public Alarm(UUID serialNumber) {
        super(serialNumber, 100);
        this.isArmed = false; // Alarm is disarmed by default
    }


    /**
     * Executes a specific command on the alarm device.
     * This method allows the alarm to be armed or disarmed based on the provided command.
     *
     * @param command The device command to be executed, either ARM_ALARM or DISARM_ALARM.
     */
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

    /**
     * Performs actions on each tick based on the current state of the alarm.
     * This method updates the power consumption and wear of the alarm, and checks if it's broken.
     */
    @Override
    public void onTick() {
        DeviceState currentState = this.getState();
        if(currentState != DeviceState.OFF && currentState!= DeviceState.MALFUNCTION) {
            performAllActions();
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

    /**
     * Arms the alarm system, setting its state to ACTIVE.
     * This method changes the state of the alarm to active if it is not already armed.
     */
    private void armAlarm() {
        if (!isArmed) {
            this.isArmed = true;
            this.setState(DeviceState.ACTIVE);
        }
    }

    /**
     * Disarms the alarm system, setting its state to IDLE.
     * This method changes the state of the alarm to idle if it is currently armed.
     */
    private void disarmAlarm() {
        if (isArmed) {
            this.isArmed = false;
            this.setState(DeviceState.IDLE);
        }
    }
}

