package cz.cvut.fel.omo.semestral.entity.livingSpace;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents a sport equipment.
 */
@Data
@AllArgsConstructor
public class SportEquipment {
    /** Type of the sport equipment. {@see SportEquipmentType} */
    SportEquipmentType type;
    /** True if the sport equipment is used, false otherwise. */
    boolean isUsed;

    /**
     * Creates new sport equipment. By default, it is not used.
     * @param type Type of the sport equipment.
     */
    public SportEquipment(SportEquipmentType type) {
        this(type, false);
    }
}
