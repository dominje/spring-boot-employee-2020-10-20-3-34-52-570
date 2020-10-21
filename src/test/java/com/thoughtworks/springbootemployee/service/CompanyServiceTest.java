package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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

}