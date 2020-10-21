package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

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

    @Test
    void should_create_employee_when_create_given_employee_request() {

        // given
        Employee employeeRequest = new Employee(1, "Tom", 18, "Male", 1000);
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);

        when(employeeRepository.save(employeeRequest)).thenReturn(employeeRequest);
        EmployeeService service = new EmployeeService(employeeRepository);

        // when
        Employee actual = service.create(employeeRequest);

        // then
        Assertions.assertEquals(employeeRequest.getId(), actual.getId());
    }

    @Test
    public void should_get_employee_when_get_by_id_given_id(){

        // given
        Employee employee = new Employee(1, "Tom", 18, "Male", 1000);

        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);

        when(employeeRepository.findById(employee.getId())).thenReturn(employee);
        EmployeeService service = new EmployeeService(employeeRepository);

        // when
        Employee actual = service.findById(employee.getId());

        // then
        Assertions.assertEquals(employee.getId(), actual.getId());

    }

    @Test
    public void should_update_employee_when_update_by_id_given_id(){

        // given
        Employee employee = new Employee(1, "Tom", 18, "Male", 1000);
        Employee updatedEmployee = new Employee(1, "Tom", 18, "Male", 3000);

        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);

        when(employeeRepository.updateById(employee.getId(), employee)).thenReturn(updatedEmployee);
        EmployeeService service = new EmployeeService(employeeRepository);

        // when
        Employee actual = service.updateById(employee.getId(), employee);

        // then
        Assertions.assertNotEquals(employee.getSalary(), actual.getSalary());

    }

    @Test
    public void should_delete_employee_when_delete_given_id(){

        // given
        Employee employee = new Employee(1, "Tom", 18, "Male", 1000);
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        // when
        employeeService.deleteById(employee.getId());

        // then
        verify(employeeRepository, times(1)).deleteById(employee.getId());

    }

}