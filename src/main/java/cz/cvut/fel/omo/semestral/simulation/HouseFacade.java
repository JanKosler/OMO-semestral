package cz.cvut.fel.omo.semestral.simulation;

import cz.cvut.fel.omo.semestral.entity.beings.Human;
import cz.cvut.fel.omo.semestral.entity.beings.Pet;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Floor;
import cz.cvut.fel.omo.semestral.entity.livingSpace.House;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;
import cz.cvut.fel.omo.semestral.reporting.HouseConfigurationReport;
import cz.cvut.fel.omo.semestral.tick.TickPublisher;
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
     * @param configFilename Name of the configuration file.
     */
    private boolean initSimulation(String configFilename) {
        if (simulationConfig == null) {
            simulationConfig = new SimulationConfig(configFilename);
        }
        return simulationConfig.isLoaded();
    }

    /**
     * Gets a room by its name.
     * @param name Name of the room.
     * @return Room with the given name.
     */
    private Room getRoomByName(String name) {
        return house.getFloors().stream()
                .flatMap(floor -> floor.getRooms().stream())
                .filter(room -> room.getRoomName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets a floor by its name.
     * @param name Name of the floor.
     * @return Floor with the given name.
     */
    private Floor getFloorByName(String name) {
        return house.getFloors().stream()
                .filter(floor -> floor.getFloorName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets a floor by its level.
     * @param level Level of the floor.
     * @return Floor with the given level.
     */
    private Floor getFloorByLevel(int level) {
        return house.getFLoor(level);
    }

    /**
     * Gets a human by its name.
     * @param name Name of the human.
     * @return Human with the given name.
     */
    private Human getHumanByName(String name) {
        return house.getAllPeople().stream()
                .filter(human -> human.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets a pet by its name.
     * @param name Name of the pet.
     * @return Pet with the given name.
     */
    private Pet getPetByName(String name) {
        return house.getAllPets().stream()
                .filter(pet -> pet.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets a device by its id
     * @param id ID of the device.
     * @return Device with the given id.
     */
    private DeviceSystem getDeviceSystemByID(int id) {
        return house.getAllDeviceSystems().stream()
                .filter(deviceSystem -> deviceSystem.getDeviceSystemID() == id)
                .findFirst()
                .orElse(null);
    }

    public List<DeviceSystem> getDeviceSystems() {return house.getAllDeviceSystems();}
    public List<IDevice> getDevices(){return house.getAllDevices();}
    public List<Human> getHumans(){return house.getAllPeople();}
    public List<Pet> getPets(){return house.getAllPets();}
}
