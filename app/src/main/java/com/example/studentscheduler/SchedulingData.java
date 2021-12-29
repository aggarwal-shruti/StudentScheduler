package com.example.studentscheduler;

import java.util.Date;
import java.util.List;

public class SchedulingData {

    private Date date;
    private List<Data> data;

    public SchedulingData() {
    }

    public SchedulingData(Date date, List<Data> data) {
        this.date = date;
        this.data = data;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
