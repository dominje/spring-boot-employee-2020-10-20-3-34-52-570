package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;

import java.util.ArrayList;
import java.util.List;

public class CompanyRepository {
    private final List<Company> companyList = new ArrayList<>();

    public List<Company> findAll() {
        return companyList;
    }

    public Company save(Company companyRequest) {
        companyList.add(companyRequest);
        return companyRequest;
    }

    public Company findCompanyById(int companyId) {
        return companyList.stream()
                .filter(company -> company.getCompanyId() == companyId)
                .findFirst()
                .orElse(null);
    }
}
