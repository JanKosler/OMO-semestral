package cz.cvut.fel.omo.semestral.entity.beings;

import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Being {
    protected String name;
    protected int age;
    private Room room;
    // Add other common attributes and methods relevant to all beings...

    public Being(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /*
     TODO: Perform change on Room
     */
    public void goTo(Room room){
        if (this.room == room) {
            return;
        }

        this.room = room;
    }

    public void eat() {
        System.out.println(this.name + " eats");
    }

}
