package cz.cvut.fel.omo.semestral.entity.devices.appliances;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Alarm extends Appliance {

    private boolean isArmed;

    public Alarm(UUID serialNumber) {
        super(serialNumber);
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

