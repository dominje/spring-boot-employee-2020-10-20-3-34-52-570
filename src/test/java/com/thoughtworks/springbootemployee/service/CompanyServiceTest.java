package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;

class CompanyServiceTest {

    @Test
    void should_get_all_when_get_comapnies() {
        // given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        List<Company> companyList = asList(new Company(), new Company());
        when(companyRepository.findAll()).thenReturn(companyList);
        CompanyService companyService = new CompanyService(companyRepository);

        // when
        List<Company> actual = companyService.getAll();

        // then
        Assertions.assertEquals(2, actual.size());
    }

    @Test
    void should_create_comapany_when_create_given_company_request() {

        // given
        Company companyRequest = new Company(1, "Telus");
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);

        when(companyRepository.save(companyRequest)).thenReturn(companyRequest);
        CompanyService companyService = new CompanyService(companyRepository);

        // when
        Company actual = companyService.create(companyRequest);

        // then
        Assertions.assertEquals(companyRequest.getCompanyId(), actual.getCompanyId());
    }

    @Test
    public void should_get_company_when_get_by_company_id_given_company_id() {

        // given
        Company company = new Company(1, "Telus");

        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        when(companyRepository.findCompanyById(company.getCompanyId())).thenReturn(company);
        CompanyService companyService = new CompanyService(companyRepository);

        // when
        Company actual = companyService.getCompanyById(company.getCompanyId());

        // then
        Assertions.assertEquals(company.getCompanyId(), actual.getCompanyId());

    }

    @Test
    public void should_update_company_when_update_by_company_id_given_id() {

        // given
        Company company = new Company(1, "Telus");
        Company updatedCompany = new Company(1, "SVG");

        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);

        when(companyRepository.updateByCompanyId(company.getCompanyId(), updatedCompany)).thenReturn(updatedCompany);
        CompanyService companyService = new CompanyService(companyRepository);

        // when
        Company actual = companyService.updateByCompanyId(company.getCompanyId(), updatedCompany);

        // then
        Assertions.assertNotEquals(company.getCompanyName(), actual.getCompanyName());
    }

    @Test
    public void should_get_employees_in_company_when_get_employees_given_company_id() {

        // given
        Company company = new Company(1, "Telus");
        Employee employee = new Employee(1, "Tom", 18, "Male", 1000, 1);

        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);

        when(companyRepository.getEmployeesByCompanyId(company.getCompanyId())).thenReturn(Collections.singletonList(employee));
        CompanyService companyService = new CompanyService(companyRepository);

        // when
        List<Employee> actual = companyService.getEmployeesByCompanyId(company.getCompanyId());

        // then
        Assertions.assertEquals(company.getCompanyId(), actual.getCompanyId);

    }
}