package cz.cvut.fel.omo.semestral.entity.beings;


import cz.cvut.fel.omo.semestral.entity.actions.Action;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;
import cz.cvut.fel.omo.semestral.reporting.Report;
import cz.cvut.fel.omo.semestral.reporting.ReportVisitor;
import cz.cvut.fel.omo.semestral.tick.Tickable;

import java.util.Queue;

/**
 * Represents a pet in the smart home simulation.
 * * This class extends the {@link Being} class and adds pet-specific functionalities.
 */
public class Pet extends Being implements Tickable {
    /**
     * Constructs a new Pet with the specified name, action plan, and initial room.
     * @param petID The ID of the pet.
     * @param name The name of the pet.
     * @param room The initial room where the pet is located.
     * @param actionPlan The queue of actions that this pet will perform.
     */
    public Pet(int petID, String name, Room room, Queue<Action> actionPlan) {
        super(petID, name, room, actionPlan);
    }

    /**
     * Constructs a new Pet with the specified name, action plan, and initial room.
     * @param petID The ID of the pet.
     * @param name The name of the pet.
     * @param room The initial room where the pet is located.
     */
    public Pet(int petID, String name, Room room) {
        super(petID, name, room);
    }


    @Override
    public void onTick() {
        performNextAction();
    }

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
                    break;
            }

        }
    }

    public Report accept(ReportVisitor visitor) {
        return visitor.visitPet(this);
    }
}
