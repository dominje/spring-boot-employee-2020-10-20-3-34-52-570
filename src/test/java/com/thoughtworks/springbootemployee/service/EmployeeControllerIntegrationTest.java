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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.RequestResultMatchers;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void tearDown(){
        employeeRepository.deleteAll();
    }

    @Test
    public void should_get_all_employees_when_get() throws Exception {

        //given
        Company company = new Company(1,"TomAndJerry");
        Employee employee = new Employee(1, "Tom", 18, "Male", 1000, 1);
        companyRepository.save(company);
        employeeRepository.save(employee);

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

        Company company = new Company(1,"TomAndJerry");
        companyRepository.save(company);

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
        Company company = new Company(1,"TomAndJerry");
        Employee employee = new Employee(3, "Tom", 18, "Male", 1000, 1);
        companyRepository.save(company);
        employeeRepository.save(employee);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{employeeID}",3))
                //then
                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        Optional<Employee> foundEmployee = employeeRepository.findById(3);
//        Assertions.assertEquals("Tom", foundEmployee.get().getName());
    }

//    @Test
//    public void should_update_employee_when_update_given_employee_id() throws Exception {
//        //given
//        String employeeAsJson = "{\n" +
//                "    \"id\": 4,\n" +
//                "    \"name\": \"Lola\",\n" +
//                "    \"age\": 18,\n" +
//                "    \"gender\": \"Female\",\n" +
//                "    \"salary\": 2000.0,\n" +
//                "    \"company_id\": 1\n" +
//                "}";
//
//        Company company = new Company(1,"TomAndJerry");
//        Employee employee = new Employee(4, "Tom", 18, "Male", 1000, 1);
//        companyRepository.save(company);
//        employeeRepository.save(employee);
//
//        //when
//        mockMvc.perform(MockMvcRequestBuilders.put("/employees")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(employeeAsJson))
//                //then
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        Optional<Employee> foundEmployee = employeeRepository.findById(4);
//        foundEmployee.get
//        Assertions.assertNotEquals(employee, foundEmployee.get());
//    }

    @Test
    public void should_delete_employee_when_delete_given_company_id() throws Exception {

        //given

        Company company = new Company(1,"TomAndJerry");
        Employee employee = new Employee(5, "Tom", 18, "Male", 1000, 1);
        companyRepository.save(company);
        employeeRepository.save(employee);

        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{employeeID}", 1))
                //then
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}
