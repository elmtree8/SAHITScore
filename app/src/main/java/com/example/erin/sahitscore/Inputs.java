package com.example.erin.sahitscore;

import java.io.Serializable;

/**
 * Created by erin on 26/04/16.
 * Object to keep track of data received in InputActivity.
 * @author erin
 */

public class Inputs implements Serializable {
    private final Integer age;
    private final Boolean hypertension;
    private final Integer wfns;
    private Integer size;
    private String location;
    private Integer fisher;
    private String repair;

    public Inputs(Integer age, Boolean hypertension, Integer wfns) {
        this.age = age;
        this.hypertension = hypertension;
        this.wfns = wfns;
    }

    public Integer getAge() {
        return age;
    }

    public Boolean getHypertension() {
        return hypertension;
    }

    public Integer getWfns() {
        return wfns;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getFisher() {
        return fisher;
    }

    public void setFisher(Integer fisher) {
        this.fisher = fisher;
    }

    public String getRepair() {
        return repair;
    }

    public void setRepair(String repair) {
        this.repair = repair;
    }

    public String toString() { return age + "|" + hypertension + "|" + wfns + "|" + size + "|" + location + "|" + fisher + "|" + repair; }
}
