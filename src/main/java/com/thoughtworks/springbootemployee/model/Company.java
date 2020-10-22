package com.thoughtworks.springbootemployee.model;

import sun.jvm.hotspot.debugger.cdbg.basic.LazyType;

import javax.persistence.*;
import java.util.List;

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int companyId;
    private String companyName;
    @OneToMany
            (
                    fetch = FetchType.EAGER
            )
    @JoinColumn(name="company_id")
    private List<Employee> employeeList;

    public Company() {
    }

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
