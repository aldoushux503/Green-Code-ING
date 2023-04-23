package com.example.greencoding;

import java.util.Objects;

public class Atm {
    private int region;
    private int atmId;

    public Atm(int region, int atmId) {
        this.region = region;
        this.atmId = atmId;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public int getAtmId() {
        return atmId;
    }

    public void setAtmId(int atmId) {
        this.atmId = atmId;
    }

}
