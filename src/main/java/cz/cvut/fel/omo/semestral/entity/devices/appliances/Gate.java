package cz.cvut.fel.omo.semestral.entity.devices.appliances;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import lombok.Getter;

import java.util.UUID;

/**
 * Represents a Gate within the smart home system. This class provides
 * functionalities to open, close, and toggle the state of the gate. The
 * gate can be controlled through specific commands, and its state is
 * reflected by whether it is open (active state) or closed (idle state).
 */
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
            case TOGGLE_GATE:
                toggleGate();
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

