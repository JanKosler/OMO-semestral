package cz.cvut.fel.omo.semestral.tick;

/**
 * Interface representing tickable entities within a simulation.
 * This interface is used to define objects that should perform certain actions
 * or update their state on each tick of the simulation clock.
 */
public interface Tickable {

    /**
     * Method called on each tick of the simulation.
     * Implementing classes should define the actions to be performed on each tick,
     * such as updating the state, processing events, or performing routine activities.
     */
    void onTick();
}
