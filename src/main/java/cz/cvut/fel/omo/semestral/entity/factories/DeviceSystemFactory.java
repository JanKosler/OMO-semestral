package cz.cvut.fel.omo.semestral.entity.factories;

import cz.cvut.fel.omo.semestral.entity.devices.appliances.*;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.*;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.MotionSensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.SecuritySensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.TemperatureSensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import cz.cvut.fel.omo.semestral.entity.livingSpace.House;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
import cz.cvut.fel.omo.semestral.entity.systems.*;

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
    public FridgeSystem createFridgeSystem(Room room) {
        // Create the components of the FridgeSystem
        Fridge fridge = new Fridge(generateUUID());
        UserInputSensor userInputSensor = new UserInputSensor();
        FridgeController controller = new FridgeController(fridge, userInputSensor);

        return new FridgeSystem(fridge, controller, userInputSensor);
    }

    /**
     * Creates and configures a SecuritySystem.
     *
     * @return The assembled SecuritySystem.
     */
    public SecuritySystem createSecuritySystem(Room room) {
        // Create the components of the SecuritySystem
        Alarm alarm = new Alarm(generateUUID());
        SecuritySensor securitySensor = new SecuritySensor();
        UserInputSensor userInputSensor = new UserInputSensor();
        SecurityController controller = new SecurityController(securitySensor, userInputSensor, alarm);

        return new SecuritySystem(alarm, securitySensor, controller, userInputSensor);
    }

    /**
     * Creates and configures a LightingSystem with a specified number of lights.
     *
     * @return The assembled LightingSystem.
     */
    public LightingSystem createLightingSystem(Room room) {
        List<Light> lights = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            lights.add(new Light(generateUUID()));
        }

        MotionSensor motionSensor = new MotionSensor(room);
        UserInputSensor userInputSensor = new UserInputSensor();
        LightController lightController = new LightController(lights, motionSensor, userInputSensor);

        return new LightingSystem(lights, lightController, motionSensor, userInputSensor);
    }

    /**
     * Creates and configures an HVACSystem.
     *
     * @return The assembled HVACSystem.
     */
    public class HVACSystemFactory {
        public HVACSystem createHVACSystem(House house, Room room) {
            HVAC hvac = new HVAC(generateUUID(), house.getInternalTemperature());
            TemperatureSensor internalSensor = new TemperatureSensor(house.getInternalTemperature());
            TemperatureSensor externalSensor = new TemperatureSensor(house.getExternalTemperature());
            UserInputSensor userInputSensor = new UserInputSensor();
            TemperatureController temperatureController = new TemperatureController(internalSensor, externalSensor, hvac, userInputSensor);

            return new HVACSystem(hvac, temperatureController, internalSensor, externalSensor, userInputSensor);
        }
    }


    /**
     * Creates and configures a TVSystem.
     *
     * @return The assembled TVSystem.
     */
    public class TVSystemFactory {
        public TVSystem createEntertainmentSystem(Room room) {
            TV tv = new TV(generateUUID());
            UserInputSensor userInputSensor = new UserInputSensor();
            TVController tvController = new TVController(tv, userInputSensor);

            return new TVSystem(tv, tvController, userInputSensor);
        }
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
