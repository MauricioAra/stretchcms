package com.rammp.stretchyourbody.service.dto;

import java.io.Serializable;

/**
 * Created by mbp on 8/12/17.
 */
public class ResultAverageDTO implements Serializable {

    private int average;

    public ResultAverageDTO() {

    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }
}
