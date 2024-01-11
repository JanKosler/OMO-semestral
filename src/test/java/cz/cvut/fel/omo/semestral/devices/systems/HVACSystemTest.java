package cz.cvut.fel.omo.semestral.devices.systems;

import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import cz.cvut.fel.omo.semestral.common.enums.Temperature;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.HVAC;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.TemperatureController;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.TemperatureSensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import cz.cvut.fel.omo.semestral.entity.systems.HVACSystem;

import java.util.UUID;

public class HVACSystemTest {

    private HVACSystem hvacSystem;
    private HVAC hvac;
    private TemperatureController controller;
    private TemperatureSensor internalSensor, externalSensor;
    private UserInputSensor userInputSensor;
    private Temperature internalTemperature, externalTemperature;

    @Before
    public void setUp() {
        internalTemperature = new Temperature(22.0); // example initial temperature
        externalTemperature = new Temperature(15.0); // example initial temperature
        hvac = new HVAC(UUID.randomUUID(), internalTemperature);
        internalSensor = new TemperatureSensor(internalTemperature);
        externalSensor = new TemperatureSensor(externalTemperature);
        userInputSensor = new UserInputSensor();
        controller = new TemperatureController(internalSensor, externalSensor, hvac, userInputSensor);
        hvacSystem = new HVACSystem(hvac, controller, internalSensor, externalSensor, userInputSensor);
    }

    @Test
    public void testTemperatureResponse() {
        // Simulate a temperature change
        internalTemperature.setTemperature(30.0); // Set a new external temperature
        hvacSystem.onTick(); // Process the temperature change

        // Check if the HVAC state changes as expected
        // Assuming the HVAC should switch to cooling mode in this case
        assertEquals("VentilationState", hvac.getCurrentState().getClass().getSimpleName());
    }

    @Test
    public void testUserInputHandling() {
        // Simulate user setting a new target temperature
        double newTargetTemp = 10.0;
        userInputSensor.detectInput(UserInputType.HVAC_TEMPERATURE, newTargetTemp);
        hvacSystem.onTick(); // Process the user input

        assertEquals("CoolingState", hvac.getCurrentState().getClass().getSimpleName());
    }

    @Test
    public void testSystemBehaviorInExtremeTemperatures() {
        // Simulate extreme temperatures
        internalTemperature.setTemperature(40.0);
        externalTemperature.setTemperature(35.0);
        hvacSystem.onTick();

        // Assuming the system should be in cooling mode under these conditions
        assertEquals("VentilationState", hvac.getCurrentState().getClass().getSimpleName());
    }


    @Test
    public void testPowerConsumptionAndWearUpdates() {
        double initialWear = hvac.getTotalWear();
        double initialPowerConsumption = hvac.getTotalPowerConsumption();

        hvacSystem.onTick();

        assertTrue(hvac.getTotalWear() > initialWear);
        assertTrue(hvac.getTotalPowerConsumption() > initialPowerConsumption);
    }

    @Test
    public void testInternalTemperatureChangeOverMultipleTicks() {
        // Initial setup for the test
        double initialInternalTemp = 22.0;
        internalTemperature.setTemperature(initialInternalTemp);

        // Simulate user setting the HVAC to heating mode
        userInputSensor.detectInput(UserInputType.HVAC_TEMPERATURE, 25.0); // Target temperature

        // Simulate multiple ticks
        int numberOfTicks = 6; // Example number of ticks
        for (int i = 0; i < numberOfTicks; i++) {
            hvacSystem.onTick();
        }

        userInputSensor.detectInput(UserInputType.HVAC_TEMPERATURE, 20.0);

        for (int j = 0; j < numberOfTicks; j++) {
            hvacSystem.onTick();
        }

        externalTemperature.setTemperature(25.0);

        for (int k = 0; k < 10; k++) {
            hvacSystem.onTick();
        }

        System.out.println("Total consumption: " + hvac.getTotalPowerConsumption());

        // Additionally, you can check if the temperature is approaching the target temperature
        // This assertion depends on the rate at which your HVAC system changes the temperature
        assertEquals(20.0, internalTemperature.getTemperature(), 0.0);
    }
}
