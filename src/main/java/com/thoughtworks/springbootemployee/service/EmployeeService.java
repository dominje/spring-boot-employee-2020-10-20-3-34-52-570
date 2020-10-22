package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.isNull;

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
        return repository.findAllById(employeeId);
    }

    public Employee updateById(int id, Employee employee) {
        Optional<Employee> updatedEmployee = repository.findById(id);
        if(updatedEmployee.isPresent()){
            updatedEmployee.get().setAge(employee.getAge());
            updatedEmployee.get().setCompany_id(employee.getCompany_id());
            updatedEmployee.get().setGender(employee.getGender());
            updatedEmployee.get().setSalary(employee.getSalary());
            updatedEmployee.get().setName(employee.getName());
            return repository.save(updatedEmployee.get());
        }
        return null;
    }

    public void deleteById(int employeeId) {
         repository.deleteById(employeeId);
    }

    public List<Employee> findByGender(String gender) {
        return repository.findByGender(gender);
    }
//
//    public List<Employee> setPagination(int page, int pageSize) {
//        return repository.getEmployeesWithPagination(page, pageSize);
//    }
}
