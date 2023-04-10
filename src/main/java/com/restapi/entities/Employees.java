package com.restapi.entities;

import jakarta.persistence.*;

@Entity
public class Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long employeeID;

    private String firstName;

    private String lastName;

    private String emailAddress;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id", nullable = false)
    private Offices employeeOffice;

    private JobTitle jobTitle;

    public Employees() {}

    public Employees(String firstName, String lastName, String emailAddress, Offices employeeOffice, JobTitle jobTitle) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.employeeOffice = employeeOffice;
        this.jobTitle = jobTitle;
    }

    public long getEmployeeID() { return this.employeeID; }

    public String getFirstName() { return this.firstName; }

    public String getLastName() { return this.lastName; }

    public String getEmailAddress() { return this.emailAddress; }

    public Offices getEmployeeOffice() { return this.employeeOffice; }

    public JobTitle getJobTitle() { return this.jobTitle; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public void setEmployeeOffice(Offices employeeOffice) { this.employeeOffice = employeeOffice; }

    public void setJobTitle(JobTitle title) { this.jobTitle = title; }

}

