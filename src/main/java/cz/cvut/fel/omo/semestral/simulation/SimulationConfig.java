package cz.cvut.fel.omo.semestral.simulation;

import cz.cvut.fel.omo.semestral.entity.beings.Human;
import cz.cvut.fel.omo.semestral.entity.beings.Pet;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Floor;
import cz.cvut.fel.omo.semestral.entity.livingSpace.House;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Temperature;
import cz.cvut.fel.omo.semestral.entity.systems.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO : Check if this class is needed. Not sure what it should do.
 */
@Getter
@Slf4j
public class SimulationConfig {
    String _configFilename;

    @Getter
    /** Whether the configuration has been loaded. */
    private boolean _isLoaded = false;

    /** Extensions of the Being class */
    /** Map<roomID, List> */
    private Map<Integer, List<Pet>> _petConfigMap;
    private Map<Integer, List<Human>> _humanConfigMap;

    /** Implementations of the IDevice interface */
    /*
    private List<Sensor> sensorList;
    private List<Controller> controllerList;
    private List<Appliance> appliancesList;
     */
    /** Map<roomID, deviceSystemList> */
    private Map<Integer, List<DeviceSystem>> _deviceSystemConfigMap;


    /** Implementations of the ILivingSpace interface */
    /** Map<FloorID, List> */
    private Map<Integer, List<Room>> _roomMap;
    private List<Floor> _floorList;
    private House _house;

    private Temperature _internalTemperature, _externalTemperature;

    /**
     * Loads configuration from a json file in the ConfigsJSON folder.
     * @param configFilename Name of the file to load configuration from
     */
    public SimulationConfig(String configFilename) {
        this._configFilename = configFilename;
    }

    public House getConfiguredHouse() {
        if (!_isLoaded)
            loadConfigIntoConfigMaps();
        return createConfiguredHouse();
    }

    private House createConfiguredHouse() {
        // add people to rooms and floors and house



        return new House(_house.getHouseID(), _house.getHouseNumber(), _house.getAddress(), _house.getInternalTemperature(), _house.getExternalTemperature(), );
    }

    public void loadConfigIntoConfigMaps() {
        log.info("Loading configuration from file: " + _configFilename);

        ObjectMapper mapper = new ObjectMapper();
        // Absolute path to JSON configuration file
        File file = new File("ConfigsJSON/" + _configFilename);
        String absoluteConfigPath = file.getAbsolutePath();

        if (!file.exists()) {
            log.error("Configuration file does not exist.");
            log.error("Configuration loading failed.");
            _isLoaded = false;
            return;
        }

        try {
            JsonNode jsonObject = mapper.readTree(new File(absoluteConfigPath));
            System.out.println("[Config] JSON parsed...");

            /** CONFIGURATION OF HOUSE */
            // Create house object
            JsonNode house = jsonObject.get("House");
            int houseID = house.get("houseID").asInt();
            int houseNumber = house.get("houseNumber").asInt();
            String address = house.get("address").asText();
            int internalTemp = house.get("internalTemperature").asInt();
            int externalTemp = house.get("externalTemperature").asInt();
            this._internalTemperature = new Temperature(internalTemp);
            this._externalTemperature = new Temperature(externalTemp);
            this._house = new House(houseID, houseNumber, address, this._internalTemperature, this._externalTemperature);

            /** CONFIGURATION OF FLOORS */
            // Create floors from config and add them to the config map
            JsonNode floors = jsonObject.get("Floors");
            for (JsonNode floor : floors) {
                int floorID = floor.get("floorID").asInt();
                String floorName = floor.get("floorName").asText();
                int floorLevel = floor.get("floorLevel").asInt();
                Floor newFloor = new Floor(floorID, floorName, floorLevel, null);
                this._floorList.add(newFloor);
            }

            /** CONFIGURATION OF ROOMS */
            // Create rooms from config and add them to the config map
            JsonNode rooms = jsonObject.get("Rooms");
            for (JsonNode room : rooms) {
                int roomID = room.get("roomID").asInt();
                String roomName = room.get("roomName").asText();
                int floorID = room.get("floorID").asInt();
                Room tmpRoom = Room.roomBuilder()
                        .setRoomID(roomID)
                        .setRoomName(roomName)
                        .build();
                this._roomMap.computeIfAbsent(floorID, k -> new ArrayList<>())
                        .add(tmpRoom);
            }

            /** CONFIGURATION OF PETS */
            // Create pets from config and add them to the pet config map
            JsonNode pets = jsonObject.get("Pets");
            for (JsonNode pet : pets) {
                int petID = pet.get("petID").asInt();
                String petName = pet.get("petName").asText();
                int petRoomID = pet.get("roomID").asInt();
                Pet tmpPet = new Pet(petID, petName, null);
                this._petConfigMap.computeIfAbsent(petRoomID, k -> new ArrayList<>())
                        .add(tmpPet);
            }

            /** CONFIGURATION OF HUMANS */
            // Create humans from config and add them to the human config map
            JsonNode humans = jsonObject.get("Humans");
            for (JsonNode human : humans) {
                int personID = human.get("personID").asInt();
                String personName = human.get("personName").asText();
                int personRoomID = human.get("roomID").asInt();
                Human tmpHuman = new Human(personID, personName, null);
                this._humanConfigMap.computeIfAbsent(personRoomID, k -> new ArrayList<>())
                        .add(tmpHuman);
            }

            /** CONFIGURATION OF DEVICE SYSTEMS */
            // Create device systems from config and add them to the device system config map
            JsonNode deviceSystems = jsonObject.get("DeviceSystems");
            for (JsonNode deviceSystem : deviceSystems) {
                int deviceSystemID = deviceSystem.get("systemID").asInt();
                String deviceSystemName = deviceSystem.get("systemName").asText();
                int deviceSystemRoomID = deviceSystem.get("roomID").asInt();
                this._deviceSystemConfigMap.computeIfAbsent(deviceSystemRoomID, k -> new ArrayList<>())
                        .add(this.createSystemByType(deviceSystemID, deviceSystemName));
            }

            _isLoaded = true;


        } catch (Exception e) {
            log.error("Error while parsing JSON file.");
            log.error("Configuration loading failed.");
            _isLoaded = false;
            return;
        }
    }
        private DeviceSystem createSystemByType(int deviceSystemID, String deviceSystemName) {
            return switch (deviceSystemName) {
                case "FridgeSystem" ->
                        new FridgeSystem();
                case "GateControlSystem" ->
                        new GateControlSystem();
                case "HVACSystem" ->
                        new HVACSystem();
                case "LightingSystem" ->
                        new LightingSystem();
                case "SecuritySystem" ->
                        new SecuritySystem();
                case "TVSystem" ->
                        new TVSystem();
                default -> null;
            };
        }
}
