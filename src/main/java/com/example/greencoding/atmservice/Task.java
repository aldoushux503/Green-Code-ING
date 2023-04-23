package com.example.greencoding.atmservice;

public class Task implements Comparable<Task> {
    private int region;
    private RequestType requestType;
    private int atmId;

    public Task(int region, RequestType requestType, int atmId) {
        this.region = region;
        this.requestType = requestType;
        this.atmId = atmId;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public int getAtmId() {
        return atmId;
    }

    public void setAtmId(int atmId) {
        this.atmId = atmId;
    }

    @Override
    public int compareTo(Task o) {
        return Integer.compare(this.getRegion(), o.getRegion());
    }
}
