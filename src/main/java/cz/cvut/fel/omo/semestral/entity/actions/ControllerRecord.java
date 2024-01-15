package cz.cvut.fel.omo.semestral.entity.actions;

import cz.cvut.fel.omo.semestral.entity.devices.controllers.Controller;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ControllerRecord {

    private int tick;
    private Controller controller;
    private String action;

}
