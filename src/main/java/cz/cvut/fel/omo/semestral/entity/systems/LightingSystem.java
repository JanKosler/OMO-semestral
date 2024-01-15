package cz.cvut.fel.omo.semestral.entity.systems;

import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Light;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.Controller;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.LightController;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.MotionSensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LightingSystem extends DeviceSystem {
        public final List<Light> lights;
        public final LightController controller;
        public final MotionSensor motionSensor;
        public final UserInputSensor userInputSensor;
        private final List<UserInputType> allowedUserInputTypes = List.of(UserInputType.LIGHT_SWITCH);
        private final int deviceSystemID;

        public LightingSystem(int deviceSystemID,List<Light> lights, LightController controller, MotionSensor motionSensor, UserInputSensor userInputSensor) {
            this.deviceSystemID = deviceSystemID;
            this.lights = lights;
            this.controller = controller;
            this.motionSensor = motionSensor;
            this.userInputSensor = userInputSensor;
            turnOn();
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
        public Controller getController() {
            return controller;
        }

        @Override
        public MotionSensor getMotionSensor() {
            return motionSensor;
        }

        @Override
        public double getTotalConsumption() {
            return lights.stream().mapToDouble(Light::getTotalPowerConsumption).sum() + controller.getTotalPowerConsumption() + userInputSensor.getTotalPowerConsumption();
        }

        @Override
        public List<IDevice> getDevices() {
            List<IDevice> devices = new ArrayList<>(lights);
            devices.add(controller);
            devices.add(userInputSensor);
            devices.add(motionSensor);
            return devices;
        }

        @Override
        public void onTick() {
            motionSensor.onTick();
            userInputSensor.onTick();
            controller.onTick();
            lights.forEach(Light::onTick);
        }

        @Override
        public String toString() {
            return "LightingSystem{" + "deviceSystemID=" + deviceSystemID + '}';
        }

    @Override
    public void setRoom(Room room) {
        motionSensor.setRoom(room);
    }
}
