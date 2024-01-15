package cz.cvut.fel.omo.semestral.entity.actions;

import cz.cvut.fel.omo.semestral.entity.devices.controllers.Controller;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
/**
 * Represents a record of a controller action performed at a specific time tick.
 * This class encapsulates the details of an action and the tick (time point)
 * at which the action occurred.
 */
public class ControllerRecord {

    /**
     * The action performed.
     * This field holds an instance of the Action class, representing
     * the specific action that was carried out.
     */
    private int tick;
    /**
     * The controller that performed the action.
     * This field holds an instance of the Controller class, representing
     * the specific controller that performed the action.
     */
    private Controller controller;
    /**
     * The action performed.
     * This field holds an instance of the Action class, representing
     * the specific action that was carried out.
     */
    private String action;

}
