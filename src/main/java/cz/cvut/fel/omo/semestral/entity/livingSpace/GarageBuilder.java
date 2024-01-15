package cz.cvut.fel.omo.semestral.entity.livingSpace;

import cz.cvut.fel.omo.semestral.entity.beings.Being;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GarageBuilder implements IRoomBuilder<Garage> {
    List<SportEquipment> sportEquipmentList;
    RoomBuilder roomBuilder;
    public GarageBuilder() {
        this.roomBuilder = new RoomBuilder();
        sportEquipmentList = new ArrayList<>();
    }
    public GarageBuilder setRoomID(int roomID) {
        this.roomBuilder.setRoomID(roomID);
        return this;
    }
    public GarageBuilder setRoomName(String roomName) {
        this.roomBuilder.setRoomName(roomName);
        return this;
    }
    public GarageBuilder addDevice(IDevice device) {
        this.roomBuilder.addDevice(device);
        return this;
    }
    public GarageBuilder addDeviceSystem(DeviceSystem deviceSystem) {
        this.roomBuilder.addDeviceSystem(deviceSystem);
        return this;
    }

    public GarageBuilder addPerson(Being person) {
        this.roomBuilder.addPerson(person);
        return this;
    }

    public GarageBuilder addSportEquipment(List<SportEquipment> sportEquipment) {
        for (SportEquipment equipment : sportEquipment)
            this.addSportEquipment(equipment);
        return this;
    }

    public GarageBuilder addSportEquipment(SportEquipment sportEquipment) {
        this.sportEquipmentList.add(sportEquipment);
        return this;
    }

    @Override
    public Garage build() {
        return new Garage(this);
    }
}
