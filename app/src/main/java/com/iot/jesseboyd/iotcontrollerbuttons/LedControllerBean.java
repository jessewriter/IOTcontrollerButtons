package com.iot.jesseboyd.iotcontrollerbuttons;

/**
 * Created by portlandhomeowner on 3/31/17.
 */

public class LedControllerBean {

    boolean isOn;
    String name;
    String color;
    String frequency;
    String duration;

    public LedControllerBean(boolean isOn, String name, String color, String frequency, String duration) {
        this.isOn = isOn;
        this.name = name;
        this.color = color;
        this.frequency = frequency;
        this.duration = duration;
    }

    public LedControllerBean(){
        setName("not set");
        setColor("not set");
        setFrequency("not set");
        setIsOn(false);
        setDuration("not set");

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean getIsOn() {
        return isOn;
    }

    public void setIsOn(boolean isOnIn) {
        this.isOn = isOnIn;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "LedController{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", isON='" + isOn + '\'' +
                ", frequency='" + frequency + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }

}
