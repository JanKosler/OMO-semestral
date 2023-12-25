package cz.cvut.fel.omo.semestral.entity.devices.appliances;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Gate extends Appliance {

    private boolean isOpen;

    public Gate(UUID serialNumber) {
        super(serialNumber);
        this.isOpen = false; // Gates are closed by default
    }

    @Override
    public void executeCommand(DeviceCommand command) {
        switch (command) {
            case OPEN_GATE:
                openGate();
                break;
            case CLOSE_GATE:
                closeGate();
                break;
            default:
                System.out.println("Command not recognized for Gate.");
                break;
        }
    }

    private void openGate() {
        if (!isOpen) {
            this.isOpen = true;
            this.setState(DeviceState.ACTIVE);
            System.out.println("Opening gate...");
        }
    }

    private void closeGate() {
        if (isOpen) {
            this.isOpen = false;
            this.setState(DeviceState.IDLE);
            System.out.println("Closing gate...");
        }
    }

    public void toggleGate() {
        if (isOpen) {
            closeGate();
        } else {
            openGate();
        }
    }
}

