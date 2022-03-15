package com.htmz.lichvn.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author vtt0053
 */
public class DateInfo {

    private int weekOfYear;
    private int dayOfYear;

    public int getWeekOfYear() {
        return weekOfYear;
    }

    public void setWeekOfYear(int weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    public int getDayOfYear() {
        return dayOfYear;
    }

    public void setDayOfYear(int dayOfYear) {
        this.dayOfYear = dayOfYear;
    }

    @Override
    public String toString() {
        return "DateInfo{" + "weekOfYear=" + weekOfYear + ", dayOfYear=" + dayOfYear + '}';
    }

}
