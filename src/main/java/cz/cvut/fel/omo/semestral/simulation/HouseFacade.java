package cz.cvut.fel.omo.semestral.simulation;

import cz.cvut.fel.omo.semestral.entity.beings.Human;
import cz.cvut.fel.omo.semestral.entity.beings.Pet;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.livingSpace.House;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;
import cz.cvut.fel.omo.semestral.reporting.HouseConfigurationReport;
import cz.cvut.fel.omo.semestral.reporting.ReportGenerator;
import cz.cvut.fel.omo.semestral.tick.TickPublisher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Facade for the house.
 * @see House
 * @see HouseConfigurationReport
 */
@NoArgsConstructor
public class HouseFacade {
    /** The house. */
    private House house;

    private SimulationConfig simulationConfig;
    private TickPublisher tickPublisher;

    /**
     * Initializes the simulation and runs it.
     */
    public void runSimulation(String configFilename) {
        if (house == null) {
            initSimulation(configFilename);
            house = simulationConfig.getConfiguredHouse();
        }
        simulate();
    }

    /**
     * Run of the simulation
     */
    private void simulate() {
        tickPublisher = new TickPublisher();

        getDeviceSystems().forEach(tickPublisher::subscribe);
        getHumans().forEach(tickPublisher::subscribe);
        getPets().forEach(tickPublisher::subscribe);

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            tickPublisher.tick();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Initializes the simulation.
     */
    private boolean initSimulation(String configFilename) {
        if (simulationConfig == null) {
            simulationConfig = new SimulationConfig(configFilename);
        }
        return simulationConfig.isLoaded();
    }
    public List<DeviceSystem> getDeviceSystems() {return house.getAllDeviceSystems();}
    public List<IDevice> getDevices(){return house.getAllDevices();}
    public List<Human> getHumans(){return house.getAllPeople();}
    public List<Pet> getPets(){return house.getAllPets();}
}
