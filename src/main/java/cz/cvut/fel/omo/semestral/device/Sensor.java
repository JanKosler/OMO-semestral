package cz.cvut.fel.omo.semestral.device;

public abstract class Sensor implements IDevice {
    private DeviceState state;
    private int totalWear;
    private double powerConsumption;

    public Sensor() {
        this.state = DeviceState.OFF;
        this.totalWear = 0;
        this.powerConsumption = 0;
    }

    public Sensor(DeviceState state, int totalWear, double powerConsumption) {
        this.state = state;
        this.totalWear = totalWear;
        this.powerConsumption = powerConsumption;
    }

    public double getPowerConsumption() {
        return powerConsumption;
    }

    public DeviceState getState() {
        return state;
    }

    public int getTotalWear() {
        return totalWear;
    }

    public void setState(DeviceState state) {
        this.state = state;
    }

    public void turnOn() {
        this.state = DeviceState.ON;
    }

    public void turnOff() {
        this.state = DeviceState.OFF;
    }

    public void setTotalWear(int totalWear) {
        this.totalWear = totalWear;
    }

    public void setPowerConsumption(double powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    public void increaseTotalWear(int wear) {
        this.totalWear += wear;
    }

    public void increasePowerConsumption(double powerConsumption) {
        this.powerConsumption += powerConsumption;
    }

    public void decreasePowerConsumption(double powerConsumption) {
        this.powerConsumption -= powerConsumption;
    }

    public void decreaseTotalWear(int wear) {
        this.totalWear -= wear;
    }
}
