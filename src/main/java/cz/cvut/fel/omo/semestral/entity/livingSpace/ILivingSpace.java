package cz.cvut.fel.omo.semestral.entity.livingSpace;

import cz.cvut.fel.omo.semestral.entity.beings.Being;
import cz.cvut.fel.omo.semestral.entity.beings.Human;
import cz.cvut.fel.omo.semestral.entity.beings.Pet;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;

import java.util.List;

/**
 * Interface for living spaces. Used for the composite pattern.
 * Implemented by {@link House}, {@link Floor} and {@link Room}.
 */
public interface ILivingSpace {
    /**
     * Gets all devices in the living space implementation.
     * 
     * @return List of devices.
     */
    List<IDevice> getAllDevices();

    /**
     * Gets all device systems in the living space implementation.
     * 
     * @return List of device systems.
     */
    List<DeviceSystem> getAllDeviceSystems();

    /**
     * Gets all people in the living space implementation.
     * 
     * @return List of people.
     */
    List<Human> getAllPeople();

    /**
     * Gets all pets in the living space implementation.
     * 
     * @return List of pets.
     */
    List<Pet> getAllPets();

    /**
     * Gets all beings in the living space implementation.
     * 
     * @return List of beings.
     */
    List<Being> getAllBeings();
}
