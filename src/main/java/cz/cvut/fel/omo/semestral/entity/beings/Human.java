package cz.cvut.fel.omo.semestral.entity.beings;


import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.actions.Action;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Garage;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
import cz.cvut.fel.omo.semestral.entity.livingSpace.SportEquipment;
import cz.cvut.fel.omo.semestral.entity.livingSpace.SportEquipmentType;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;
import cz.cvut.fel.omo.semestral.manual.Manual;
import cz.cvut.fel.omo.semestral.manual.ManualRepo;
import cz.cvut.fel.omo.semestral.reporting.Report;
import cz.cvut.fel.omo.semestral.reporting.ReportVisitor;
import cz.cvut.fel.omo.semestral.tick.Tickable;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Queue;

/**
 * Represents a human being in the smart home simulation.
 * This class extends the {@link Being} class and adds human-specific functionalities.
 */
@Slf4j
public class Human extends Being implements Tickable {

    SportEquipment currentSportEquipment = null;
    @Setter
    ManualRepo manualRepo;

    /**
     * Constructs a new Human with the specified name, action plan, and initial room.
     *
     * @param beingID    The ID of the human.
     * @param name       The name of the human.
     * @param room       The initial room where the human is located.
     * @param actionPlan The queue of actions that this human will perform.
     */
    public Human(int beingID, String name, Room room, ManualRepo manualRepo, Queue<Action> actionPlan) {
        super(beingID, name, room, actionPlan);
        this.manualRepo = manualRepo;
    }
    /**
     * Constructs a new Human with the specified name and initial room.
     *
     * @param beingID    The ID of the human.
     * @param name       The name of the human.
     * @param room       The initial room where the human is located.
     */
    public Human(int beingID, String name, Room room, ManualRepo manualRepo) {
        super(beingID, name, room);
        this.manualRepo = manualRepo;
    }

    /**
     * Sends user input to a specified device system.
     * This method simulates the interaction of the human with a device in the smart home.
     *
     * @param deviceSystem The device system to receive the input.
     * @param action  The type of user action.
     */
    protected void sendUserInput(DeviceSystem deviceSystem, Action action) {
        deviceSystem.getUserInputSensor().addtoActionPlan(action);
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
        setTickCounter(getTickCounter() + 1);
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
                        addPerformedAction(nextAction);
                    }
                    break;
                case B_REPAIR:
                    if(nextAction.getValue() instanceof IDevice device) {
                        log.info("Repairing device {}", device.getClass().getSimpleName());
                        Manual manual = manualRepo.getManual(device.getClass().getSimpleName());
                        if( manual != null ) {
                            log.info("Manual found, repairing device");
                            device.repair(manual);
                        } else {
                            log.info("Manual not found, repairing device without manual");
                            device.repair();
                        }
                        addPerformedAction(nextAction);
                    }
                    break;
                case B_STARTSPORT:
                    if(nextAction.getValue() instanceof SportEquipmentType) {
                        doSport((SportEquipmentType) nextAction.getValue());
                        addPerformedAction(nextAction);
                    }
                    break;
                case B_STOPSPORT:
                    if(nextAction.getValue() instanceof SportEquipmentType) {
                        stopSport((SportEquipmentType) nextAction.getValue());
                        addPerformedAction(nextAction);
                    }
                    break;
                case B_NOTHING:
                    break;
                default:
                    DeviceSystem deviceSystem = findDeviceSystemInRoom(nextAction.getType());
                    if (deviceSystem != null){
                        sendUserInput(deviceSystem, nextAction);
                        addPerformedAction(nextAction);
                    }
                    break;
            }

        }
    }

    /**
     * Engages the human in a sporting activity using the specified sport equipment.
     * This method prints a message indicating the start of the sport with the given equipment
     * and marks the equipment as being used.
     *
     * @param sportEquipment The sport equipment to be used in the activity.
     */
    public void doSport(SportEquipmentType sportEquipment) {
        if (room instanceof Garage garage){
            garage.useSportEquipment(sportEquipment);
        }
    }

    /**
     * Stops the sporting activity involving the specified sport equipment.
     * This method prints a message indicating the end of the sport with the given equipment
     * and marks the equipment as not being used.
     *
     * @param sportEquipment The sport equipment that was used in the activity.
     */
    public void stopSport(SportEquipmentType sportEquipment) {
        if (room instanceof Garage garage){
            garage.returnSportEquipment(sportEquipment);
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

    /**
     * Accepts a visit from a ReportVisitor, allowing the visitor to perform operations specific to the type of this object.
     * This method is a part of the Visitor design pattern and is used to generate a report based on the type of Human.
     *
     * @param visitor The ReportVisitor visiting this object.
     * @return A Report object specific to the type of this object, as generated by the visitor.
     */
    public Report accept(ReportVisitor visitor) {
        return visitor.visitHuman(this);
    }

    @Override
    public String toString() {
        return "Human{" + "ID=" + beingID + ", name='" + name + '\'' + '}';
    }
}
