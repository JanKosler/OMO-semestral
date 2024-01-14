package cz.cvut.fel.omo.semestral.devices.systems;

import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.TV;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.TVController;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import cz.cvut.fel.omo.semestral.entity.systems.TVSystem;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class TVSystemTest {

    private TVSystem tvSystem;
    private TV tv;
    private TVController controller;
    private UserInputSensor userInputSensor;


    @Before
    public void setUp() {
        tv = new TV(UUID.randomUUID());
        userInputSensor = new UserInputSensor(UUID.randomUUID());
        controller = new TVController(UUID.randomUUID(),tv, userInputSensor);
        tvSystem = new TVSystem(1,tv, controller, userInputSensor);
    }

    @Test
    public void testUserInputResponse(){
        userInputSensor.detectInput(UserInputType.TV_VOLUME, 20);
        userInputSensor.detectInput(UserInputType.TV_CHANNEL, 5);
        userInputSensor.detectInput(UserInputType.FRIDGE_TEMPERATURE, 5);
        tvSystem.onTick();
        assertEquals(10, tv.getVolumeLevel());
        assertEquals(5, tv.getCurrentChannel());
    }
}
