package cz.cvut.fel.omo.semestral.reporting;

import cz.cvut.fel.omo.semestral.entity.devices.appliances.Appliance;
import cz.cvut.fel.omo.semestral.simulation.HouseFacade;
import cz.cvut.fel.omo.semestral.simulation.SimulationConfig;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
        return reportVisitor.createReport(houseFacade);

    }

    public void generateReportAndSaveToFile(ReportType reportType, HouseFacade houseFacade, String directoryPath) {
        Report report = generateReport(reportType, houseFacade);
        saveReportToFile(report, directoryPath, reportType);
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

    private void saveReportToFile(Report report, String directoryPath, ReportType reportType) {
        Path path = Paths.get(directoryPath, "Report_" + reportType + "_" + report.getReportId() + ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
            writer.write(report.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
