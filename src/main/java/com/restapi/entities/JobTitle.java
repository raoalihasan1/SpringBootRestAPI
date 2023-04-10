package com.restapi.entities;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum JobTitle {

    President(0),
    VPSales(1),
    VPMarketing(2),
    SalesRep(3),
    SalesManagerNA(4),
    SalesManagerAPAC(5),
    SalesManagerEMEA(6);

    private final int jobTitleVal;

    JobTitle(int jobTitleVal) {
        this.jobTitleVal = jobTitleVal;
    }

    public int getTitleCode() { return this.jobTitleVal; }

    @JsonCreator
    public static JobTitle getJobTitleFromCode(int jobTitleID) {
        for (JobTitle title : JobTitle.values())
            if (title.getTitleCode() == jobTitleID) return title;
        return null;
    }

}
