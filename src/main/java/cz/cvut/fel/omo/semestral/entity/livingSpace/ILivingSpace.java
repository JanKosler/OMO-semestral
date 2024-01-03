package cz.cvut.fel.omo.semestral.entity.livingSpace;

import cz.cvut.fel.omo.semestral.entity.devices.IDevice;

import java.util.List;

public interface ILivingSpace {
    /**
     * this will get implemented by Floor, House, Room
     * TODO:
     *  getAllPeople()
     *  getAllDevices()
     */

    /**
     * Gets all devices in the living space implementation.
     * @return List of devices.
     */
    List<IDevice> getAllDevices();
}
