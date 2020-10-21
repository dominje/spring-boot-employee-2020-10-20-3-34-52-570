package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    private final List<Employee> employeeList = new ArrayList<>();

    public List<Employee> findAll() {
        return employeeList;
    }

    public Employee save(Employee employee) {
        employeeList.add(employee);
        return employee;
    }

    public Employee findById(int employeeId) {
        return employeeList.stream().filter(employee ->
                employee.getId() == employeeId)
                .findFirst()
                .orElse(null);
    }

    public Employee updateById(int employeeID, Employee updatedEmployee) {
        employeeList.stream().filter(employee ->
                employee.getId() == employeeID
        ).findFirst().ifPresent(employee -> {
            employeeList.remove(employee);
            employeeList.add(updatedEmployee);
                }
        );
        return updatedEmployee;
    }
}
