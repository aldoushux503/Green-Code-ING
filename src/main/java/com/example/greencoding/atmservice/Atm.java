package com.example.greencoding.atmservice;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Atm atm = (Atm) o;
        return region == atm.region && atmId == atm.atmId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(region, atmId);
    }
}
