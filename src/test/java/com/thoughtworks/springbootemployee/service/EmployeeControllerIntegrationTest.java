package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.RequestResultMatchers;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EmployeeControllerIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_delete_employee_when_delete_given_company_id() throws Exception {
        //given
        Company company = companyRepository.save(new Company("TomAndJerry"));
        Employee employee = employeeRepository.save(new Employee(1, "Lola", 18, "Female", 2000, company.getCompanyId()));
        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{employeeID}", employee.getId()))
                //then
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void should_get_all_employees_when_get() throws Exception {

        //given
        Company company = companyRepository.save(new Company("TomAndJerry"));
        employeeRepository.save(new Employee(1, "Tom", 18, "Male", 1000, company.getCompanyId()));

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                //then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Tom"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(18))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("Male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].company_id").value(1));
    }

    @Test
    public void should_create_employee_when_create_given_employee_request() throws Exception {
        //given
        String employeeAsJson = "{\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"Lola\",\n" +
                "    \"age\": 18,\n" +
                "    \"gender\": \"Female\",\n" +
                "    \"salary\": 2000.0,\n" +
                "    \"company_id\": 1\n" +
                "}";

        companyRepository.save(new Company("TomAndJerry"));

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeAsJson))
                //then
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lola"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(18))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("Female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(2000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.company_id").value(1));
    }

    @Test
    public void should_find_employee_when_get_given_employee_id() throws Exception {

        //given
        Company company = companyRepository.save(new Company("TomAndJerry"));
        Employee employee = employeeRepository.save(new Employee(3, "Tom", 18, "Male", 1000, company.getCompanyId()));

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{employeeID}",employee.getId()))
                //then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Tom"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(18))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("Male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.company_id").value(company.getCompanyId()));
    }

    @Test
    public void should_update_employee_when_update_given_employee_id() throws Exception {
        //given
        String employeeAsJson = "{\n" +
                "    \"id\": 4,\n" +
                "    \"name\": \"Lola\",\n" +
                "    \"age\": 18,\n" +
                "    \"gender\": \"Female\",\n" +
                "    \"salary\": 2000.0,\n" +
                "    \"company_id\": 1\n" +
                "}";

        Company company = companyRepository.save(new Company("TomAndJerry"));
        Employee employee = employeeRepository.save(new Employee(4, "Tom", 18, "Male", 1000, company.getCompanyId()));

        //when
        mockMvc.perform(MockMvcRequestBuilders.put("/employees/{employeeID}", employee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeAsJson))
                //then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lola"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("Female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(2000));
    }

    @Test
    public void should_find_employee_when_find_given_gender() throws Exception {

        //given
        Company company = companyRepository.save(new Company("TomAndJerry"));
        employeeRepository.save(new Employee(6, "Tom", 18, "Male", 1000, company.getCompanyId()));
        employeeRepository.save(new Employee(6, "Lola", 18, "Female", 1000, company.getCompanyId()));

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/employees?gender=Male"))
                //then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Tom"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(18))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("Male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].company_id").value(1));
    }

    @Test
    public void should_find_employees_when_find_given_page_and_page_size() throws Exception {

        //given
        Company company = companyRepository.save(new Company("TomAndJerry"));
        employeeRepository.save(new Employee(7, "Tom", 18, "Male", 1000, company.getCompanyId()));
        employeeRepository.save(new Employee(7, "Lola", 18, "Female", 1000, company.getCompanyId()));
        Employee employee3 = employeeRepository.save(new Employee(7, "Nina", 18, "Female", 1000, company.getCompanyId()));

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/employees?page=1&pageSize=2"))
                //then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employee3.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(employee3.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value(employee3.getGender()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(employee3.getSalary()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].company_id").value(employee3.getCompany_id()));
    }
}
