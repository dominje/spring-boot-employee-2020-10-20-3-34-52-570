package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private final List<Company> companyList = new ArrayList<>();

    public CompanyRepository() {
        companyList.add(new Company(1, "Toyota"));
        companyList.add(new Company(2, "Mitsubishi"));
        companyList.add(new Company(3, "Ferrari"));
        companyList.add(new Company(4, "Apple"));
        companyList.add(new Company(5, "Samsung"));
    }

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

    public List<Company> setPagination(int page, int pageSize) {
        return companyList.stream()
                .sorted(Comparator.comparing(Company::getCompanyId))
                .skip(page)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public void deleteEmployeesByCompanyId(int companyId) {
        EmployeeRepository employeeRepository = new EmployeeRepository();
        getEmployeesByCompanyId(companyId).forEach(employee -> employeeRepository.deleteById(employee.getId()));
    }
}
