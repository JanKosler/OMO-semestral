package cz.cvut.fel.omo.semestral.simulation;

import cz.cvut.fel.omo.semestral.entity.beings.Human;
import cz.cvut.fel.omo.semestral.entity.beings.Pet;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Appliance;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.Controller;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.Sensor;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Floor;
import cz.cvut.fel.omo.semestral.entity.livingSpace.House;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * TODO : Check if this class is needed. Not sure what it should do.
 */
@Getter
@Slf4j
public class SimulationConfig {
    String configFilename;

    /** Extensions of the Being class */
    private List<Pet> petList;
    private List<Human> humanList;

    /** Implementations of the IDevice interface */
    private List<Sensor> sensorList;
    private List<Controller> controllerList;
    private List<Appliance> appliancesList;

    /** Implementations of the ILivingSpace interface */
    private List<Room> roomList;
    private List<Floor> floorList;
    private House house;

    private List<DeviceSystem> deviceSystemList;
    /**
     * Loads configuration from a json file in the ConfigsJSON folder.
     * @param configFilename Name of the file to load configuration from
     */
    public SimulationConfig(String configFilename) {
        this.configFilename = configFilename;
    }

    public void loadConfiguration() {
        log.info("Loading configuration from file: " + configFilename);
    }
}
