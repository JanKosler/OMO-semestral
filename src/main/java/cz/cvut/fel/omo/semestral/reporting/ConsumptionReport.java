package cz.cvut.fel.omo.semestral.reporting;

import cz.cvut.fel.omo.semestral.entity.beings.Human;
import cz.cvut.fel.omo.semestral.entity.beings.Pet;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.livingSpace.House;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;
import cz.cvut.fel.omo.semestral.simulation.HouseFacade;

import java.time.Instant;
import java.util.UUID;

/**
 * The {@code ConsumptionReport} class generates reports about the energy consumption of the house and its devices. It implements the {@link ReportVisitor} interface to visit various entities within the smart home simulation and collect consumption data for reporting.
 */
public class ConsumptionReport implements ReportVisitor {
    /** Total energy consumption in kilowatt-hours (kWh) */
    public double totalConsumption = 0;
    /** Price for 1 kWh in Czech crowns (CZK) */
    public final double kwhPrice = 7.35;

    /**
     * Visits a device system, generates a report for its consumption, and updates the total consumption.
     *
     * @param deviceSystem The device system to visit.
     * @return A report for the device system's consumption.
     */
    @Override
    public Report visitDeviceSystem(DeviceSystem deviceSystem) {
        Report report = new Report();

        StringBuilder deviceSystemStringBuilder = new StringBuilder();

        deviceSystemStringBuilder.append("Device System (").append(deviceSystem.getClass().getSimpleName()).append(") with ID: ").append(deviceSystem.getDeviceSystemID()).append(" consumed: ").append(String.format("%.2f", deviceSystem.getTotalConsumption())).append(" kWh\n");
        deviceSystemStringBuilder.append("\nDevices: \n");
        for (IDevice device : deviceSystem.getDevices()) {
            deviceSystemStringBuilder.append(device.getClass().getSimpleName()).append(" consumed: ").append(String.format("%.2f", device.getTotalPowerConsumption())).append(" kWh.").append("\n");
        }
        deviceSystemStringBuilder.append("\n");

        report.setContent(deviceSystemStringBuilder.toString());


        totalConsumption += deviceSystem.getTotalConsumption();


        return report;
    }

    @Override
    public Report visitHuman(Human human) {
        return null;
    }

    @Override
    public Report visitPet(Pet pet) {
        return null;
    }

    @Override
    public Report visitHouse(House house) {
        return null;
    }

    /**
     * Creates a comprehensive report by visiting all device systems in the house facade, calculating total consumption, and providing pricing information.
     *
     * @param houseFacade The house facade containing device systems.
     * @return A comprehensive report summarizing energy consumption and costs.
     */
    @Override
    public Report createReport(HouseFacade houseFacade) {
        Report finalReport = new Report();
        finalReport.setReportId(UUID.randomUUID());
        finalReport.setTimestamp(Instant.now());

        int numberOfTicks = houseFacade.getNumberOfTicks();

        StringBuilder reportContent = new StringBuilder();

        reportContent.append("House consumption report\n");
        reportContent.append("--------------------------\n");
        reportContent.append("\n");

        for (DeviceSystem deviceSystem : houseFacade.getDeviceSystems()) {
            Report deviceSystemReport = deviceSystem.accept(this);
            reportContent.append(deviceSystemReport.getContent());
        }

        reportContent.append("--------------------------\n");
        reportContent.append("Total consumption (kWh):\n");
        reportContent.append(String.format("%.2f", totalConsumption)).append(" kWh\n");
        reportContent.append("--------------------------\n");
        reportContent.append("Price for 1 kWh: " + kwhPrice + " CZK\n");
        double total = kwhPrice * totalConsumption;
        reportContent.append("Total: ").append(String.format("%.2f", total)).append(" CZK\n");

        int hours = numberOfTicks / 6;
        int minutes = (numberOfTicks % 6) * 10;
        reportContent.append("Total time: ").append(hours).append(" hours and ").append(minutes).append(" minutes\n");

        finalReport.setContent(reportContent.toString());

        return finalReport;
    }


}

