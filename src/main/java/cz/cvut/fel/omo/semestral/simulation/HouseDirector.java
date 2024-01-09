package cz.cvut.fel.omo.semestral.simulation;

import cz.cvut.fel.omo.semestral.entity.livingSpace.RoomBuilder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HouseDirector {
    // TODO: Implement house factory
    // private HouseFactory houseFactory;
    private RoomBuilder roomBuilder;

    /**
     * Constructs house object from the configuration.
     */
    public void buildHouse() {

    }

    /**
     * Loads configuration from a json file in the ConfigsJSON folder.
     * @param filename Name of the file to load configuration from
     */
    public void loadConfiguration(String filename) {

    }
}
