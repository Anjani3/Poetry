package com.anjani.poetry.Response;

import com.anjani.poetry.Models.poetrymodel;

import java.util.List;

public class GetPoetryResponse {

    String status;                         //same as getting from tomcat status
    String messege;
    List<poetrymodel> data;

    public GetPoetryResponse(String status, String messege, List<poetrymodel> data) {
        this.status = status;
        this.messege = messege;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessege() {
        return messege;
    }

    public void setMessege(String messege) {
        this.messege = messege;
    }

    public List<poetrymodel> getData() {
        return data;
    }

    public void setData(List<poetrymodel> data) {
        this.data = data;
    }
}
