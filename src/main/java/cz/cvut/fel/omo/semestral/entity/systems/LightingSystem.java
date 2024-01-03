package cz.cvut.fel.omo.semestral.entity.systems;

import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Light;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.LightController;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.MotionSensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import lombok.Getter;

import java.util.List;

@Getter
public class LightingSystem extends DeviceSystem {
        public final List<Light> lights;
        public final LightController controller;
        public final MotionSensor motionSensor;
        public final UserInputSensor userInputSensor;
        private final List<UserInputType> allowedUserInputTypes = List.of(UserInputType.LIGHT_SWITCH);

        public LightingSystem(List<Light> lights, LightController controller, MotionSensor motionSensor, UserInputSensor userInputSensor) {
            this.lights = lights;
            this.controller = controller;
            this.motionSensor = motionSensor;
            this.userInputSensor = userInputSensor;
        }

        @Override
        public Light getAppliance() {
            return lights.get(0);
        }

    @Override
    public UserInputSensor getUserInputSensor() {
        return userInputSensor;
    }

        @Override
        public void turnOn() {
            userInputSensor.turnOn();
            controller.turnOn();
            lights.forEach(Light::turnOn);
        }

        @Override
        public void turnOff() {
            userInputSensor.turnOff();
            controller.turnOff();
            lights.forEach(Light::turnOff);
        }
}
