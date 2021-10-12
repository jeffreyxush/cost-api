package com.mynt.parcel.costapi.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("Message")
public class Message {

    @Value("${parcel-message.heavy}")
    private String heavy;

    @Value("${parcel-message.small}")
    private String small;

    @Value("${parcel-message.medium}")
    private String medium;

    @Value("${parcel-message.large}")
    private String large;

    @Value("${parcel-message.reject}")
    private String reject;


    public String getHeavy() {
        return heavy;
    }

    public void setHeavy(String heavy) {
        this.heavy = heavy;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }
    public String getReject() {
        return reject;
    }

    public void setReject(String reject) {
        this.reject = reject;
    }

}
