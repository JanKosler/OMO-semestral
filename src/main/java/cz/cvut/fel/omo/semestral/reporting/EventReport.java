package cz.cvut.fel.omo.semestral.reporting;

import cz.cvut.fel.omo.semestral.entity.actions.ControllerRecord;
import cz.cvut.fel.omo.semestral.entity.beings.Human;
import cz.cvut.fel.omo.semestral.entity.beings.Pet;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.Controller;
import cz.cvut.fel.omo.semestral.entity.livingSpace.House;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;
import cz.cvut.fel.omo.semestral.entity.systems.SecuritySystem;
import cz.cvut.fel.omo.semestral.simulation.HouseFacade;

import java.time.Instant;
import java.util.UUID;

public class EventReport implements ReportVisitor{
    @Override
    public Report visitDeviceSystem(DeviceSystem deviceSystem) {
        Report report = new Report();

        StringBuilder deviceSystemStringBuilder = new StringBuilder();

        deviceSystemStringBuilder.append("Device System (").append(deviceSystem.getClass().getSimpleName()).append(") with ID: ").append(deviceSystem.getDeviceSystemID()).append("\n");
        deviceSystemStringBuilder.append("\nController action report: \n");

        Controller controller = deviceSystem.getController();

        for(ControllerRecord controllerRecord : controller.getRecords()){
            deviceSystemStringBuilder.append("[TICK: ").append(controllerRecord.getTick()).append("] ").append(controllerRecord.getController().getClass().getSimpleName()).append(": ").append(controllerRecord.getAction()).append("\n");
        }

        deviceSystemStringBuilder.append("\n");

        report.setContent(deviceSystemStringBuilder.toString());

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

        reportContent.append("Controllers event report\n");
        reportContent.append("--------------------------\n");
        reportContent.append("\nDesign systems:\n");

        for(DeviceSystem deviceSystem : houseFacade.getDeviceSystems()){
            reportContent.append(visitDeviceSystem(deviceSystem).getContent());
        }

        finalReport.setContent(reportContent.toString());

        return finalReport;
    }
}
