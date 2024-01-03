package cz.cvut.fel.omo.semestral.entity.beings;


import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Appliance;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;

public class Human extends Being {
    // Additional human-specific attributes and methods...

    public Human(String name, int age) {
        super(name, age);
        // Initialize human-specific attributes
    }

    protected void sendUserInput(DeviceSystem deviceSystem, UserInputType inputType, Object inputValue) {
        deviceSystem.getUserInputSensor().detectInput(inputType, inputValue);
    }

    public void feed(Human human) {
        System.out.println(this.name + " feeds " + human.getName());
        human.eat();
    }

    public void feed(Pet pet) {
        System.out.println(this.name + " feeds " + pet.getName());
        pet.eat();
    }

    public void turnOnSystem(DeviceSystem deviceSystem) {
        System.out.println(this.name + " turns on system " + deviceSystem.getClass().getName() + " with appliance: " + deviceSystem.getAppliance().getSerialNumber());
        deviceSystem.turnOn();
    }

    public void turnOffSystem(DeviceSystem deviceSystem) {
        System.out.println(this.name + " turns off system " + deviceSystem.getClass().getName() + " with appliance: " + deviceSystem.getAppliance().getSerialNumber());
        deviceSystem.turnOff();
    }
}
