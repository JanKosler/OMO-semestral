package cz.cvut.fel.omo.semestral.manual;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;


@Slf4j
public class ManualRepoImpl implements ManualRepo {
    private final OfflineManualDatabase database;
    // TODO : cache all manuals, on get
    private Map<String, Manual> manual;

    /**
     * Creates a new manual repo.
     * @param database database to load manual from
     */
    public ManualRepoImpl(OfflineManualDatabase database) {
        manual = new java.util.HashMap<>();
        this.database = database;
    }

    /**
     * Loads manual from database
     * @param deviceName name of the device
     */
    private Manual loadManualFromDb(String deviceName) {
        log.info("Loading manual from database");
        return database.requestManual(deviceName).orElse(null);
    }

    /**
     * Returns manual
     * @return manual
     */
    @Override
    public Manual getManual(String deviceName) {
        if (!manual.containsKey(deviceName)) {
            manual.put(deviceName, loadManualFromDb(deviceName));
        }
        return manual.get(deviceName);
    }
}
