package cz.cvut.fel.omo.semestral.simulation;

import cz.cvut.fel.omo.semestral.entity.beings.Human;
import cz.cvut.fel.omo.semestral.entity.beings.Pet;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.livingSpace.House;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;
import cz.cvut.fel.omo.semestral.reporting.HouseConfigurationReport;
import cz.cvut.fel.omo.semestral.reporting.ReportGenerator;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Facade for the house.
 * @see House
 * @see HouseConfigurationReport
 */
@AllArgsConstructor
public class HouseFacade {
    /** List of all pets in the house. */
    private List<Pet> pets;

    /** List of all people in the house. */
    private List<Human> humans;

    /** The house. */
    private HouseDirector houseDirector;

    /** List of all devices in the house. */
    private List<IDevice> devices;

    /** List of all device systems in the house. */
    private List<DeviceSystem> deviceSystems;


    public HouseFacade() {
        pets = new ArrayList<>();
        humans = new ArrayList<>();
        devices = new ArrayList<>();
        deviceSystems = new ArrayList<>();
    }

    /**
     * Starts the simulation.
     */
    public void startSimulation() {

    }


}
