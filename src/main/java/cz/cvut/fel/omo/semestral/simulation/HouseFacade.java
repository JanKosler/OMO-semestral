package cz.cvut.fel.omo.semestral.simulation;

import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.actions.Action;
import cz.cvut.fel.omo.semestral.entity.beings.Human;
import cz.cvut.fel.omo.semestral.entity.beings.Pet;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.livingSpace.House;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
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

        // Subscribe all devices, humans and pets to the tick publisher
        getDeviceSystems().forEach(tickPublisher::subscribe);
        getHumans().forEach(tickPublisher::subscribe);
        getPets().forEach(tickPublisher::subscribe);

        List<Human> humans = new ArrayList<>(getHumans());
        List<Pet> pets = new ArrayList<>(getPets());
        /*List<Room> rooms = new ArrayList<>(house.getAllRooms());

        List<Action> human1action = new ArrayList<>();
        human1action.add(new Action(UserInputType.B_CHANGEROOM, rooms.get(0)));
        human1action.add(new Action(UserInputType.TV_POWER, null));
        human1action.add(new Action(UserInputType.TV_VOLUME, 50));
        human1action.add(new Action(UserInputType.TV_CHANNEL, 1));
        human1action.add(new Action(UserInputType.TV_POWER, null));
        human1action.add(new Action(UserInputType.B_CHANGEROOM, rooms.get(1)));
        human1action.add(new Action(UserInputType.HVAC_TEMPERATURE, 20));
        */

        for (int i = 0; i < 500; i++) {
            tickPublisher.tick();
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
