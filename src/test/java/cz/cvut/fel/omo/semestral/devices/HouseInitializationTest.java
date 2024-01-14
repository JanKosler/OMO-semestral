package cz.cvut.fel.omo.semestral.devices;

import cz.cvut.fel.omo.semestral.entity.livingSpace.Temperature;
import cz.cvut.fel.omo.semestral.entity.beings.Being;
import cz.cvut.fel.omo.semestral.entity.beings.Human;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Alarm;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Appliance;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Gate;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.Controller;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.SecurityController;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.SecuritySensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.Sensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import cz.cvut.fel.omo.semestral.entity.livingSpace.*;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;
import cz.cvut.fel.omo.semestral.entity.systems.SecuritySystem;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class HouseInitializationTest {
    Sensor securitySensor = new SecuritySensor(UUID.randomUUID());
    Appliance alarm = new Alarm(UUID.randomUUID());
    UserInputSensor userInputSensor = new UserInputSensor(UUID.randomUUID());
    Controller controller = new SecurityController(UUID.randomUUID(),(SecuritySensor) securitySensor, userInputSensor, (Alarm) alarm);
    DeviceSystem securitySystem = new SecuritySystem( 1,(Alarm) alarm,
                                        (SecuritySensor) securitySensor,
                                        (SecurityController) controller,
                                        userInputSensor );

    Appliance gate = new Gate(UUID.randomUUID());
    Being humanNamedPete = new Human(1,"Pete", null);
    SportEquipment bike = new SportEquipment(SportEquipmentType.BIKE,false);
    SportEquipment skis = new SportEquipment(SportEquipmentType.SKIS,false);

    @Test
    public void testRoomBuilder() {
        ILivingSpace room1 = Room.roomBuilder()
                .setRoomID(1)
                .setRoomName("Living room")
                .addDevice(securitySensor).addDevice(alarm).addDevice(gate)
                .addDeviceSystem(securitySystem)
                .addPerson(humanNamedPete)
                .build();

        Room room2 = new RoomBuilder()
                .setRoomID(2)
                .setRoomName("Living room 2")
                .addDevice(securitySensor).addDevice(alarm).addDevice(gate)
                .addDeviceSystem(securitySystem)
                .addPerson(humanNamedPete)
                .build();
    }
    @Test
    public void testGarageBuilder() {
        List<SportEquipment> sportEquipmentList = new LinkedList<>();
        sportEquipmentList.add(bike);
        sportEquipmentList.add(skis);

        Garage garage1 = Garage.garageBuilder()
                .setRoomID(3)
                .setRoomName("Garage")
                .addDevice(securitySensor).addDevice(alarm).addDevice(gate)
                .addDeviceSystem(securitySystem)
                .addPerson(humanNamedPete)
                .addSportEquipment(bike).addSportEquipment(skis)
                .addSportEquipment(sportEquipmentList)
                .build();

        Room garage2 = new GarageBuilder()
                .setRoomID(4)
                .setRoomName("Garage 2")
                .addDevice(securitySensor).addDevice(alarm).addDevice(gate)
                .addDeviceSystem(securitySystem)
                .addPerson(humanNamedPete)
                .addSportEquipment(bike).addSportEquipment(skis)
                .addSportEquipment(sportEquipmentList)
                .build();
    }

    @Test
    public void testFloorCreation() {
        List<Room> rooms = new LinkedList<>();
        rooms.add(Room.roomBuilder()
                .setRoomID(1)
                .setRoomName("Living room")
                .addDevice(securitySensor).addDevice(alarm).addDevice(gate)
                .addDeviceSystem(securitySystem)
                .addPerson(humanNamedPete)
                .build());
        rooms.add(Room.roomBuilder()
                .setRoomID(2)
                .setRoomName("Living room 2")
                .addDevice(securitySensor).addDevice(alarm).addDevice(gate)
                .addDeviceSystem(securitySystem)
                .addPerson(humanNamedPete)
                .build());
        rooms.add(Garage.garageBuilder()
                .setRoomID(3)
                .setRoomName("Garage")
                .addDevice(securitySensor).addDevice(alarm).addDevice(gate)
                .addDeviceSystem(securitySystem)
                .addPerson(humanNamedPete)
                .addSportEquipment(bike).addSportEquipment(skis)
                .build());
        rooms.add(new GarageBuilder()
                .setRoomID(4)
                .setRoomName("Garage 2")
                .addDevice(securitySensor).addDevice(alarm).addDevice(gate)
                .addDeviceSystem(securitySystem)
                .addPerson(humanNamedPete)
                .addSportEquipment(bike).addSportEquipment(skis)
                .build());

        Floor floor = new Floor(1, "Ground floor", 1, rooms);
    }

    @Test
    public void testHouseCreation() {
        List<Room> rooms = new LinkedList<>();
        rooms.add(Room.roomBuilder()
                .setRoomID(1)
                .setRoomName("Living room")
                .addDevice(securitySensor).addDevice(alarm).addDevice(gate)
                .addDeviceSystem(securitySystem)
                .addPerson(humanNamedPete)
                .build());
        rooms.add(Room.roomBuilder()
                .setRoomID(2)
                .setRoomName("Living room 2")
                .addDevice(securitySensor).addDevice(alarm).addDevice(gate)
                .addDeviceSystem(securitySystem)
                .addPerson(humanNamedPete)
                .build());
        rooms.add(Garage.garageBuilder()
                .setRoomID(3)
                .setRoomName("Garage")
                .addDevice(securitySensor).addDevice(alarm).addDevice(gate)
                .addDeviceSystem(securitySystem)
                .addPerson(humanNamedPete)
                .addSportEquipment(bike).addSportEquipment(skis)
                .build());
        rooms.add(new GarageBuilder()
                .setRoomID(4)
                .setRoomName("Garage 2")
                .addDevice(securitySensor).addDevice(alarm).addDevice(gate)
                .addDeviceSystem(securitySystem)
                .addPerson(humanNamedPete)
                .addSportEquipment(bike).addSportEquipment(skis)
                .build());

        Floor floor = new Floor(1, "Ground floor", 1, rooms);
        Floor floor2 = new Floor(2, "First floor", 2, rooms);

        List<Floor> floors = new ArrayList<>();
        floors.add(floor);
        floors.add(floor2);

        House house = new House(1,13,"Wonder street, state", new Temperature(20), new Temperature(10), floors);

    }
}
