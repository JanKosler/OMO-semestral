package cz.cvut.fel.omo.semestral.reporting;

import cz.cvut.fel.omo.semestral.entity.beings.Being;
import cz.cvut.fel.omo.semestral.entity.beings.Human;
import cz.cvut.fel.omo.semestral.entity.beings.Pet;
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
    Report visitAppliance(Appliance appliance);
    Report visitSensor(Sensor sensor);
    Report visitController(Controller controller);
    Report visitDeviceSystem(DeviceSystem deviceSystem);
    Report visitHuman(Human human);
    Report visitPet(Pet pet);
    Report visitHouse(House house);
}
