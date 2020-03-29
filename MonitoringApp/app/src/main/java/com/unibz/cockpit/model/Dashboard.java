package com.unibz.cockpit.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Dashboard {

    Integer progress;
    ArrayList<ActivityReport> activityReport;
    HashMap<String, Double> locationReport;
    public Integer getProgress() {
        return progress;
    }
    public void setProgress(Integer progress) {
        this.progress = progress;
    }
    public ArrayList<ActivityReport> getActivityReport() {
        return activityReport;
    }
    public void setActivityReport(ArrayList<ActivityReport> activityReport) {
        this.activityReport = activityReport;
    }
    public HashMap<String, Double> getLocationReport() {
        return locationReport;
    }
    public void setLocationReport(HashMap<String, Double> locationReport) {
        this.locationReport = locationReport;
    }




}
