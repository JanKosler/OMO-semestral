package cz.cvut.fel.omo.semestral.entity.beings;

import cz.cvut.fel.omo.semestral.entity.actions.Action;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
import cz.cvut.fel.omo.semestral.tick.Tickable;
import lombok.Getter;
import lombok.Setter;

import java.util.Queue;

/**
 * Abstract class representing a being (like a human or a pet) in the smart home simulation.
 * This class provides common attributes and methods for all beings, such as name, room, and action plan.
 */
@Getter
@Setter
public abstract class Being implements Tickable {
    protected String name;
    protected Room room;
    protected Queue<Action> actionPlan;

    /**
     * Constructs a new Being with the specified action plan, name, and initial room.
     *
     * @param actionPlan The queue of actions that this being will perform.
     * @param name       The name of the being.
     * @param room       The initial room where the being is located.
     */
    public Being(Queue<Action> actionPlan, String name, Room room) {
        this.name = name;
        this.actionPlan = actionPlan;
        this.room = room;
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
    public void goTo(Room room){
        if (this.room == room) {
            return;
        }

        this.room = room;
    }

    /**
     * Simulates the eating action of the being.
     * This method can be overridden or extended in subclasses for specific eating behaviors.
     */
    public void eat() {
        System.out.println(this.name + " eats");
    }

    /**
     * Method called on each tick of the simulation.
     * Subclasses should override this method to define behavior that occurs on each tick.
     */
    @Override
    public void onTick() {
    }

}
