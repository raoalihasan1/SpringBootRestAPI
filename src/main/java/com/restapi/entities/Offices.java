package com.restapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Set;
import java.util.HashSet;

@Entity
public class Offices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long officeID;

    private String addressLineOne;

    private String addressLineTwo;

    private String City;

    private String State;

    private String postCode;

    private String phoneNumber;

    @OneToMany(mappedBy = "employeeOffice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"offices"})
    private Set<Employees> employeesSet = new HashSet<Employees>();

    public Offices() {}

    public Offices(String addressLineOne, String addressLineTwo, String City, String State, String postCode, String phoneNumber) {
        this.addressLineOne = addressLineOne;
        this.addressLineTwo = addressLineTwo;
        this.City = City;
        this.State = State;
        this.postCode = postCode;
        this.phoneNumber = phoneNumber;
    }

    public long getOfficeID() { return this.officeID; }

    public String getAddressLineOne() { return this.addressLineOne; }

    public String getAddressLineTwo() { return this.addressLineTwo; }

    public String getCity() { return this.City; }

    public String getState() { return this.State; }

    public String getPostCode() { return this.postCode; }

    public String getPhoneNumber() { return this.phoneNumber; }

    public Set<Employees> getEmployeesSet() { return this.employeesSet; }

    public void setAddressLineOne(String addressLineOne) { this.addressLineOne = addressLineOne; }

    public void setAddressLineTwo(String addressLineTwo) { this.addressLineTwo = addressLineTwo; }

    public void setCity(String City) { this.City = City; }

    public void setState(String State) { this.State = State; }

    public void setPostCode(String postCode) { this.postCode = postCode; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

}
