package cz.cvut.fel.omo.semestral.simulation;

import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.actions.Action;
import cz.cvut.fel.omo.semestral.entity.beings.Human;
import cz.cvut.fel.omo.semestral.entity.beings.Pet;
import cz.cvut.fel.omo.semestral.entity.devices.DeviceMalfunctionObserver;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.livingSpace.*;
import cz.cvut.fel.omo.semestral.entity.systems.*;
import cz.cvut.fel.omo.semestral.reporting.HouseConfigurationReport;
import cz.cvut.fel.omo.semestral.reporting.ReportGenerator;
import cz.cvut.fel.omo.semestral.reporting.ReportType;
import cz.cvut.fel.omo.semestral.tick.TickPublisher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static cz.cvut.fel.omo.semestral.reporting.ReportType.*;

/**
 * Facade for the house.
 * @see House
 * @see HouseConfigurationReport
 */
@NoArgsConstructor
@Slf4j
public class HouseFacade implements DeviceMalfunctionObserver {
    /** The house. */
    @Getter
    private House house;
    private SimulationConfig simulationConfig;
    private TickPublisher tickPublisher;

    @Getter
    int numberOfTicks = 200;

    /**
     * Initializes the simulation and runs it.
     * @param configFilename Name of the configuration file.
     *
     * @throws SimulationException If error occurs during the simulation or the configuration is not loaded.
     */
    public void runSimulation(String configFilename) throws SimulationException {
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

        log.info("[SIMULATION] Simulation started");

        // checks if all things house are loaded
        // logHouseObject();


        tickPublisher = new TickPublisher();
        // Subscribe all devices, humans and pets to the tick publisher
        getHumans().forEach(tickPublisher::subscribe);
        getPets().forEach(tickPublisher::subscribe);
        getDeviceSystems().forEach(tickPublisher::subscribe);

        // Add the malfunction observer to all devices
        for(DeviceSystem devicesystem : getDeviceSystems()) {
            for(IDevice device : devicesystem.getDevices()) {
                device.addMalfunctionObserver(this);
            }
        }

        Human john_doe = getHumanByName("John Doe");
        Human jane_doe = getHumanByName("Jane Doe");
        Human james_doe = getHumanByName("James Doe");

        Room kitchen = getRoomByName("Kitchen");
        Room livingRoom = getRoomByName("Living Room");
        Room bedroom = getRoomByName("Bedroom");
        Room bathroom = getRoomByName("Bathroom");
        Room garage = getRoomByName("Garage");

        Queue<Action> johnActionList = new LinkedList<>();
        johnActionList.add(new Action(UserInputType.B_CHANGEROOM, livingRoom));
        johnActionList.add(new Action(UserInputType.B_NOTHING, null));
        johnActionList.add(new Action(UserInputType.TV_POWER, null));
        johnActionList.add(new Action(UserInputType.TV_VOLUME, 5));
        johnActionList.add(new Action(UserInputType.TV_CHANNEL, 1));
        johnActionList.add(new Action(UserInputType.TV_POWER, null));
        johnActionList.add(new Action(UserInputType.B_NOTHING, null));
        johnActionList.add(new Action(UserInputType.B_NOTHING, null));
        johnActionList.add(new Action(UserInputType.HVAC_TEMPERATURE, 50.0));
        johnActionList.add(new Action(UserInputType.B_CHANGEROOM, bedroom));
        johnActionList.add(new Action(UserInputType.B_NOTHING, null));
        johnActionList.add(new Action(UserInputType.B_NOTHING, null));
        johnActionList.add(new Action(UserInputType.B_CHANGEROOM, bathroom));
        johnActionList.add(new Action(UserInputType.B_CHANGEROOM, kitchen));
        johnActionList.add(new Action(UserInputType.FRIDGE_TEMPERATURE, 6));
        johnActionList.add(new Action(UserInputType.B_NOTHING, null));
        johnActionList.add(new Action(UserInputType.B_NOTHING, null));
        johnActionList.add(new Action(UserInputType.B_NOTHING, null));
        johnActionList.add(new Action(UserInputType.B_CHANGEROOM, livingRoom));
        johnActionList.add(new Action(UserInputType.HVAC_TEMPERATURE, 7.0));
        john_doe.setActionPlan(johnActionList);

        Queue<Action> janeActionList = new LinkedList<>();
        janeActionList.add(new Action(UserInputType.B_CHANGEROOM, bathroom));
        janeActionList.add(new Action(UserInputType.B_CHANGEROOM, kitchen));
        janeActionList.add(new Action(UserInputType.FRIDGE_TEMPERATURE, 5));
        janeActionList.add(new Action(UserInputType.B_CHANGEROOM, bathroom));
        janeActionList.add(new Action(UserInputType.B_CHANGEROOM, livingRoom));
        janeActionList.add(new Action(UserInputType.TV_POWER, true));
        janeActionList.add(new Action(UserInputType.TV_VOLUME, 7));
        janeActionList.add(new Action(UserInputType.TV_CHANNEL, 3));
        janeActionList.add(new Action(UserInputType.TV_POWER, false));
        janeActionList.add(new Action(UserInputType.HVAC_TEMPERATURE, 15.0));
        jane_doe.setActionPlan(janeActionList);

        Queue<Action> jamesActionList = new LinkedList<>();
        jamesActionList.add(new Action(UserInputType.B_CHANGEROOM, bedroom));
        jamesActionList.add(new Action(UserInputType.B_CHANGEROOM, bathroom));
        jamesActionList.add(new Action(UserInputType.B_CHANGEROOM, kitchen));
        jamesActionList.add(new Action(UserInputType.FRIDGE_TEMPERATURE, 4));
        jamesActionList.add(new Action(UserInputType.B_CHANGEROOM, livingRoom));
        jamesActionList.add(new Action(UserInputType.B_NOTHING, null));
        jamesActionList.add(new Action(UserInputType.B_NOTHING, null));
        jamesActionList.add(new Action(UserInputType.B_NOTHING, null));
        jamesActionList.add(new Action(UserInputType.B_NOTHING, null));
        jamesActionList.add(new Action(UserInputType.B_NOTHING, null));
        jamesActionList.add(new Action(UserInputType.B_NOTHING, null));
        jamesActionList.add(new Action(UserInputType.B_NOTHING, null));
        jamesActionList.add(new Action(UserInputType.B_NOTHING, null));
        jamesActionList.add(new Action(UserInputType.B_NOTHING, null));
        jamesActionList.add(new Action(UserInputType.B_NOTHING, null));
        jamesActionList.add(new Action(UserInputType.B_NOTHING, null));
        jamesActionList.add(new Action(UserInputType.B_NOTHING, null));
        jamesActionList.add(new Action(UserInputType.B_NOTHING, null));
        jamesActionList.add(new Action(UserInputType.B_NOTHING, null));
        jamesActionList.add(new Action(UserInputType.HVAC_TEMPERATURE, 25.0));
        jamesActionList.add(new Action(UserInputType.B_CHANGEROOM, bathroom));
        jamesActionList.add(new Action(UserInputType.B_CHANGEROOM, garage));
        jamesActionList.add(new Action(UserInputType.B_STARTSPORT, SportEquipmentType.SKIS));
        jamesActionList.add(new Action(UserInputType.B_NOTHING, null));
        jamesActionList.add(new Action(UserInputType.B_NOTHING, null));
        jamesActionList.add(new Action(UserInputType.B_NOTHING, null));
        jamesActionList.add(new Action(UserInputType.B_STOPSPORT, SportEquipmentType.SKIS));
        jamesActionList.add(new Action(UserInputType.B_NOTHING, null));
        jamesActionList.add(new Action(UserInputType.B_CHANGEROOM, livingRoom));
        jamesActionList.add(new Action(UserInputType.HVAC_TEMPERATURE, 27.0));
        james_doe.setActionPlan(jamesActionList);


        for (int i = 0; i < numberOfTicks; i++) {
            log.info("[SIMULATION] Tick " + i);
            tickPublisher.tick();
        }

        log.info("[SIMULATION] Simulation ended");

        ReportGenerator reportGenerator = new ReportGenerator();
        reportGenerator.generateReportAndSaveToFile(ACTIVITY_USAGE, this, System.getProperty("user.dir") + "/reports/");
        reportGenerator.generateReportAndSaveToFile(CONFIGURATION, this, System.getProperty("user.dir") + "/reports/");
        reportGenerator.generateReportAndSaveToFile(CONSUMPTION, this, System.getProperty("user.dir") + "/reports/");
        reportGenerator.generateReportAndSaveToFile(EVENT, this, System.getProperty("user.dir") + "/reports/");

    }




    /**
     * Initializes the simulation.
     *
     * @param configFilename Name of the configuration file.
     */
    private void initSimulation(String configFilename) {
        if (simulationConfig == null) {
            simulationConfig = new SimulationConfig(configFilename);
        }
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

    private void logHouseObject() {
        log.info("Logging house started");

        for (Floor floor : house.getFloors()) {
            log.info("Floor " + floor.getFloorName() + " has " + floor.getRooms().size() + " rooms");
        }

        List<Room> rooms = house.getFloors().stream().flatMap(f->f.getRooms().stream()).toList();
        for (Room room : rooms) {
            log.info("Room " + room.getRoomName());
        }

        for (DeviceSystem deviceSystem : house.getAllDeviceSystems()) {
            log.info("Device system " + deviceSystem.getDeviceSystemID() + " has " + deviceSystem.getDevices().size() + " devices");
        }

        for (Human human : house.getAllPeople()) {
            log.info("Human " + human.getName());
        }
        log.info("Logging house ended");
    }

    @Override
    public void onDeviceMalfunction(IDevice device) {
        getHumans().get(0).getActionPlan().add(new Action(UserInputType.B_REPAIR, device));
        log.info(getHumans().get(0).getName() + " has added repair action to the action plan.");
    }
}
