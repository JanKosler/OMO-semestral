package cz.cvut.fel.omo.semestral.reporting;

import cz.cvut.fel.omo.semestral.entity.devices.appliances.Appliance;
import cz.cvut.fel.omo.semestral.simulation.HouseFacade;
import cz.cvut.fel.omo.semestral.simulation.SimulationConfig;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class for generating reports
 */
public class ReportGenerator {
    /**
     * Handles the generation of reports and saving them to files.
     */
    public Report generateReport(ReportType reportType, HouseFacade houseFacade) {
        ReportVisitor reportVisitor = createReportVisitor(reportType);

        Report finalReport = reportVisitor.createReport(houseFacade);

        return finalReport;

    }
    private ReportVisitor createReportVisitor(ReportType reportType) {
        return switch (reportType) {
            case CONFIGURATION -> new HouseConfigurationReport();
            case CONSUMPTION -> new ConsumptionReport();
            case ACTIVITY_USAGE -> new ActivityAndUsageReport();
            case EVENT -> new EventReport();
            default -> throw new IllegalArgumentException("Unsupported report type");
        };

    }

}
