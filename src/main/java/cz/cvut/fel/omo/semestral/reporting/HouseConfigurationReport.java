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
 * The {@code HouseConfigurationReport} class generates reports about the configuration of a smart home, including details about the house structure, rooms, device systems, humans, and pets. It implements the {@link ReportVisitor} interface to visit various entities within the smart home simulation and collect configuration data for reporting.
 */
public class HouseConfigurationReport implements ReportVisitor{

    /**
     * Visits a device system, generates a report for the device system's configuration, and summarizes the configuration data.
     *
     * @param deviceSystem The device system to visit.
     * @return A report containing configuration data for the device system.
     */
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

    /**
     * Visits a human and generates a report. Not implemented in this class.
     *
     * @param human The human to visit.
     * @return A report for the human.
     */
    @Override
    public Report visitHuman(Human human) {

        Report report = new Report();

        report.setContent("\nHuman with Name: " + human.getName() + "\n");

        return report;
    }

    /**
     * Visits a pet and generates a report. Not implemented in this class.
     *
     * @param pet The pet to visit.
     * @return A report for the pet.
     */
    @Override
    public Report visitPet(Pet pet) {
        Report report = new Report();

        report.setContent("\nPet with Name: " + pet.getName() + "\n");

        return report;
    }

    /**
     * Visits a house, generates a report for the entire house configuration, and summarizes the configuration data.
     *
     * @param house The house to visit.
     * @return A report containing configuration data for the entire house.
     */
    @Override
    public Report visitHouse(House house) {
        Report report = new Report();

        StringBuilder houseReportContent = new StringBuilder();

        List<Floor> floorsList = house.getFloors();

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

    /**
     * Creates a comprehensive report by visiting the house and all entities within it, including humans and pets, and summarizing the entire configuration.
     *
     * @param houseFacade The house facade containing the house and entities.
     * @return A comprehensive report summarizing the entire house configuration.
     */
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

