package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;

class EmployeeServiceTest {

    @Test
    void should_get_all_when_get_employees() {

        // given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        List<Employee> employeeList = asList(new Employee(), new Employee());
        when(employeeRepository.findAll()).thenReturn(employeeList);
        EmployeeService service = new EmployeeService(employeeRepository);

        // when
        List<Employee> actual = service.getAll();

        // then
        Assertions.assertEquals(2, actual.size());
    }
}