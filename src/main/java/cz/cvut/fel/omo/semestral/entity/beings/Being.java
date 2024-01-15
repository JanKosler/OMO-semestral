package cz.cvut.fel.omo.semestral.entity.beings;

import cz.cvut.fel.omo.semestral.entity.actions.Action;
import cz.cvut.fel.omo.semestral.entity.actions.ActionRecord;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
import cz.cvut.fel.omo.semestral.manual.ManualRepo;
import cz.cvut.fel.omo.semestral.tick.Tickable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Abstract class representing a being (like a human or a pet) in the smart home
 * simulation.
 * This class provides common attributes and methods for all beings, such as
 * name, room, and action plan.
 */
@Getter
@Setter
@Slf4j
public abstract class Being implements Tickable {
    /** Unique ID of the being. */
    protected int beingID;
    /** Name of the being. */
    protected String name;
    /** Room where the being is located. */
    protected Room room;
    /** Queue of actions that the being will perform. */
    protected Queue<Action> actionPlan;
    /** List of actions that have been performed by the being. */
    protected List<ActionRecord> performedActions = new LinkedList<>();
    protected int TickCounter = 0;
    protected ManualRepo manualRepo;

    /**
     * Constructs a new Being with the specified action plan, name, and initial
     * room.
     *
     * @param beingID    The ID of the being.
     * @param actionPlan The queue of actions that this being will perform.
     * @param name       The name of the being.
     * @param room       The initial room where the being is located.
     */
    public Being(int beingID, String name, Room room, ManualRepo manualRepo, Queue<Action> actionPlan) {
        this.beingID = beingID;
        this.name = name;
        this.room = room;
        this.actionPlan = actionPlan;
        this.manualRepo = manualRepo;
    }

    /**
     * Constructs a new Being with the specified name and initial room.
     *
     * @param beingID The ID of the being.
     * @param name    The name of the being.
     * @param room    The initial room where the being is located.
     */
    public Being(int beingID, String name, Room room, ManualRepo manualRepo) {
        this(beingID, name, room, manualRepo, new LinkedList<>());
    }

    /**
     * Adds an action to the being's action plan.
     *
     * @param action The action to be added to the plan.
     */
    public void addActionToPlan(Action action) {
        actionPlan.add(action);
    }

    /**
     * Abstract method for performing the next action in the action plan.
     * This method should be implemented by subclasses to define specific behaviors.
     */
    public abstract void performNextAction();

    /**
     * Moves the being to a specified room.
     * If the being is already in the specified room, no action is taken.
     *
     * @param room The room to move the being to.
     */
    public void goTo(Room room) {
        log.info(this.name + " is moving to room " + room.getRoomName() + " from room " + this.room.getRoomName());
        if (this.room == room) {
            return;
        }
        this.room.leaveRoom(this);
        room.enterRoom(this);

        this.room = room;
    }

    /**
     * Simulates the eating action of the being.
     * This method can be overridden or extended in subclasses for specific eating
     * behaviors.
     */
    public void eat() {
        System.out.println(this.name + " eats");
    }

    /**
     * Method called on each tick of the simulation.
     * Subclasses should override this method to define behavior that occurs on each
     * tick.
     */
    @Override
    public void onTick() {
    }

    /**
     * Adds a performed action to the list of actions that have been executed.
     * This method is used for tracking the actions that have been performed,
     * allowing for a record of past actions. This can be useful for auditing,
     * history tracking, or undo functionalities.
     *
     * @param action The action that has been performed and needs to be recorded.
     */
    public void addPerformedAction(Action action) {
        performedActions.add(new ActionRecord(action, TickCounter));
    }

}
