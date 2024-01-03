package cz.cvut.fel.omo.semestral.entity.beings;


import cz.cvut.fel.omo.semestral.entity.actions.Action;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
import cz.cvut.fel.omo.semestral.tick.Tickable;

import java.util.Queue;

public class Pet extends Being implements Tickable {
    // Additional pet-specific attributes and methods...

    public Pet(String name, Queue<Action> actionPlan, Room room) {
        super(actionPlan, name, room);
        // Initialize pet-specific attributes
    }

    @Override
    public void onTick() {
        performNextAction();
    }

    @Override
    public void performNextAction() {

    }
}
