package cz.cvut.fel.omo.semestral.entity.devices.appliances;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.states.*;
import lombok.Getter;

import java.util.UUID;

@Getter
public class HVAC extends Appliance {

    private HVACState currentState;

    public HVAC(UUID serialNumber) {
        super(serialNumber);
        this.currentState = new OffState(); // Default state is off
    }

    public void setState(HVACState newState) {
        this.currentState = newState;
    }

    @Override
    public void executeCommand(DeviceCommand command) {
        switch (command) {
            case SWITCH_TO_HEATING:
                setState(new HeatingState());
                break;
            case SWITCH_TO_COOLING:
                setState(new CoolingState());
                break;
            case SWITCH_TO_VENTILATION:
                setState(new VentilationState());
                break;
            case TURN_OFF:
                setState(new OffState());
                break;
            default:
                System.out.println("Command not recognized for HVAC.");
                break;
        }
    }

    @Override
    public void turnOn() {
        currentState.turnOn(this);
    }

    @Override
    public void turnOff() {
        currentState.turnOff(this);
    }
}

