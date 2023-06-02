package com.example.kinoprokatrest.models;

public enum AgeRestriction {

    A(0), B(6), C(12), D(14), E(16), F(18);
    private int numVal;

    AgeRestriction(int numVal) {
        this.numVal = numVal;
    }

    AgeRestriction() {
    }

    public int getNumVal() {
        return numVal;
    }
}
