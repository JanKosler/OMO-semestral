package cz.cvut.fel.omo.semestral.simulation;

import cz.cvut.fel.omo.semestral.entity.beings.Human;
import cz.cvut.fel.omo.semestral.entity.beings.Pet;
import cz.cvut.fel.omo.semestral.entity.livingSpace.*;
import cz.cvut.fel.omo.semestral.entity.systems.*;

import cz.cvut.fel.omo.semestral.manual.ManualRepo;
import cz.cvut.fel.omo.semestral.manual.ManualRepoProxy;
import cz.cvut.fel.omo.semestral.manual.OfflineManualDatabase;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.*;

/**
 * This class represents the configuration for a simulation. It loads the configuration from a JSON file and creates a configured house based on the loaded data.
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
    private final OfflineManualDatabase _offlineManualDatabase;
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
        this._offlineManualDatabase = new OfflineManualDatabase();
    }

    /**
     * Get the configured house based on the loaded configuration.
     *
     * @return Configured house
     * @throws SimulationException If there is an issue with loading or creating the configuration.
     */
    public House getConfiguredHouse() throws SimulationException {
        if (!isLoaded)
            loadConfigIntoConfigMaps();
        if (!isLoaded)
            throw new SimulationException("Configuration not loaded.");
        try {
            House configuredHouse = createConfiguredHouse();
            logConfiguredHouse(configuredHouse);
            return configuredHouse;
        } catch (ConfigurationException e) {
            throw new SimulationException(e.getMessage());
        }
    }

    /**
     * Create a configured house based on the loaded configuration data.
     *
     * @return Configured house
     * @throws ConfigurationException If there is an issue with the configuration data.
     */
    private House createConfiguredHouse() throws ConfigurationException {
        ManualRepo manualRepo = new ManualRepoProxy(_offlineManualDatabase);
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
                configuredRoom.getAllPeople().forEach(person -> {
                    person.setRoom(configuredRoom);
                    person.setManualRepo(manualRepo);
                });
                configuredRoom.getAllPets().forEach(pet -> pet.setRoom(configuredRoom));

                // add device systems to the room
                for(DeviceSystem deviceSystem : deviceSystems) {
                    DeviceSystem newDeviceSystem = this.createSystemByType(
                            room.getRoomID(),
                            deviceSystem.getDeviceSystemID(),
                            deviceSystemNameByIdMap.get(deviceSystem.getDeviceSystemID()),
                            configuredRoom,
                            _internalTemperature,
                            _externalTemperature
                    );
                    configuredRoom.addDeviceSystem(newDeviceSystem);
                }
                // add configured room to the floor map
                configuredRooms.computeIfAbsent(floor.getFloorID(), k -> new ArrayList<>())
                        .add(configuredRoom);
            }
            configuredFloors.add(new Floor(floor.getFloorID(), floor.getFloorName(), floor.getFloorLevel(), configuredRooms.get(floor.getFloorID())));
        }
        log.info("[CONFIG][HOUSE] Configured house successfully created.");
        return new House(_house.getHouseID(), _house.getHouseNumber(), _house.getAddress(), _house.getInternalTemperature(), _house.getExternalTemperature(), configuredFloors);
    }

    /**
     * Load configuration data into internal maps.
     */
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

                /* CONFIGURATION OF FLOORS */
                Set<Integer> floorIDSet = new HashSet<>();
                Set<String> floorNameSet = new HashSet<>();
                Set<Integer> floorLevelSet = new HashSet<>();
                // Create floors from config and add them to the config map
                JsonNode floors = jsonObject.get("Floors");
                for (JsonNode floor : floors) {

                    // check if this floorID is unique
                    int floorID = floor.get("floorID").asInt();
                    if(floorIDSet.contains(floorID))
                        throw new ConfigurationException("[FLOOR] Floor with this ID already exists : " + floorID);
                    floorIDSet.add(floorID);

                    // check if floor name is unique
                    String floorName = floor.get("floorName").asText();
                    if(floorNameSet.contains(floorName))
                        throw new ConfigurationException("[FLOOR] Floor with this name already exists : " + floorName);
                    floorNameSet.add(floorName);

                    // check if floor level is unique
                    int floorLevel = floor.get("floorLevel").asInt();
                    if(floorLevelSet.contains(floorLevel))
                        throw new ConfigurationException("[FLOOR] Floor with this level already exists : " + floorLevel);
                    floorLevelSet.add(floorLevel);

                    // create new floor and add it to the floor list
                    Floor newFloor = new Floor(floorID, floorName, floorLevel, null);
                    this._floorList.add(newFloor);
                }

                log.info("[CONFIG][PARSING] Floors successfully initialized.");

                /* CONFIGURATION OF GARAGE */
                // Create garage object
                JsonNode garage = jsonObject.get("Garage");
                int garageID = garage.get("roomID").asInt();
                if (garageID != 0)
                    throw new ConfigurationException("Garage ID must be 0.");
                String garageName = garage.get("garageName").asText();

                // check if sport equipment count is positive
                int sportEquipmentCountBIKE = garage.get("sportEquipmentCountBIKE").asInt();
                if( sportEquipmentCountBIKE < 0)
                    throw new ConfigurationException("Sport equipment count must be positive.");

                // check if sport equipment count is positive
                int sportEquipmentCountSKATES = garage.get("sportEquipmentCountSKATES").asInt();
                if( sportEquipmentCountSKATES < 0)
                    throw new ConfigurationException("Sport equipment count must be positive.");

                // check if sport equipment count is positive
                int sportEquipmentCountSKIS = garage.get("sportEquipmentCountSKIS").asInt();
                if( sportEquipmentCountSKIS < 0)
                    throw new ConfigurationException("Sport equipment count must be positive.");

                List<SportEquipment> sportEquipmentList = this.createSportEquipmentList(sportEquipmentCountBIKE, sportEquipmentCountSKATES, sportEquipmentCountSKIS);
                Garage garageObj = Garage.garageBuilder()
                        .setRoomID(garageID)
                        .setRoomName(garageName)
                        .addSportEquipment(sportEquipmentList)
                        .build();

                int lowestFloorID = this._floorList.stream().mapToInt(Floor::getFloorID).min().orElseThrow(() -> new ConfigurationException("No floors found."));
                this._roomMap.computeIfAbsent(lowestFloorID, k -> new ArrayList<>())
                        .add(garageObj);

                log.info("[CONFIG][PARSING] Garage successfully initialized.");

                /* CONFIGURATION OF ROOMS */
                Set<Integer> roomIDSet = new HashSet<>();
                Set<String> roomNameSet = new HashSet<>();
                // Create rooms from config and add them to the config map
                JsonNode rooms = jsonObject.get("Rooms");
                for (JsonNode room : rooms) {

                    // check if roomID is unique and is not 0 (reserved for garage)
                    int roomID = room.get("roomID").asInt();
                    if(roomID == 0)
                        throw new ConfigurationException("[ROOM] RoomID 0 is reserved for garage.");
                    if(roomIDSet.contains(roomID))
                        throw new ConfigurationException("[ROOM] Room with this ID already exists : " + roomID);
                    roomIDSet.add(roomID);

                    // check if room name is unique
                    String roomName = room.get("roomName").asText();
                    if(roomNameSet.contains(roomName))
                        throw new ConfigurationException("[ROOM] Room with this name already exists : " + roomName);
                    roomNameSet.add(roomName);

                    // check if floor of this id exists
                    int floorID = room.get("floorID").asInt();
                    if(this._floorList.stream().noneMatch(floor -> floor.getFloorID() == floorID))
                        throw new ConfigurationException("[ROOM] Floor with ID " + floorID + " does not exist.");

                    // create temp room and add it to the room map
                    Room tmpRoom = Room.roomBuilder()
                            .setRoomID(roomID)
                            .setRoomName(roomName)
                            .build();
                    this._roomMap.computeIfAbsent(floorID, k -> new ArrayList<>())
                            .add(tmpRoom);
                }

                log.info("[CONFIG][PARSING] Rooms successfully initialized.");

                /* CONFIGURATION OF PETS */
                Set<Integer> petIDSet = new HashSet<>();
                Set<String> petNameSet = new HashSet<>();
                // Create pets from config and add them to the pet config map
                JsonNode pets = jsonObject.get("Pets");
                for (JsonNode pet : pets) {

                    // check if petID is unique
                    int petID = pet.get("petID").asInt();
                    if(petIDSet.contains(petID))
                        throw new ConfigurationException("[PET] Pet with this ID already exists : " + petID);
                    petIDSet.add(petID);

                    // check if pet name is unique
                    String petName = pet.get("petName").asText();
                    if(petNameSet.contains(petName))
                        throw new ConfigurationException("[PET] Pet with this name already exists : " + petName);
                    petNameSet.add(petName);

                    // check if room of this roomID exists
                    int petRoomID = pet.get("roomID").asInt();
                    if(!roomIDSet.contains(petRoomID))
                        throw new ConfigurationException("[PET] Room with ID " + petRoomID + " does not exist.");

                    // create tmp pet and add it to the pet config map
                    Pet tmpPet = new Pet(petID, petName, null);
                    this._petConfigMap.computeIfAbsent(petRoomID, k -> new ArrayList<>())
                            .add(tmpPet);
                }

                log.info("[CONFIG][PARSING] Pets successfully initialized.");

                /* CONFIGURATION OF HUMANS */
                Set<Integer> humanIDSet = new HashSet<>();
                Set<String> humanNameSet = new HashSet<>();
                // Create humans from config and add them to the human config map
                JsonNode humans = jsonObject.get("Humans");
                for (JsonNode human : humans) {

                    // check if personID is unique
                    int personID = human.get("personID").asInt();
                    if(humanIDSet.contains(personID))
                        throw new ConfigurationException("[HUMAN] Human with this ID already exists : " + personID);
                    humanIDSet.add(personID);

                    // check if person name is unique
                    String personName = human.get("personName").asText();
                    if(humanNameSet.contains(personName))
                        throw new ConfigurationException("[HUMAN] Human with this name already exists : " + personName);
                    humanNameSet.add(personName);

                    // check if room of this roomID exists
                    int personRoomID = human.get("roomID").asInt();
                    if(!roomIDSet.contains(personRoomID))
                        throw new ConfigurationException("[HUMAN] Room with ID " + personRoomID + " does not exist. " + personID);

                    // create tmp human and add it to the human config map
                    Human tmpHuman = new Human(personID, personName, null, null);
                    this._humanConfigMap.computeIfAbsent(personRoomID, k -> new ArrayList<>())
                            .add(tmpHuman);
                }

                log.info("[CONFIG][PARSING] Humans successfully initialized.");

                /* CONFIGURATION OF DEVICE SYSTEMS */
                // Create device systems from config and add them to the device system config map
                JsonNode deviceSystems = jsonObject.get("DeviceSystems");
                for (JsonNode deviceSystem : deviceSystems) {

                    // check if deviceSystemID is unique
                    int deviceSystemID = deviceSystem.get("systemID").asInt();
                    if(this.deviceSystemNameByIdMap.containsKey(deviceSystemID))
                        throw new ConfigurationException("[DEVICESYSTEM] Device system with this ID already exists : " + deviceSystemID);

                    String deviceSystemName = deviceSystem.get("systemName").asText();

                    // check if room of this roomID exists
                    int deviceSystemRoomID = deviceSystem.get("roomID").asInt();
                    if(deviceSystemRoomID != 0 && !roomIDSet.contains(deviceSystemRoomID))
                        throw new ConfigurationException("[DEVICESYSTEM] Room with ID " + deviceSystemRoomID + " does not exist.");

                    // Check if the room is found, then add the device system to the map
                    this._deviceSystemConfigMap.computeIfAbsent(deviceSystemRoomID, k -> new ArrayList<>())
                            .add(this.createSystemByType(deviceSystemRoomID, deviceSystemID, deviceSystemName, null, _internalTemperature, _externalTemperature));
                    deviceSystemNameByIdMap.put(deviceSystemID, deviceSystemName);
                }

                log.info("[CONFIG][PARSING] Device systems successfully initialized.");
                log.info("[CONFIG][PARSING] Configuration successfully loaded.");

                isLoaded = true;
            } catch (ConfigurationException e) {
                log.error(e.getMessage());
                log.error("Configuration loading failed.");
                isLoaded = false;
                return;
            }
        } catch (Exception e) {
            log.error("Error while parsing JSON file.");
            log.error("Configuration loading failed.");
            isLoaded = false;
        }
    }

    /**
     * Logs the configuration.
     */
    private void logConfiguration() {
        log.info("Configuration:");
        log.info("House: " + _house.toString());
        log.info("Floors: " + _floorList.toString());
        log.info("Rooms: " + _roomMap.toString());
        log.info("Pets: " + _petConfigMap.toString());
        log.info("Humans: " + _humanConfigMap.toString());
        log.info("Device systems: " + _deviceSystemConfigMap.toString());
    }

    /**
     * Logs the configured house.
     * @param house House to log
     */
    private void logConfiguredHouse(House house) {
        log.info("Configured house: " + house.toString());
        house.getFloors().forEach(floor -> log.info("Floor: " + floor.toString()));
        house.getFloors().forEach(floor -> floor.getRooms().forEach(room -> log.info("Room: " + room.toString())));
        house.getFloors().forEach(floor -> floor.getRooms().forEach(room -> room.getAllPeople().forEach(person -> log.info("Person: " + person.toString()))));
        house.getFloors().forEach(floor -> floor.getRooms().forEach(room -> room.getAllPets().forEach(pet -> log.info("Pet: " + pet.toString()))));
        house.getFloors().forEach(floor -> floor.getRooms().forEach(room -> room.getAllDeviceSystems().forEach(deviceSystem -> log.info("Device system: " + deviceSystem.toString()))));
    }

    /**
     * Creates a list of sport equipment. {@link SportEquipment}
     * @param sportEquipmentCountBIKE number of bikes
     * @param sportEquipmentCountSKATES number of skates
     * @param sportEquipmentCountSKIS number of skis
     * @return List of sport equipment {@see SportEquipment}
     */
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

    /**
     * Creates a device system by its type.
     * @param roomID roomId (used to check if were creating a gate control system in the garage and nowhere else)
     * @param deviceSystemID deviceSystemId
     * @param deviceSystemName deviceSystemName
     * @param room room (used to create a lighting system)
     * @param internalTemp internalTemp
     * @param externalTemp externalTemp
     * @return DeviceSystem
     * @throws ConfigurationException if the device system type is not recognized
     */
     private DeviceSystem createSystemByType(int roomID, int deviceSystemID, String deviceSystemName, Room room, Temperature internalTemp, Temperature externalTemp) throws ConfigurationException {
         if( roomID != 0 && deviceSystemName.equals("GateControlSystem"))
             throw new ConfigurationException("GateControlSystem can only be in the garage.");

         DeviceSystemFactory factory = new DeviceSystemFactory();
         return switch (deviceSystemName) {
             case "FridgeSystem" -> factory.createFridgeSystem(deviceSystemID);
             case "GateControlSystem" -> factory.createGateControlSystem(deviceSystemID);
             case "HVACSystem" -> factory.createHVACSystem(deviceSystemID, internalTemp, externalTemp);
             case "LightingSystem" -> factory.createLightingSystem(deviceSystemID,room);
             case "SecuritySystem" -> factory.createSecuritySystem(deviceSystemID);
             case "TVSystem" -> factory.createEntertainmentSystem(deviceSystemID);
             default -> throw new ConfigurationException("Device system type not recognized.");
         };
     }
}
