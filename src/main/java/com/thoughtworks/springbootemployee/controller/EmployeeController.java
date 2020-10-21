package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final List<Employee> employees = new ArrayList<>();

    public EmployeeController() {
        employees.add(new Employee(1,"tom", 21,"Male",8000));
        employees.add(new Employee(2,"jerry", 22,"Female",7000));
        employees.add(new Employee(3,"Shrek", 25,"Male",7000));
        employees.add(new Employee(4,"Fiona", 23,"Female",7000));
        employees.add(new Employee(5,"Donkey", 14,"Male",7000));
    }

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

    @DeleteMapping("/{employeeID}")
    public void deleteEmployeeById(@PathVariable int employeeID){
        employees.stream().filter( employee ->
                employee.getId() == employeeID)
                .findFirst().ifPresent(employees::remove);
    }

    @GetMapping(params = "gender")
    public List<Employee> getEmployeesByGender(@RequestParam String gender){
        return employees.stream()
                .filter(employee ->
                        employee.getGender().equalsIgnoreCase(gender))
                .collect(Collectors.toList());
    }

    @GetMapping(params = {"page","pageSize"})
    public List<Employee> getEmployees(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize){
        return employees.stream().sorted(Comparator.comparing(Employee::getId)).skip(page).limit(pageSize).collect(Collectors.toList());
    }


}
