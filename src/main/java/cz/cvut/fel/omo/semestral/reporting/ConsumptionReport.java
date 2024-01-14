package cz.cvut.fel.omo.semestral.reporting;

import cz.cvut.fel.omo.semestral.entity.beings.Human;
import cz.cvut.fel.omo.semestral.entity.beings.Pet;
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
    public final double kwhPrice = 3;

    @Override
    public Report visitDeviceSystem(DeviceSystem deviceSystem) {
        Report report = new Report();

        report.setContent("Device System (" + deviceSystem.getClass().getSimpleName() + ")with ID:\n" + deviceSystem.getDeviceSystemID() +
                "consumed: " + deviceSystem.getTotalConsumption() + " mWh\n" +
                "\n");

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

        StringBuilder reportContent = new StringBuilder();

        reportContent.append("House consumption report\n");
        reportContent.append("--------------------------\n");

        for (DeviceSystem deviceSystem : houseFacade.getDeviceSystems()) {
            Report deviceSystemReport = deviceSystem.accept(this);
            reportContent.append(deviceSystemReport.getContent());
        }

        reportContent.append("--------------------------\n");
        reportContent.append("Total consumption (kWh):\n");
        reportContent.append(totalConsumption).append(" mWh\n");
        reportContent.append("--------------------------\n");
        reportContent.append("Price for 1kWh: " + kwhPrice + " CZK\n");
        double total = kwhPrice * totalConsumption;
        reportContent.append("Total: ").append(String.format("%.2f", total)).append(" CZK\n");

        finalReport.setContent(reportContent.toString());

        return finalReport;
    }


}

