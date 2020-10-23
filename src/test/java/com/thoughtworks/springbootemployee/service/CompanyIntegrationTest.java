package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CompanyIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_get_all_companies_when_get() throws Exception {
        //given
        Company company = companyRepository.save(new Company("TomAndJerry"));

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                //then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName").value(company.getCompanyName()));
    }

    @Test
    public void should_create_company_when_create_given_company_request() throws Exception {
        //given
        String companyAsJson = "{\n" +
                "        \"companyName\": \"Sqanix\"\n" +
                "}";

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyAsJson))
                //then
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("Sqanix"));
    }

    @Test
    public void should_return_company_when_find_given_company_id() throws Exception {
        //given
        Company company = companyRepository.save(new Company("Sqanix"));
        companyRepository.save(new Company("Sage"));

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{companyId}", company.getCompanyId()))
                //then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("Sqanix"));
    }

    @Test
    public void should_return_employees_when_find_given_company_id() throws Exception {
        //given
        Company company1 = companyRepository.save(new Company("Sqanix"));
        Company company2 = companyRepository.save(new Company("Sage"));
        employeeRepository.save(new Employee(1, "Tom", 18, "Male", 1000, company1.getCompanyId()));
        employeeRepository.save(new Employee(2, "Jerry", 18, "Male", 1000, company1.getCompanyId()));
        employeeRepository.save(new Employee(2, "Spike", 18, "Male", 1000, company2.getCompanyId()));

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{companyId}/employees", company1.getCompanyId()))
                //then
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void should_update_company_when_update_given_company_id() throws Exception {
        //given
        String companyAsJson = "{\n" +
                "        \"companyName\": \"Sage\"\n" +
                "}";

        Company company = companyRepository.save(new Company("TomAndJerry"));

        //when
        mockMvc.perform(MockMvcRequestBuilders.put("/companies/{companyId}", company.getCompanyId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyAsJson))
                //then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("Sage"));
    }

    @Test
    public void should_delete_employee_when_delete_given_company_id() throws Exception {
        //given
        Company company = companyRepository.save(new Company("TomAndJerry"));
        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/companies/{companyID}", company.getCompanyId()))
                //then
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
