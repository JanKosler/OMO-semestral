package cz.cvut.fel.omo.semestral.manual;

/**
 * Proxy for the manual repo.
 *
 * TODO : check if this is the right way to do it
 */
public class ManualRepoProxy implements ManualRepo {
    private ManualRepo manualRepo;
    private OfflineManualDatabase database;

    /**
     * Creates a new proxy for the manual repo.
     * @param deviceName name of the device
     */
    @Override
    public Manual getManual(String deviceName) {
        if (manualRepo == null) {
            manualRepo = new ManualRepoImpl(new OfflineManualDatabase());
        }
        return manualRepo.getManual(deviceName);
    }
}
