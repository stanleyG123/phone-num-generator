package com.stalana.phonegen.model;

/**
 * Bean to represent phone number request
 * Created by stan on 4/5/18.
 */
public class PhoneNumberRequest {
    private String phoneNumber;
    private Integer numberPerPage;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getNumberPerPage() {
        return numberPerPage;
    }

    public void setNumberPerPage(Integer numberPerPage) {
        this.numberPerPage = numberPerPage;
    }
}