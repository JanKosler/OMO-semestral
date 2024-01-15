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
 * Class for generating reports about the consumption of the house.
 */
public class ConsumptionReport implements ReportVisitor {

    public double totalConsumption = 0;
    public final double kwhPrice = 7.35;

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

