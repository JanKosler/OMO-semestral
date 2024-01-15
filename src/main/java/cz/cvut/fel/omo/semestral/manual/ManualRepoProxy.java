package cz.cvut.fel.omo.semestral.manual;


/**
 * Proxy for the manual repo.
 */
public class ManualRepoProxy implements ManualRepo {
    private ManualRepo manualRepo;
    private OfflineManualDatabase database;

    /**
     * Creates a new proxy for the manual repo.
     * @param database database to load manual from
     */
    public ManualRepoProxy(OfflineManualDatabase database) {
        this.database = database;
    }

    /**
     * Creates a new proxy for the manual repo.
     * @param deviceName name of the device
     */
    @Override
    public Manual getManual(String deviceName) {
        if (manualRepo == null) {
            manualRepo = new ManualRepoImpl(database);
        }
        return manualRepo.getManual(deviceName);
    }
}
