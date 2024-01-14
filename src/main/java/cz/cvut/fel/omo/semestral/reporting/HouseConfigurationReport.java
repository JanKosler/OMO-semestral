package cz.cvut.fel.omo.semestral.reporting;

import cz.cvut.fel.omo.semestral.entity.beings.Human;
import cz.cvut.fel.omo.semestral.entity.beings.Pet;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Floor;
import cz.cvut.fel.omo.semestral.entity.livingSpace.House;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;
import cz.cvut.fel.omo.semestral.simulation.HouseFacade;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Class for generating reports about the house configuration.
 */
public class HouseConfigurationReport implements ReportVisitor{

    @Override
    public Report visitDeviceSystem(DeviceSystem deviceSystem) {
        Report report = new Report();

        StringBuilder deviceSystemStringBuilder = new StringBuilder();

        deviceSystemStringBuilder.append("\nDevice System (").append(deviceSystem.getClass().getSimpleName()).append(")with ID:\n").append(deviceSystem.getDeviceSystemID()).append("\n");
        deviceSystemStringBuilder.append("Devices: \n");
        for(IDevice device : deviceSystem.getDevices()){
            deviceSystemStringBuilder.append("Device (").append(device.getClass().getSimpleName()).append("), with ID: ").append(device.getSerialNumber()).append("\n");
        }

        report.setContent(deviceSystemStringBuilder.toString());

        return report;
    }

    @Override
    public Report visitHuman(Human human) {

        Report report = new Report();

        report.setContent("\nHuman with Name: " + human.getName() + "\n");

        return report;
    }

    @Override
    public Report visitPet(Pet pet) {
        Report report = new Report();

        report.setContent("\nPet with Name: " + pet.getName() + "\n");

        return report;
    }

    @Override
    public Report visitHouse(House house) {
        Report report = new Report();

        StringBuilder houseReportContent = new StringBuilder();

        List<Floor> floorsList = new ArrayList<>(house.getFloors().values());

        houseReportContent.append("House ID: ").append(house.getHouseID()).append("\n");
        houseReportContent.append("Number of Floors: ").append(house.getFloors().size()).append("\n");
        for(Floor floor : floorsList) {
            houseReportContent.append("\nFloor ").append(floor.getFloorLevel()).append(":\n");
            houseReportContent.append("Number of Rooms: ").append(floor.getRooms().size()).append("\n");
            for (Room room : floor.getRooms()) {
                houseReportContent.append("\nRoom ").append(room.getRoomID()).append(":\n");
                houseReportContent.append("Number of Device Systems: ").append(room.getDeviceSystems().size()).append("\n");
                for(DeviceSystem deviceSystem : room.getDeviceSystems()){
                    houseReportContent.append(deviceSystem.accept(this).getContent());
                }
            }
        }

        report.setContent(houseReportContent.toString());

        return report;
    }

    @Override
    public Report createReport(HouseFacade houseFacade) {
        Report finalReport = new Report();
        finalReport.setReportId(UUID.randomUUID());
        finalReport.setTimestamp(Instant.now());

        StringBuilder reportContent = new StringBuilder();

        reportContent.append("House Configuration Report\n");
        reportContent.append("--------------------------\n\n");

        reportContent.append(houseFacade.getHouse().accept(this).getContent());

        reportContent.append("--------------------------\n\n");

        reportContent.append("Humans:\n");
        reportContent.append("Number of humans: ").append(houseFacade.getHumans().size()).append("\n");
        for (Human human : houseFacade.getHumans()) {
            reportContent.append(human.accept(this).getContent());
        }

        reportContent.append("--------------------------\n\n");

        reportContent.append("Pets:\n");
        reportContent.append("Number of pets: ").append(houseFacade.getPets().size()).append("\n");
        for (Pet pet : houseFacade.getPets()) {
            reportContent.append(pet.accept(this).getContent());
        }

        finalReport.setContent(reportContent.toString());

        return finalReport;
    }

}

