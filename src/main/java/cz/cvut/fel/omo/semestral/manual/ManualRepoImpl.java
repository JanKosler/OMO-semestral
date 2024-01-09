package cz.cvut.fel.omo.semestral.manual;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class ManualRepoImpl implements ManualRepo {
    private final OfflineManualDatabase database;
    private Manual manual;

    /**
     * Creates a new manual repo.
     * @param database database to load manual from
     * @param deviceName name of the device
     */
    public ManualRepoImpl(OfflineManualDatabase database, String deviceName) {
        this.database = database;
        loadManual(deviceName);
    }

    /**
     * Loads manual from database
     * @param deviceName name of the device
     */
    private void loadManual(String deviceName) {
        log.info("Loading manual from database");
        manual = database.requestManual(deviceName).orElse(null);
    }

    /**
     * Returns manual
     * @return manual
     */
    @Override
    public Manual getManual(String deviceName) {
        if (manual == null) {
            loadManual(deviceName);
        }
        return manual;
    }
}
