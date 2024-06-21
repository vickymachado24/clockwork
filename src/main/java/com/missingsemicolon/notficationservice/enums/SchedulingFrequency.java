package com.missingsemicolon.notficationservice.enums;


import lombok.Data;

public enum SchedulingFrequency {

    HOURLY("HOURLY", 1),
    DAILY("DAILY",2),
    WEEKLY("WEEKLY",3),
    MONTHLY("MONTHLY",4),
    QUARTERLY("QUARTERLY",5),
    YEARLY("YEARLY",6),
    CUSTOM("CUSTOM",7);

    private  String frequency;
    private  int id;

    SchedulingFrequency(String frequency, int id) {
        this.frequency = frequency;
        this.id = id;
    }


    public static SchedulingFrequency findByName(String name) {
        for (SchedulingFrequency freq : values()) {
            if (freq.frequency.equalsIgnoreCase(name)) {
                return freq;
            }
        }
        return null;
    }

    public static SchedulingFrequency findById(int id) {
        for (SchedulingFrequency freq : values()) {
            if (freq.id == id) {
                return freq;
            }
        }
        return null;
    }
}
