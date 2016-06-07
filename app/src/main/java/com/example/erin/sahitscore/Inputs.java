package com.example.erin.sahitscore;

import java.io.Serializable;

/**
 * Created by erin on 26/04/16.
 * Object to keep track of data received in InputActivity
 * @author erin
 * @see InputActivity
 */

public class Inputs implements Serializable {
    private Integer age;
    private Boolean hypertension;
    private Integer wfns;
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

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getHypertension() {
        return hypertension;
    }

    public void setHypertension(Boolean hypertension) {
        this.hypertension = hypertension;
    }

    public Integer getWfns() {
        return wfns;
    }

    public void setWfns(Integer wfns) {
        this.wfns = wfns;
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
