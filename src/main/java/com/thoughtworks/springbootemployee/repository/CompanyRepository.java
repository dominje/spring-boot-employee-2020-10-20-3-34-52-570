package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public Company updateByCompanyId(int companyId, Company updatedCompany) {
        companyList.stream()
                .filter(company -> company.getCompanyId()==companyId)
                .findFirst()
                .ifPresent(company -> {
                    companyList.remove(company);
                    companyList.add(updatedCompany);
                });
        return updatedCompany;
    }

    public List<Employee> getEmployeesByCompanyId(int companyId) {
        EmployeeRepository employeeRepository = new EmployeeRepository();
        return employeeRepository.findAll().stream()
                .filter(employee -> employee.getCompanyId()==companyId)
                .collect(Collectors.toList());
    }
}
