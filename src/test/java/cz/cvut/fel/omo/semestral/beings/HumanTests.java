package cz.cvut.fel.omo.semestral.beings;

import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.actions.Action;
import cz.cvut.fel.omo.semestral.entity.beings.Human;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Gate;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.GateController;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;
import cz.cvut.fel.omo.semestral.entity.systems.GateControlSystem;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class HumanTests {

    Room room;
    GateControlSystem gateControlSystem;
    UserInputSensor userInputSensor;
    GateController gateController;
    Gate gate;

    Human human;

    @Before
    public void setUp() {


        gate = new Gate(UUID.randomUUID());
        userInputSensor = new UserInputSensor(UUID.randomUUID());
        gateController = new GateController(UUID.randomUUID(), gate, userInputSensor);
        gateControlSystem = new GateControlSystem(1, gate, gateController, userInputSensor);

        room = Room.roomBuilder().setRoomID(1).setRoomName("Pokoj1").addDeviceSystem(gateControlSystem).build();

        human = new Human(1, "Pavel", room);
        room.enterRoom(human);
    }

    @Test
    public void testHumanGateInteraction() {
        // Simulate human interaction with the gate
        human.addActionToPlan(new Action(UserInputType.GATE_CONTROL, null));

        human.onTick();
        gateControlSystem.onTick();

        assertTrue(gate.isOpen());
    }
}
