package cz.cvut.fel.omo.semestral.entity.livingSpace;

import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.beings.Being;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public abstract class Room implements ILivingSpace {
    private List<IDevice> deviceList;
    private List<DeviceSystem> deviceSystems;

    private List<Being> inhabitants;

    @Override
    public List<IDevice> getAllDevices() {
        return deviceList;
    }

    @Override
    public List<DeviceSystem> getAllDeviceSystems() {
        return deviceSystems;
    }

    public List<Being> getAllPeople() {
        return inhabitants;
    }

}
