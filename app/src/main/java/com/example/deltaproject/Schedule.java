package com.example.deltaproject;

public class Schedule {
    String slot,starttime,endtime,done;

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }

    public Schedule(String slot, String starttime, String endtime, String done) {
        this.slot = slot;
        this.starttime = starttime;
        this.endtime = endtime;
        this.done = done;
    }
}
