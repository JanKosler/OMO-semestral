package cz.cvut.fel.omo.semestral.manual;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents offline Manual database
 */
@Slf4j
public class OfflineManualDatabase implements ManualDatabase {
    Map<UUID, Manual> manuals;
    Map<String, UUID> manualNames;

    /**
     * Creates offline manual database and adds some manuals to it
     */
    public OfflineManualDatabase() {
        log.info("Creating offline manual database");

        manuals = new HashMap<>();
        manualNames = new HashMap<>();

        // Add sample entries
        this.addManual(new Manual(UUID.randomUUID(), "Fridge", "This is manual for fridge, manual version 1.0"));
        this.addManual(new Manual(UUID.randomUUID(), "HVAC", "This is manual for HVAC, manual version 1.2"));
        this.addManual(new Manual(UUID.randomUUID(), "TV", "This is manual for TV, manual version 2.3"));
        this.addManual(new Manual(UUID.randomUUID(), "MotionSensor", "This is manual for motion sensor, manual version 1.0"));
        this.addManual(new Manual(UUID.randomUUID(), "GateController", "This is manual for gate controller, manual version 0.9"));
        this.addManual(new Manual(UUID.randomUUID(), "LightController", "This is manual for light controller, manual version 0.9"));
    }

    /**
     * Adds manual to the database
     * @param manual manual to be added
     */
    public void addManual(Manual manual) {
        if( manualNames.containsKey(manual.getDeviceName()) ) {
            log.warn("Manual of this name already exists");
            return;
        }
        manuals.put(manual.getManualId(), manual);
        manualNames.put(manual.getDeviceName(), manual.getManualId());
    }

    /**
     * Requests manual from the database
     * @param deviceName name of the device
     * @return manual
     */
    public Optional<Manual> requestManual(String deviceName) {
        if( !manualNames.containsKey(deviceName) ) {
            log.warn("Manual with name {} not found", deviceName);
            return Optional.empty();
        }
        return this.requestManual(manualNames.get(deviceName));
    }

    /**
     * Requests manual from the database
     * @param id UUID of the manual
     * @return manual
     */
    public Optional<Manual> requestManual(UUID id) {
        if( !manuals.containsKey(id) ) {
            log.warn("Manual with id {} not found", id);
            return Optional.empty();
        }
        return Optional.ofNullable(manuals.get(id));
    }
}
