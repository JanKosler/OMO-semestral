package cz.cvut.fel.omo.semestral.reporting;

import cz.cvut.fel.omo.semestral.entity.beings.Being;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Appliance;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.Controller;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.Sensor;
import cz.cvut.fel.omo.semestral.entity.livingSpace.House;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;

/**
 * Visitor interface for generating reports.
 * @see ReportGenerator
 */
public interface ReportVisitor {
    // TODO: this seems okay
    Report visit(Appliance appliance);
    Report visit(Sensor sensor);
    Report visit(Controller controller);
    Report visit(DeviceSystem deviceSystem);
    Report visit(Being being);
    Report visit(House house);
}
