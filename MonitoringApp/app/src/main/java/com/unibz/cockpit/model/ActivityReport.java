package com.unibz.cockpit.model;

public class ActivityReport {
    Integer activity_id;
    String activity_acronym;
    Integer units;
    int progress;


    public ActivityReport() {
        super();
    }
    public ActivityReport(Integer activity_id, String activity_acronym, Integer units) {
        super();
        this.activity_id = activity_id;
        this.activity_acronym = activity_acronym;
        this.units = units;
    }
    public Integer getActivity_id() {
        return activity_id;
    }
    public void setActivity_id(Integer activity_id) {
        this.activity_id = activity_id;
    }
    public String getActivity_acronym() {
        return activity_acronym;
    }
    public void setActivity_acronym(String activity_acronym) {
        this.activity_acronym = activity_acronym;
    }
    public Integer getUnits() {
        return units;
    }
    public void setUnits(Integer units) {
        this.units = units;
    }
    public int getProgress() {
        return progress;
    }
    public void setProgress(int progress) {
        this.progress = progress;
    }


}
