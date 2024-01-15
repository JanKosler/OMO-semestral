package cz.cvut.fel.omo.semestral.entity.actions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ActionRecord {

    private final Action action;
    private final int tick;

}
