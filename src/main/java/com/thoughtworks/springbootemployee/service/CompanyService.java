package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;

import java.util.List;

public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public Company create(Company companyRequest) {
        return companyRepository.save(companyRequest);
    }

    public Company getCompanyById(int companyId) {
        return companyRepository.findCompanyById(companyId);
    }

    public Company updateByCompanyId(int companyId, Company updatedCompany) {
        return companyRepository.updateByCompanyId(companyId, updatedCompany);
    }

    public List<Employee> getEmployeesByCompanyId(int companyId) {
        return companyRepository.getEmployeesByCompanyId(companyId);
    }

    public List<Company> setPagination(int page, int pageSize) {
        return companyRepository.setPagination(page, pageSize);
    }
}
