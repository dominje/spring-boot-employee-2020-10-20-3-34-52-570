package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> getAll() {
        return repository.findAll();
    }

    public Employee create(Employee employeeRequest) {
        return repository.save(employeeRequest);
    }

    public Employee findById(int employeeId) {
        return repository.findById(employeeId);
    }

    public Employee updateById(int id, Employee employee) {
        return repository.updateById(id, employee);
    }

    public void deleteById(int employeeId) {
         repository.deleteById(employeeId);
    }

    public List<Employee> findByGender(String gender) {
        return repository.findByGender(gender);
    }

    public List<Employee> setPagination(int page, int pageSize) {
        return repository.getEmployeesWithPagination(page, pageSize);
    }
}
