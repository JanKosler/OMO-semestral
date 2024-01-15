package cz.cvut.fel.omo.semestral.reporting;

import cz.cvut.fel.omo.semestral.entity.actions.Action;
import cz.cvut.fel.omo.semestral.entity.actions.ActionRecord;
import cz.cvut.fel.omo.semestral.entity.beings.Human;
import cz.cvut.fel.omo.semestral.entity.beings.Pet;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.livingSpace.House;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;
import cz.cvut.fel.omo.semestral.simulation.HouseFacade;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class for reporting events and usage of the house.
 */
public class ActivityAndUsageReport implements ReportVisitor{

    @Override
    public Report visitDeviceSystem(DeviceSystem deviceSystem) {
        return null;
    }

    @Override
    public Report visitHuman(Human human) {
        Report report = new Report();

        StringBuilder humanStringBuilder = new StringBuilder();

        humanStringBuilder.append("\nHuman with Name: ").append(human.getName()).append("\n");
        List<ActionRecord> activities = new ArrayList<>();
        List<ActionRecord> usages = new ArrayList<>();
        for(ActionRecord action : human.getPerformedActions()){
            switch (action.getAction().getType()) {
                case B_CHANGEROOM, B_REPAIR, B_STARTSPORT,B_STOPSPORT:
                    activities.add(action);
                    break;
                default:
                    usages.add(action);
                    break;
            }
        }
        humanStringBuilder.append("Number of activities: ").append(activities.size()).append("\n");
        humanStringBuilder.append("Number of usages: ").append(usages.size()).append("\n");
        humanStringBuilder.append("\nActivities: \n");
        for(ActionRecord action : activities){
            if(action.getAction().getValue() instanceof Room room){
                humanStringBuilder.append("[TICK: ").append(action.getTick()).append("] Action: ").append(action.getAction().getType()).append(" With value: ").append(room.getRoomName()).append("\n");
            } else if (action.getAction().getValue() instanceof IDevice device) {
                humanStringBuilder.append("[TICK: ").append(action.getTick()).append("] Action: ").append(action.getAction().getType()).append(" With value: ").append(device.getClass().getSimpleName()).append("\n");
            } else {
                humanStringBuilder.append("[TICK: ").append(action.getTick()).append("] Action: ").append(action.getAction().getType()).append(" With value: ").append(action.getAction().getValue()).append("\n");
            }

        }
        humanStringBuilder.append("\nUsages: \n");
        for(ActionRecord action : usages){
            humanStringBuilder.append("[TICK: ").append(action.getTick()).append("] Action: ").append(action.getAction().getType()).append(" With value: ").append(action.getAction().getValue()).append("\n");
        }

        report.setContent(humanStringBuilder.toString());


        return report;
    }

    @Override
    public Report visitPet(Pet pet) {
        Report report = new Report();

        StringBuilder humanStringBuilder = new StringBuilder();

        humanStringBuilder.append("\nPet with Name: ").append(pet.getName()).append("\n");
        List<ActionRecord> activities = new ArrayList<>();
        for(ActionRecord action : pet.getPerformedActions()){
            switch (action.getAction().getType()) {
                case B_CHANGEROOM:
                    activities.add(action);
                    break;
                default:
                    break;
            }
        }
        humanStringBuilder.append("Number of activities: ").append(activities.size()).append("\n");
        humanStringBuilder.append("\nActivities: \n");
        for(ActionRecord action : activities){
            humanStringBuilder.append("[TICK: ").append(action.getTick()).append("] Action: ").append(action.getAction().getType()).append(" With value: ").append(action.getAction().getValue()).append("\n");
        }

        report.setContent(humanStringBuilder.toString());


        return report;
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

        reportContent.append("Activity And Usage Report\n");
        reportContent.append("--------------------------\n");
        reportContent.append("\nHumans:\n");

        for (Human human : houseFacade.getHumans()) {
            Report humanActionReport = human.accept(this);
            reportContent.append(humanActionReport.getContent());
        }

        finalReport.setContent(reportContent.toString());

        return finalReport;
    }

}
