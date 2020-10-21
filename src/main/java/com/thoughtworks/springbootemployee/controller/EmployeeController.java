package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final List<Employee> employees = new ArrayList<>();

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employees;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        employees.add(employee);
        return employee;
    }

    @GetMapping("/{employeeID}")
    public Employee getEmployeeById(@PathVariable int employeeID) {
        return employees.stream()
                .filter(employee ->
                        employee.getId() == employeeID
                ).findFirst().orElse(null);
    }

    @PutMapping("/{employeeID}")
    public Employee updateEmployeeById(@PathVariable int employeeID, @RequestBody Employee updatedEmployee) {
        employees.stream().filter(employee ->
                employee.getId() == employeeID
        ).findFirst().ifPresent(employee -> {
                    employees.remove(employee);
                    employees.add(updatedEmployee);
                }
        );
        return updatedEmployee;
    }

}
