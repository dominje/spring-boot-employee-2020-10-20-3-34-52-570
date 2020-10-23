package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Optional<Employee> findById(int employeeId) {
        Optional<Employee> employee = repository.findById(employeeId);
        if(employee.isPresent()){
            return repository.findById(employee.get().getId());
        }
        return null;
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

    public List<Employee> getEmployeesWithPagination(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return repository.findAll(pageable).toList();
    }
}
