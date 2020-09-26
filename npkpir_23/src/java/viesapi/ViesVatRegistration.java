/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viesapi;

import java.util.Date;

/**
 *
 * @author Osito
 */
public class ViesVatRegistration {
    private String country;
    private String vatNumber;
    private Date requestDate;
    private String name;
    private String address;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "ViesVatRegistration{" + "country=" + country + ", vatNumber=" + vatNumber + ", requestDate=" + requestDate + ", name=" + name + ", address=" + address + '}';
    }
}
