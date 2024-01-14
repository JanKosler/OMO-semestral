package cz.cvut.fel.omo.semestral.reporting;

import cz.cvut.fel.omo.semestral.entity.beings.Human;
import cz.cvut.fel.omo.semestral.entity.beings.Pet;
import cz.cvut.fel.omo.semestral.entity.livingSpace.House;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;
import cz.cvut.fel.omo.semestral.simulation.HouseFacade;

public class EventReport implements ReportVisitor{
    @Override
    public Report visitDeviceSystem(DeviceSystem deviceSystem) {
        return null;
    }

    @Override
    public Report visitHuman(Human human) {
        return null;
    }

    @Override
    public Report visitPet(Pet pet) {
        return null;
    }

    @Override
    public Report visitHouse(House house) {
        return null;
    }

    @Override
    public Report createReport(HouseFacade houseFacade) {
        return null;
    }
}
