package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {
    public List<Employee> findAll() {
        return null;
    }

    public Employee save(Employee employee) {
        return employee;
    }
}