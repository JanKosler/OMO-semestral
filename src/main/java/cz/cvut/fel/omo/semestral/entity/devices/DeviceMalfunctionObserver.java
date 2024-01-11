package cz.cvut.fel.omo.semestral.entity.devices;

public interface DeviceMalfunctionObserver {
    void onDeviceMalfunction(IDevice device);
}
