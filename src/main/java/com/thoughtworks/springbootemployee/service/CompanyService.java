package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Company> getCompanyById(int companyId) {
        return companyRepository.findById(companyId);
    }

    public Company updateByCompanyId(int companyId, Company company) {
        Optional<Company> updatedCompany = companyRepository.findById(companyId);
        if(updatedCompany.isPresent()){
            updatedCompany.get().setCompanyName(company.getCompanyName());
            return companyRepository.save(updatedCompany.get());
        }
        return null;
    }

    public List<Employee> getEmployeesByCompanyId(int companyId) {
        Optional<Company> foundCompany = companyRepository.findById(companyId);
        return foundCompany.map(Company::getEmployeeList).orElse(null);
    }

    public List<Company> getCompanyWithPagination(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return companyRepository.findAll(pageable).toList();
    }

    public void deleteEmployeesByCompanyId(int companyId) {
        companyRepository.deleteById(companyId);
    }
}
