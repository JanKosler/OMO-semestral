package cz.cvut.fel.omo.semestral.devices;

import cz.cvut.fel.omo.semestral.manual.Manual;
import cz.cvut.fel.omo.semestral.manual.ManualRepo;
import cz.cvut.fel.omo.semestral.manual.ManualRepoProxy;
import cz.cvut.fel.omo.semestral.manual.OfflineManualDatabase;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;

public class ManualProxyTest {
    /**
     * Tests that two manuals for the same device cannot be added
     */
    @Test
    public void testOfflineManualDatabase() {
        OfflineManualDatabase db = new OfflineManualDatabase();
        db.addManual(new Manual(UUID.randomUUID(),"TestDevice1", "TestContent1"));
        Optional<Manual> dbResult = db.requestManual("TestDevice1");
        assertTrue(dbResult.isPresent());

        // Test that another manual for the same device cannot be added
        db.addManual(new Manual(UUID.randomUUID(),"TestDevice1", "TestContent2"));
        Optional<Manual> dbResult2 = db.requestManual("TestDevice1");
        assertTrue(dbResult2.isPresent());


        assertEquals(dbResult.get().getContent(), dbResult2.get().getContent());
    }
    @Test
    public void testRequestManualProxy() {
        OfflineManualDatabase db = new OfflineManualDatabase();
        db.addManual(new Manual(UUID.randomUUID(),"TestDevice1", "TestContent1"));
        Optional<Manual> dbResult = db.requestManual("TestDevice1");
        assertTrue(dbResult.isPresent());

        ManualRepo proxy = new ManualRepoProxy(db);
        Optional<Manual> proxyResult = Optional.ofNullable(proxy.getManual("TestDevice1"));
        assertTrue(proxyResult.isPresent());

        assertEquals(dbResult.get(), proxyResult.get());
    }


}
