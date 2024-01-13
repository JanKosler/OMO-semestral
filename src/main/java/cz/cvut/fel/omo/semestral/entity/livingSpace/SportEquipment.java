package cz.cvut.fel.omo.semestral.entity.livingSpace;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SportEquipment {
    SportEquipmentType type;
    boolean isUsed;
}
