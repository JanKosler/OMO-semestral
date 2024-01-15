package cz.cvut.fel.omo.semestral.entity.devices;

/**
 * The {@code DeviceMalfunctionObserver} interface defines a method for
 * classes that want to observe and respond to device malfunctions in a
 * smart home system.
 */
public interface DeviceMalfunctionObserver {

    /**
     * Called when a device malfunctions. Implementing classes can use this
     * method to perform specific actions or handle the malfunction.
     *
     * @param device The malfunctioning device represented as an {@link IDevice}.
     */
    void onDeviceMalfunction(IDevice device);
}
