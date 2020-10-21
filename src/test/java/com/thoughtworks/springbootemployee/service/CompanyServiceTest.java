package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        Assertions.assertTrue(actual.contains(employee));

    }

    @Test
    public void should_get_all_companies_when_set_pagination_given_page_and_pagesize() {

        // given
        int page = 2;
        int pageSize = 5;
        List<Company> companyList = new ArrayList<>();
        companyList.add(new Company(1, "Toyota"));
        companyList.add(new Company(2, "Mitsubishi"));
        companyList.add(new Company(3, "Ferrari"));
        companyList.add(new Company(4, "Apple"));
        companyList.add(new Company(5, "Samsung"));
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);

        List<Company> expectedCompanyList = companyList.stream().sorted(Comparator.comparing(Company::getCompanyId))
                .skip(page)
                .limit(pageSize)
                .collect(Collectors.toList());

        when(companyRepository.setPagination(page, pageSize)).thenReturn(expectedCompanyList);
        CompanyService companyService = new CompanyService(companyRepository);

        // when
        List<Company> actual = companyService.setPagination(page, pageSize);

        // then
        Assertions.assertEquals(expectedCompanyList.size(), actual.size());
    }
}