package com.worthsoln.patientview.notification;

import java.util.Date;

public class Notification {
    private String name;
    private Date lastnotification;

    public Notification() {
    }

    public Notification(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastnotification() {
        return lastnotification;
    }

    public void setLastnotification(Date lastnotification) {
        this.lastnotification = lastnotification;
    }
}
