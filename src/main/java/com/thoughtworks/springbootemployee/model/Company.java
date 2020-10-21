package com.thoughtworks.springbootemployee.model;

public class Company {

    private String companyName;
    private int companyId;

    public Company(int companyId, String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
