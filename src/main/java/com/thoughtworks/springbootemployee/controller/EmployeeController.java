package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        return service.create(employee);
    }

    @GetMapping("/{employeeID}")
    public Employee getEmployeeById(@PathVariable int employeeID) {
        return service.findById(employeeID);
    }

    @PutMapping("/{employeeID}")
    public Employee updateEmployeeById(@PathVariable int employeeID, @RequestBody Employee updatedEmployee) {
        return service.updateById(employeeID, updatedEmployee);
    }

    @DeleteMapping("/{employeeID}")
    public void deleteEmployeeById(@PathVariable int employeeID){
        service.deleteById(employeeID);
    }
//
//    @GetMapping(params = "gender")
//    public List<Employee> getEmployeesByGender(@RequestParam String gender){
//        return employees.stream()
//                .filter(employee ->
//                        employee.getGender().equalsIgnoreCase(gender))
//                .collect(Collectors.toList());
//    }
//
//    @GetMapping(params = {"page","pageSize"})
//    public List<Employee> getEmployees(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize){
//        return employees.stream().sorted(Comparator.comparing(Employee::getId)).skip(page).limit(pageSize).collect(Collectors.toList());
//    }
//


}
