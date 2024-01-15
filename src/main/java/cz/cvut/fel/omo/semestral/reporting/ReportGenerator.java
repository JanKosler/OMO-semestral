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
     * Generates a report of the specified type for a given house facade.
     *
     * @param reportType   The type of the report to generate.
     * @param houseFacade  The house facade containing simulation data.
     * @return             The generated report.
     */
    public Report generateReport(ReportType reportType, HouseFacade houseFacade) {
        ReportVisitor reportVisitor = createReportVisitor(reportType);
        return reportVisitor.createReport(houseFacade);

    }

    /**
     * Generates a report of the specified type and saves it to a file in the specified directory.
     *
     * @param reportType    The type of the report to generate and save.
     * @param houseFacade   The house facade containing simulation data.
     * @param directoryPath The directory path where the report will be saved.
     */
    public void generateReportAndSaveToFile(ReportType reportType, HouseFacade houseFacade, String directoryPath) {
        Report report = generateReport(reportType, houseFacade);
        saveReportToFile(report, directoryPath, reportType);
    }

    /**
     * Creates a report visitor based on the specified report type.
     *
     * @param reportType The type of report to generate.
     * @return A report visitor instance corresponding to the specified report type.
     * @throws IllegalArgumentException If the report type is unsupported.
     */
    private ReportVisitor createReportVisitor(ReportType reportType) {
        return switch (reportType) {
            case CONFIGURATION -> new HouseConfigurationReport();
            case CONSUMPTION -> new ConsumptionReport();
            case ACTIVITY_USAGE -> new ActivityAndUsageReport();
            case EVENT -> new EventReport();
            default -> throw new IllegalArgumentException("Unsupported report type");
        };
    }

    /**
     * Saves a report to a file in the specified directory with a timestamp in the file name.
     *
     * @param report        The report to be saved.
     * @param directoryPath The directory path where the report will be saved.
     * @param reportType    The type of report being saved (used in the file name).
     */
    private void saveReportToFile(Report report, String directoryPath, ReportType reportType) {
        Path path = Paths.get(directoryPath, "Report_" + reportType + "_" + report.getReportId() + ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
            writer.write(report.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
