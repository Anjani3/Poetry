package com.anjani.poetry.Models;

public class poetrymodel {
    int id;                                 //here we same name as in our database
    String poetry_data;
    String poet_name;
    String Date_time;

    public poetrymodel(int id, String poetry_data, String poet_name, String Date_time) {
        this.id = id;
        this.poetry_data = poetry_data;
        this.poet_name = poet_name;
        this.Date_time = Date_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoetry_data() {
        return poetry_data;
    }

    public void setPoetry_data(String poetry_data) {
        this.poetry_data = poetry_data;
    }

    public String getPoet_name() {
        return poet_name;
    }

    public void setPoet_name(String poet_name) {
        this.poet_name = poet_name;
    }

    public String getDate_time() {
        return Date_time;
    }

    public void setDate_time(String date_time) {
        Date_time = date_time;
    }
}
