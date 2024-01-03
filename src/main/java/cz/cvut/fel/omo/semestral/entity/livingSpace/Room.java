package cz.cvut.fel.omo.semestral.entity.livingSpace;

import cz.cvut.fel.omo.semestral.entity.beings.Being;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public abstract class Room implements ILivingSpace {
    private List<IDevice> deviceList;

    private List<Being> inhabitants;

    @Override
    public List<IDevice> getAllDevices() {
        return deviceList;
    }

    public List<Being> getAllPeople() {
        return inhabitants;
    }
}
