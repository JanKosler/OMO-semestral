package cz.cvut.fel.omo.semestral.simulation;

import cz.cvut.fel.omo.semestral.entity.beings.Human;
import cz.cvut.fel.omo.semestral.entity.beings.Pet;
import cz.cvut.fel.omo.semestral.entity.livingSpace.*;
import cz.cvut.fel.omo.semestral.entity.systems.*;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.net.StandardProtocolFamily;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO : Check if this class is needed. Not sure what it should do.
 */
@Slf4j
public class SimulationConfig {
    String _configFilename;

    @Getter
    /* Whether the configuration has been loaded. */
    private boolean isLoaded = false;

    /* Extensions of the Being class */
    /** Map<roomID, List> */
    private final Map<Integer, List<Pet>> _petConfigMap;
    /** Map<roomID, List> */
    private final Map<Integer, List<Human>> _humanConfigMap;

    /* Implementations of the IDevice interface */
    /** Map<roomID, deviceSystemList> */
    private final Map<Integer, List<DeviceSystem>> _deviceSystemConfigMap;
    /** Map<deviceSystemID, deviceSystemName> */
    private final Map<Integer, String> deviceSystemNameByIdMap;

    /* Implementations of the ILivingSpace interface */
    /** Map<FloorID, List> */
    private final Map<Integer, List<Room>> _roomMap;
    private final List<Floor> _floorList;
    private House _house;
    private Garage _garage;

    private Temperature _internalTemperature, _externalTemperature;

    /**
     * Loads configuration from a json file in the ConfigsJSON folder.
     * @param configFilename Name of the file to load configuration from
     */
    public SimulationConfig(String configFilename) {
        this._configFilename = configFilename;
        this._petConfigMap = new HashMap<>();
        this._humanConfigMap = new HashMap<>();
        this._deviceSystemConfigMap = new HashMap<>();
        this._roomMap = new HashMap<>();
        this._floorList = new ArrayList<>();
        this.deviceSystemNameByIdMap = new HashMap<>();
    }

    public House getConfiguredHouse() throws SimulationException {
        if (!isLoaded)
            loadConfigIntoConfigMaps();
        if (!isLoaded)
            throw new SimulationException("Configuration not loaded.");
        return createConfiguredHouse();
    }



    private House createConfiguredHouse() {
        log.info("[CONFIG][HOUSE] Creating configured house...");
        List<Floor> configuredFloors = new ArrayList<>();
        // create rooms
        for (Floor floor : _floorList) {
            /* Map<floorID, List> */
            Map<Integer, List<Room>> configuredRooms = new HashMap<>();
            // add rooms to the floor
            for (Room room : _roomMap.get(floor.getFloorID())) {
                List<Pet> pets = _petConfigMap.get(room.getRoomID());
                List<Human> humans = _humanConfigMap.get(room.getRoomID());
                List<DeviceSystem> deviceSystems = _deviceSystemConfigMap.get(room.getRoomID());

                RoomBuilder configuredRoomBuilder = Room.roomBuilder()
                        .setRoomID(room.getRoomID())
                        .setRoomName(room.getRoomName());
                // add pets to the room
                if (pets != null)
                    for (Pet pet : pets)
                        configuredRoomBuilder.addPerson(pet);
                // add humans to the room
                if (humans != null)
                    for (Human human : humans)
                        configuredRoomBuilder.addPerson(human);

                Room configuredRoom = configuredRoomBuilder.build();

                // add configured room to human and pet object -> used for their impl of Being::goTo() method
                configuredRoom.getAllPeople().forEach(person -> person.setRoom(configuredRoom));
                configuredRoom.getAllPets().forEach(pet -> pet.setRoom(configuredRoom));

                for(DeviceSystem deviceSystem : deviceSystems) {
                    DeviceSystem newDeviceSystem = this.createSystemByType(
                            deviceSystem.getDeviceSystemID(),
                            deviceSystemNameByIdMap.get(deviceSystem.getDeviceSystemID()),
                            configuredRoom,
                            _internalTemperature,
                            _externalTemperature
                    );
                    configuredRoom.addDeviceSystem(newDeviceSystem);
                }

                configuredRooms.computeIfAbsent(floor.getFloorID(), k -> new ArrayList<>())
                        .add(configuredRoom);
            }

            configuredFloors.add(new Floor(floor.getFloorID(), floor.getFloorName(), floor.getFloorLevel(), configuredRooms.get(floor.getFloorID())));

        }
        log.info("[CONFIG][HOUSE] Configured house successfully created.");
        House configuredHouse = new House(_house.getHouseID(), _house.getHouseNumber(), _house.getAddress(), _house.getInternalTemperature(), _house.getExternalTemperature(), configuredFloors);
        configuredHouse.setGarage(_garage);
        return configuredHouse;
    }

    public void loadConfigIntoConfigMaps() {
        log.info("[CONFIG][PATH] Loading configuration from file: " + _configFilename);

        ObjectMapper mapper = new ObjectMapper();
        // Absolute path to JSON configuration file
        log.info("[CONFIG][PATH] Current working directory: " + System.getProperty("user.dir"));
        log.info("[CONFIG][PATH] Config path " + System.getProperty("user.dir") + "/config/" + _configFilename);

        File file = new File(System.getProperty("user.dir") + "/config/" + _configFilename);
        String absoluteConfigPath = file.getAbsolutePath();

        if (!file.exists()) {
            log.error("Configuration file does not exist.");
            log.error("Configuration loading failed.");
            isLoaded = false;
            return;
        }

        try {
            JsonNode jsonObject = mapper.readTree(new File(absoluteConfigPath));
            log.info("[CONFIG][PARSING] Parsing JSON file...");

            /* CONFIGURATION OF HOUSE */
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

            log.info("[CONFIG][PARSING] House successfully initialized.");

            /* CONFIGURATION OF GARAGE */
            // Create garage object
            JsonNode garage = jsonObject.get("Garage");
            int garageID = garage.get("garageID").asInt();
            String garageName = garage.get("garageName").asText();
            int sportEquipmentCountBIKE = garage.get("sportEquipmentCountBIKE").asInt();
            int sportEquipmentCountSKATES = garage.get("sportEquipmentCountSKATES").asInt();
            int sportEquipmentCountSKIS = garage.get("sportEquipmentCountSKIS").asInt();
            List<SportEquipment> sportEquipmentList = this.createSportEquipmentList(sportEquipmentCountBIKE, sportEquipmentCountSKATES, sportEquipmentCountSKIS);
            this._garage = Garage.garageBuilder()
                    .setRoomID(garageID)
                    .setRoomName(garageName)
                    .addSportEquipment(sportEquipmentList)
                    .build();

            log.info("[CONFIG][PARSING] Garage successfully initialized.");

            /* CONFIGURATION OF FLOORS */
            // Create floors from config and add them to the config map
            JsonNode floors = jsonObject.get("Floors");
            for (JsonNode floor : floors) {
                int floorID = floor.get("floorID").asInt();
                String floorName = floor.get("floorName").asText();
                int floorLevel = floor.get("floorLevel").asInt();
                Floor newFloor = new Floor(floorID, floorName, floorLevel, null);
                this._floorList.add(newFloor);
            }

            log.info("[CONFIG][PARSING] Floors successfully initialized.");

            /* CONFIGURATION OF ROOMS */
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

            log.info("[CONFIG][PARSING] Rooms successfully initialized.");

            /* CONFIGURATION OF PETS */
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

            log.info("[CONFIG][PARSING] Pets successfully initialized.");

            /* CONFIGURATION OF HUMANS */
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

            log.info("[CONFIG][PARSING] Humans successfully initialized.");

            /* CONFIGURATION OF DEVICE SYSTEMS */
            // Create device systems from config and add them to the device system config map
            JsonNode deviceSystems = jsonObject.get("DeviceSystems");
            for (JsonNode deviceSystem : deviceSystems) {
                int deviceSystemID = deviceSystem.get("systemID").asInt();
                String deviceSystemName = deviceSystem.get("systemName").asText();
                int deviceSystemRoomID = deviceSystem.get("roomID").asInt();

                // Check if the room is found, then add the device system to the map
                this._deviceSystemConfigMap.computeIfAbsent(deviceSystemRoomID, k -> new ArrayList<>())
                        .add(this.createSystemByType(deviceSystemID, deviceSystemName, null, _internalTemperature, _externalTemperature));
                deviceSystemNameByIdMap.put(deviceSystemID, deviceSystemName);
            }

            log.info("[CONFIG][PARSING] Device systems successfully initialized.");
            log.info("[CONFIG][PARSING] Configuration successfully loaded.");

            isLoaded = true;

        } catch (Exception e) {
            log.error("Error while parsing JSON file.");
            log.error("Configuration loading failed.");
            isLoaded = false;
            return;
        }
    }
    private void logConfiguration() {
        log.info("Configuration:");
        log.info("House: " + _house.toString());
        log.info("Floors: " + _floorList.toString());
        log.info("Rooms: " + _roomMap.toString());
        log.info("Pets: " + _petConfigMap.toString());
        log.info("Humans: " + _humanConfigMap.toString());
        log.info("Device systems: " + _deviceSystemConfigMap.toString());
    }

    private void logConfiguredHouse(House house) {
        log.info("Configured house: " + house.toString());
        house.getFloors().forEach(floor -> log.info("Floor: " + floor.toString()));
        house.getFloors().forEach(floor -> floor.getRooms().forEach(room -> log.info("Room: " + room.toString())));
        house.getFloors().forEach(floor -> floor.getRooms().forEach(room -> room.getAllPeople().forEach(person -> log.info("Person: " + person.toString()))));
        house.getFloors().forEach(floor -> floor.getRooms().forEach(room -> room.getAllPets().forEach(pet -> log.info("Pet: " + pet.toString()))));
        house.getFloors().forEach(floor -> floor.getRooms().forEach(room -> room.getAllDeviceSystems().forEach(deviceSystem -> log.info("Device system: " + deviceSystem.toString()))));
    }
        private List<SportEquipment> createSportEquipmentList(int sportEquipmentCountBIKE, int sportEquipmentCountSKATES, int sportEquipmentCountSKIS) {
            List<SportEquipment> sportEquipmentList = new ArrayList<>();
            for (int i = 0; i < sportEquipmentCountBIKE; i++)
                sportEquipmentList.add(new SportEquipment(SportEquipmentType.BIKE));
            for (int i = 0; i < sportEquipmentCountSKATES; i++)
                sportEquipmentList.add(new SportEquipment(SportEquipmentType.SKATES));
            for (int i = 0; i < sportEquipmentCountSKIS; i++)
                sportEquipmentList.add(new SportEquipment(SportEquipmentType.SKIS));
            return sportEquipmentList;
        }
        private DeviceSystem createSystemByType(int deviceSystemID, String deviceSystemName, Room room, Temperature internalTemp, Temperature externalTemp) {
            DeviceSystemFactory factory = new DeviceSystemFactory();
            return switch (deviceSystemName) {
                case "FridgeSystem" -> factory.createFridgeSystem(deviceSystemID);
                case "GateControlSystem" -> factory.createGateControlSystem(deviceSystemID);
                case "HVACSystem" -> factory.createHVACSystem(deviceSystemID, internalTemp, externalTemp);
                case "LightingSystem" -> factory.createLightingSystem(deviceSystemID,room);
                case "SecuritySystem" -> factory.createSecuritySystem(deviceSystemID);
                case "TVSystem" -> factory.createEntertainmentSystem(deviceSystemID);
                default -> null;
            };
        }
}
