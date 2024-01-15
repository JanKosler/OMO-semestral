package cz.cvut.fel.omo.semestral.entity.devices.appliances;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * Represents a Gate within the smart home system. This class provides
 * functionalities to open, close, and toggle the state of the gate. The
 * gate can be controlled through specific commands, and its state is
 * reflected by whether it is open (active state) or closed (idle state).
 */
@Getter
@Slf4j
public class Gate extends Appliance {
    /** Indicates whether the gate is open or closed */
    public boolean isOpen;
    /** The maximum wear capacity of the gate */
    private final int wearCapacity = 100;
    /** The power consumption of the gate per tick when idle */
    private final double powerConsumptionPerTick_IDLE = (double) 5 / 600;
    /** The power consumption of the gate per tick when active */
    private final double powerConsumptionPerTick_ACTIVE = (double) 15 / 600;

    public Gate(UUID serialNumber) {
        super(serialNumber, 100);
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

    @Override
    public void onTick() {
        DeviceState currentState = this.getState();
        if (currentState != DeviceState.OFF && currentState != DeviceState.MALFUNCTION) {
            performAllActions();
            updateWear(1);
            updatePowerConsumption(powerConsumptionPerTick_IDLE);
            checkIfBroken();
        }
    }

    private void openGate() {
        if (!isOpen) {
            this.isOpen = true;
            this.setState(DeviceState.ACTIVE);
            updateWear(10);
            updatePowerConsumption(powerConsumptionPerTick_ACTIVE);
            log.info("Gate: Opened.");
        }
    }

    private void closeGate() {
        if (isOpen) {
            this.isOpen = false;
            this.setState(DeviceState.IDLE);
            updatePowerConsumption(powerConsumptionPerTick_ACTIVE);
            updateWear(10);
            log.info("Gate: Closed.");
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

