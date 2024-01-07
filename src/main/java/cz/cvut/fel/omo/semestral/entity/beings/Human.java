package cz.cvut.fel.omo.semestral.entity.beings;


import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.actions.Action;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;
import cz.cvut.fel.omo.semestral.tick.Tickable;

import java.util.List;
import java.util.Queue;

/**
 * Represents a human being in the smart home simulation.
 * This class extends the {@link Being} class and adds human-specific functionalities.
 */
public class Human extends Being implements Tickable {

    /**
     * Constructs a new Human with the specified name, action plan, and initial room.
     *
     * @param name       The name of the human.
     * @param actionPlan The queue of actions that this human will perform.
     * @param room       The initial room where the human is located.
     */
    public Human(String name, Queue<Action> actionPlan, Room room) {
        super(actionPlan, name, room);
        // Initialize human-specific attributes
    }

    /**
     * Sends user input to a specified device system.
     * This method simulates the interaction of the human with a device in the smart home.
     *
     * @param deviceSystem The device system to receive the input.
     * @param inputType    The type of user input.
     * @param inputValue   The value of the input.
     */
    protected void sendUserInput(DeviceSystem deviceSystem, UserInputType inputType, Object inputValue) {
        deviceSystem.getUserInputSensor().detectInput(inputType, inputValue);
    }

    /**
     * Simulates the action of this human feeding another human.
     *
     * @param human The human being fed.
     */
    public void feed(Human human) {
        System.out.println(this.name + " feeds " + human.getName());
        human.eat();
    }

    /**
     * Simulates the action of this human feeding a pet.
     *
     * @param pet The pet being fed.
     */
    public void feed(Pet pet) {
        System.out.println(this.name + " feeds " + pet.getName());
        pet.eat();
    }

    /**
     * Turns on a specified device system.
     *
     * @param deviceSystem The device system to be turned on.
     */
    public void turnOnSystem(DeviceSystem deviceSystem) {
        System.out.println(this.name + " turns on system " + deviceSystem.getClass().getName() + " with appliance: " + deviceSystem.getAppliance().getSerialNumber());
        deviceSystem.turnOn();
    }

    /**
     * Turns off a specified device system.
     *
     * @param deviceSystem The device system to be turned off.
     */
    public void turnOffSystem(DeviceSystem deviceSystem) {
        System.out.println(this.name + " turns off system " + deviceSystem.getClass().getName() + " with appliance: " + deviceSystem.getAppliance().getSerialNumber());
        deviceSystem.turnOff();
    }

    @Override
    public void onTick() {
        performNextAction();
    }

    /**
     * Performs the next action in the action plan.
     * This method handles being-specific actions and delegates device interactions to the appropriate device systems.
     */
    @Override
    public void performNextAction() {
        if (!actionPlan.isEmpty()) {
            Action nextAction = actionPlan.poll();

            switch (nextAction.getType()) {
                // Handling Being-specific actions
                case B_CHANGEROOM:
                    if(nextAction.getValue() instanceof Room) {
                        goTo((Room) nextAction.getValue());
                    }
                    break;
                case B_SLEEP:
                    // Logic for sleeping
                    break;
                case B_EAT:
                    // Logic for eating
                    break;
                default:
                    DeviceSystem deviceSystem = findDeviceSystemInRoom(nextAction.getType());
                    if (deviceSystem != null){
                        sendUserInput(deviceSystem, nextAction.getType(), nextAction.getValue());
                    }
                    break;
            }

        }
    }

    /**
     * Finds a device system in the current room that can handle a given type of user input.
     *
     * @param inputType The type of user input for which to find the device system.
     * @return The device system that can handle the given input type, or null if none is found.
     */
    public DeviceSystem findDeviceSystemInRoom(UserInputType inputType) {
        List<DeviceSystem> deviceSystems = room.getDeviceSystems();
        for (DeviceSystem deviceSystem : deviceSystems) {
            if (deviceSystem.getAllowedUserInputTypes().contains(inputType)) {
                return deviceSystem;
            }
        }
        return null;
    }

}