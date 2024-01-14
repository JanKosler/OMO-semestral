package cz.cvut.fel.omo.semestral.entity.systems;

import cz.cvut.fel.omo.semestral.entity.devices.appliances.*;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.*;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.MotionSensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.SecuritySensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.TemperatureSensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import cz.cvut.fel.omo.semestral.entity.livingSpace.House;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Factory for creating various Device Systems within the smart home.
 * This factory provides methods to create systems like FridgeSystem, SecuritySystem,
 * LightingSystem, HVACSystem, and TVSystem with all their necessary components.
 */
public class DeviceSystemFactory {

    /**
     * Creates and configures a FridgeSystem.
     *
     * @return The assembled FridgeSystem.
     */
    public FridgeSystem createFridgeSystem(int deviceSystemID,Room room) {
        // Create the components of the FridgeSystem
        Fridge fridge = new Fridge(generateUUID());
        UserInputSensor userInputSensor = new UserInputSensor();
        FridgeController controller = new FridgeController(fridge, userInputSensor);

        return new FridgeSystem(deviceSystemID,fridge, controller, userInputSensor);
    }

    public GateControlSystem createGateControlSystem(int deviceSystemID, Room room) {
        // Create the components of the GateControlSystem
        Gate gate = new Gate(generateUUID());
        UserInputSensor userInputSensor = new UserInputSensor();
        GateController controller = new GateController(gate, userInputSensor);

        return new GateControlSystem(deviceSystemID,gate, controller, userInputSensor);
    }

    /**
     * Creates and configures a SecuritySystem.
     *
     * @return The assembled SecuritySystem.
     */
    public SecuritySystem createSecuritySystem(int deviceSystemID, Room room) {
        // Create the components of the SecuritySystem
        Alarm alarm = new Alarm(generateUUID());
        SecuritySensor securitySensor = new SecuritySensor();
        UserInputSensor userInputSensor = new UserInputSensor();
        SecurityController controller = new SecurityController(securitySensor, userInputSensor, alarm);

        return new SecuritySystem(deviceSystemID,alarm, securitySensor, controller, userInputSensor);
    }

    /**
     * Creates and configures a LightingSystem with a specified number of lights.
     *
     * @return The assembled LightingSystem.
     */
    public LightingSystem createLightingSystem(int deviceSystemID, Room room) {
        List<Light> lights = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            lights.add(new Light(generateUUID()));
        }

        MotionSensor motionSensor = new MotionSensor(room);
        UserInputSensor userInputSensor = new UserInputSensor();
        LightController lightController = new LightController(lights, motionSensor, userInputSensor);

        return new LightingSystem(deviceSystemID,lights, lightController, motionSensor, userInputSensor);
    }

    public HVACSystem createHVACSystem(int deviceSystemID, House house, Room room) {
        HVAC hvac = new HVAC(generateUUID(), house.getInternalTemperature());
        TemperatureSensor internalSensor = new TemperatureSensor(house.getInternalTemperature());
        TemperatureSensor externalSensor = new TemperatureSensor(house.getExternalTemperature());
        UserInputSensor userInputSensor = new UserInputSensor();
        TemperatureController temperatureController = new TemperatureController(internalSensor, externalSensor, hvac, userInputSensor);

        return new HVACSystem(deviceSystemID,hvac, temperatureController, internalSensor, externalSensor, userInputSensor);
    }


    public TVSystem createEntertainmentSystem(int deviceSystemID, Room room) {
        TV tv = new TV(generateUUID());
        UserInputSensor userInputSensor = new UserInputSensor();
        TVController tvController = new TVController(tv, userInputSensor);

        return new TVSystem(deviceSystemID,tv, tvController, userInputSensor);
    }

    /**
     * Generates a unique UUID.
     *
     * @return A randomly generated UUID.
     */
    private UUID generateUUID() {
        return UUID.randomUUID();
    }


}
