package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public void should_get_employee_when_get_by_id_given_id() {

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
    public void should_update_employee_when_update_by_id_given_id() {

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
    public void should_delete_employee_when_delete_given_id() {

        // given
        Employee employee = new Employee(1, "Tom", 18, "Male", 1000);
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        // when
        employeeService.deleteById(employee.getId());

        // then
        verify(employeeRepository, times(1)).deleteById(employee.getId());

    }

    @Test
    public void should_get_all_male_employees_when_get_by_gender_given_male_gender() {

        // given

        String gender = "Male";
        Employee firstMaleEmployee = new Employee(1, "Tom", 18, "Male", 1000);
        Employee secondMaleEmployee = new Employee(1, "Pooh", 18, "Male", 1000);

        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);

        when(employeeRepository.findByGender(gender)).thenReturn(asList(firstMaleEmployee, secondMaleEmployee));
        EmployeeService service = new EmployeeService(employeeRepository);

        // when
        List<Employee> actual = service.findByGender(gender);

        // then
        Assertions.assertEquals(asList(firstMaleEmployee, secondMaleEmployee), actual);

    }

    @Test
    public void should_get_all_female_employees_when_get_by_gender_given_female_gender() {

        // given

        String gender = "Female";
        Employee femaleEmployee = new Employee(1, "Jerry", 18, "Female", 1000);

        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);

        when(employeeRepository.findByGender(gender)).thenReturn(asList(femaleEmployee));
        EmployeeService service = new EmployeeService(employeeRepository);

        // when
        List<Employee> actual = service.findByGender(gender);

        // then
        Assertions.assertEquals(asList(femaleEmployee), actual);

    }

    @Test
    public void should_get_all_employees_when_set_pagination_given_page_and_pagesize() {

        // given
        int page = 2;
        int pageSize = 5;
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1, "Tom", 18, "Male", 1000));
        employeeList.add(new Employee(2, "Tom", 18, "Male", 1000));
        employeeList.add(new Employee(3, "Tom", 18, "Male", 1000));
        employeeList.add(new Employee(4, "Tom", 18, "Male", 1000));
        employeeList.add(new Employee(5, "Tom", 18, "Male", 1000));
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);

        List<Employee> expectedEmployeeList = employeeList.stream().sorted(Comparator.comparing(Employee::getId))
                .skip(page)
                .limit(pageSize)
                .collect(Collectors.toList());

        when(employeeRepository.setPagination(page, pageSize)).thenReturn(expectedEmployeeList);
        EmployeeService service = new EmployeeService(employeeRepository);

        // when
        List<Employee> actual = service.setPagination(page, pageSize);

        // then
        Assertions.assertEquals(3, actual.size());
    }

}


