package cz.cvut.fel.omo.semestral.tick;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the ticking mechanism for a simulation, notifying subscribed entities on each tick.
 * This class acts as a central publisher for tick events, allowing various components of the simulation
 * to stay synchronized with the simulation clock.
 */
public class TickPublisher {
    private final List<Tickable> tickables = new ArrayList<>();
    private int tickCount = 0;

    /**
     * Subscribes a Tickable entity to receive tick updates.
     *
     * @param tickable The Tickable entity to be subscribed.
     */
    public void subscribe(Tickable tickable) {
        tickables.add(tickable);
    }

    /**
     * Unsubscribes a Tickable entity from receiving tick updates.
     *
     * @param tickable The Tickable entity to be unsubscribed.
     */
    public void unsubscribe(Tickable tickable) {
        tickables.remove(tickable);
    }

    /**
     * Triggers a tick event, notifying all subscribed Tickable entities.
     * Each subscribed entity will have its onTick method called.
     */
    public void tick() {
        tickCount++;
        for (Tickable tickable : tickables) {
            tickable.onTick();
        }
    }

    /**
     * Retrieves the current count of ticks since the start of the simulation.
     *
     * @return The number of ticks that have occurred.
     */
    public int getTickCount() {
        return tickCount;
    }
}
