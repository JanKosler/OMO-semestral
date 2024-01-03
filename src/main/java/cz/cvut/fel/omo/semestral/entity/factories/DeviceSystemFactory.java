package cz.cvut.fel.omo.semestral.entity.factories;

import cz.cvut.fel.omo.semestral.entity.devices.appliances.*;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.*;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.MotionSensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.SecuritySensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.TemperatureSensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import cz.cvut.fel.omo.semestral.entity.systems.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeviceSystemFactory {

    public FridgeSystem createFridgeSystem() {
        // Create the components of the FridgeSystem
        Fridge fridge = new Fridge(generateUUID());
        UserInputSensor userInputSensor = new UserInputSensor();
        FridgeController controller = new FridgeController(fridge, userInputSensor);

        return new FridgeSystem(fridge, controller, userInputSensor);
    }

    public SecuritySystem createSecuritySystem() {
        // Create the components of the SecuritySystem
        Alarm alarm = new Alarm(generateUUID());
        SecuritySensor securitySensor = new SecuritySensor();
        UserInputSensor userInputSensor = new UserInputSensor();
        SecurityController controller = new SecurityController(securitySensor, userInputSensor, alarm);

        return new SecuritySystem(alarm, securitySensor, controller, userInputSensor);
    }

    public LightingSystem createLightingSystem(int numberOfLights) {
        List<Light> lights = new ArrayList<>();
        for (int i = 0; i < numberOfLights; i++) {
            lights.add(new Light(generateUUID()));
        }

        MotionSensor motionSensor = new MotionSensor();
        UserInputSensor userInputSensor = new UserInputSensor();
        LightController lightController = new LightController(lights, motionSensor, userInputSensor);

        return new LightingSystem(lights, lightController, motionSensor, userInputSensor);
    }

    public class HVACSystemFactory {
        public HVACSystem createHVACSystem() {
            HVAC hvac = new HVAC(generateUUID());
            TemperatureSensor internalSensor = new TemperatureSensor();
            TemperatureSensor externalSensor = new TemperatureSensor();
            UserInputSensor userInputSensor = new UserInputSensor();
            TemperatureController temperatureController = new TemperatureController(internalSensor, externalSensor, hvac, userInputSensor);

            return new HVACSystem(hvac, temperatureController, internalSensor, externalSensor, userInputSensor);
        }
    }

    public class SecuritySystemFactory {
        public SecuritySystem createSecuritySystem() {
            Alarm alarm = new Alarm(generateUUID());
            SecuritySensor securitySensor = new SecuritySensor();
            UserInputSensor userInputSensor = new UserInputSensor();
            SecurityController securityController = new SecurityController(securitySensor, userInputSensor, alarm);

            return new SecuritySystem(alarm, securitySensor, securityController, userInputSensor);
        }
    }

    public class TVSystemFactory {
        public TVSystem createEntertainmentSystem() {
            TV tv = new TV(generateUUID());
            UserInputSensor userInputSensor = new UserInputSensor();
            TVController tvController = new TVController(tv, userInputSensor);

            return new TVSystem(tv, tvController, userInputSensor);
        }
    }

    private UUID generateUUID() {
        return UUID.randomUUID();
    }


}
