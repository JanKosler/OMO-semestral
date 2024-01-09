package cz.cvut.fel.omo.semestral.reporting;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

/**
 * Report is a class that holds information about the simulation.
 */
@Data
public class Report {
    /** Unique ID of the report */
    private UUID reportId;
    /** Content of the report */
    private String content;
    /** Timestamp of the report */
    private Instant timestamp;
}
