package com.mynt.parcel.costapi.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("Rule")
public class Rule {

    @Value("${parcel-rule.heavy}")
    private double heavy;

    @Value("${parcel-rule.small}")
    private double small;

    @Value("${parcel-rule.medium}")
    private double medium;

    @Value("${parcel-rule.large}")
    private double large;

    public double getHeavy() {
        return heavy;
    }

    public void setHeavy(double heavy) {
        this.heavy = heavy;
    }

    public double getSmall() {
        return small;
    }

    public void setSmall(double small) {
        this.small = small;
    }

    public double getMedium() {
        return medium;
    }

    public void setMedium(double medium) {
        this.medium = medium;
    }

    public double getLarge() {
        return large;
    }

    public void setLarge(double large) {
        this.large = large;
    }


}
