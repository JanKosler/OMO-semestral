package cz.cvut.fel.omo.semestral.entity.livingSpace;

import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * Represents a garage in a house.
 */
@Getter
public class Garage extends Room {
    /** List of sport equipment in the garage */
    private final List<SportEquipment> sportEquipmentList;

    /**
     * Creates a new garage.
     * @param builder Garage builder.
     */
    public Garage(GarageBuilder builder) {
        super(builder.roomBuilder);
        this.sportEquipmentList = builder.sportEquipmentList;
    }

    /**
     * Uses sport equipment. Checks if there is unused equipment of the given type and sets it to used.
     * @param type Type of the sport equipment.
     * @return True if can be used, false otherwise.
     */
    public boolean useSportEquipment(SportEquipmentType type) {
        return sportEquipmentList.stream().findFirst()
                .filter(equipment -> equipment.getType() == type && !equipment.isUsed())
                .map(equipment -> {
                    equipment.setUsed(true);
                    return true;
                }).orElse(false);
    }

    /**
     * Returns sport equipment. Checks if there is used equipment of the given type and sets it to unused.
     * @param type Type of the sport equipment.
     */
    public void returnSportEquipment(SportEquipmentType type) {
        sportEquipmentList.stream().findFirst()
                .filter(equipment -> equipment.getType() == type && equipment.isUsed())
                .ifPresent(equipment -> equipment.setUsed(false));
    }

    /**
     * Gets the builder for the garage.
     *
     * @return Garage builder.
     */
    public static GarageBuilder garageBuilder() {
        return new GarageBuilder();
    }
}
