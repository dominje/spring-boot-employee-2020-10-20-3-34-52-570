package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
        return companyRepository.findById(companyId);
    }

//    public Company updateByCompanyId(int companyId, Company updatedCompany) {
//        return companyRepository.updateByCompanyId(companyId, updatedCompany);
//    }
//
//    public List<Employee> getEmployeesByCompanyId(int companyId) {
//        return companyRepository.getEmployeesByCompanyId(companyId);
//    }
//
//    public List<Company> setPagination(int page, int pageSize) {
//        return companyRepository.getCompanyWithPagination(page, pageSize);
//    }
//
//    public void deleteEmployeesByCompanyId(int companyId) {
//        companyRepository.deleteEmployeesByCompanyId(companyId);
//    }
}
