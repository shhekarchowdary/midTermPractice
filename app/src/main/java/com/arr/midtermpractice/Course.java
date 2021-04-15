package com.arr.midtermpractice;

public class Course {
    private String level;
    private String name;
    private double basicFee;

    public Course(String level, String name, double basicFee) {
        this.level = level;
        this.name = name;
        this.basicFee = basicFee;
    }

    public String getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public double getBasicFee() {
        return basicFee;
    }
}
