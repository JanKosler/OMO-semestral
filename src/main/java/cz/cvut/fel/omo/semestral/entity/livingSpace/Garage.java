package cz.cvut.fel.omo.semestral.entity.livingSpace;

import lombok.Getter;

import java.util.Map;

@Getter
public class Garage extends Room {
    // TODO: might want to use map -> if we want to have multiple of the same
    // equipment
    private final Map<SportEquipment, Integer> sportEquipment;

    public Garage(GarageBuilder builder) {
        super(builder.roomBuilder);
        this.sportEquipment = builder.sportEquipment;
    }

    public static GarageBuilder builder() {
        return new GarageBuilder();
    }
}
