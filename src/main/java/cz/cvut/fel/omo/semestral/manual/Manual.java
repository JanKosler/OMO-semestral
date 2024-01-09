package cz.cvut.fel.omo.semestral.manual;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

/**
 * Represents Manual for a device.
 */
@Getter
@AllArgsConstructor
public class Manual {
    /**
     * UUID of the manual.
     */
    private UUID manualId;
    /**
     * Name of the device.
     */
    private String deviceName;
    /**
     * Version of the manual.
     */
    private String version;
    /**
     * Content of the manual.
     */
    private String content;
}
