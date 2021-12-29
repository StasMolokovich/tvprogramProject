package com.tvprogram.model;

import java.util.Date;

import com.tvprogram.util.DateTimeUtils;


public class Program {
    private Date startTime;
    private Date endTime;
    private String title;
    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String toString() {
        return DateTimeUtils.timeToStr(startTime) + " - " + DateTimeUtils.timeToStr(endTime) + " - " + title;
    }
}

