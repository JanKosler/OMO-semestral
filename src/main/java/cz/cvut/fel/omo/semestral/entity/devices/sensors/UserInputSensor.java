package cz.cvut.fel.omo.semestral.entity.devices.sensors;

import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.actions.Action;
import lombok.Getter;

import java.util.Queue;

/**
 * A sensor designed to capture and process various types of user inputs within the smart home system.
 * This sensor is responsible for detecting input actions like turning on a light or adjusting temperature, and communicating these actions to the system.
 */
@Getter
public class UserInputSensor extends Sensor {

    private UserInputType inputType;
    private Object inputValue; // Could be boolean, integer, double, etc., based on input type
    protected Queue<Action> actionPlan;
    private final double powerConsumptionPerTick = 0.35; //Consumption in mWh every 10 mins.

    /**
     * Constructs a UserInputSensor with default settings.
     */
    public UserInputSensor() {

        super( 100);
        this.actionPlan = new java.util.LinkedList<>();
    }

    @Override
    public void onTick() {
        if (this.getState() == DeviceState.ON) {
            performNextAction();
            updateWear(1);
            updatePowerConsumption(powerConsumptionPerTick);
            checkIfBroken();
        }
    }

    /**
     * Detects and records a user input, then notifies observers about the change.
     *
     * @param inputType The type of input detected, as defined in {@link UserInputType}.
     * @param inputValue The value associated with the input, which can vary based on the input type.
     */
    public void detectInput(UserInputType inputType, Object inputValue) {
        this.inputType = inputType;
        this.inputValue = inputValue;
        // Notifying observers about the input change
        notifyObservers();
    }

    public void addtoActionPlan(Action action) {
        actionPlan.add(action);
    }

    public void performNextAction() {
        if (!actionPlan.isEmpty()) {
            Action nextAction = actionPlan.poll();
            detectInput(nextAction.getType(), nextAction.getValue());
        }
    }
}

