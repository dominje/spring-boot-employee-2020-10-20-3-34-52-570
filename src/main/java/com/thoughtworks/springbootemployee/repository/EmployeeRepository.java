package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private List<Employee> employeeList = new ArrayList<>();

    public EmployeeRepository() {
        employeeList.add(new Employee(1, "Tom", 18, "Male", 1000, 1));
        employeeList.add(new Employee(2, "Jerry", 18, "Male", 1000, 1));
        employeeList.add(new Employee(3, "Pooh", 18, "Male", 1000, 3));
        employeeList.add(new Employee(4, "JJ", 18, "Male", 1000, 2));
        employeeList.add(new Employee(5, "Yoyo", 18, "Female", 1000, 2));
    }

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

    public void deleteById(int employeeID) {
        employeeList.stream().filter(employee ->
                employee.getId() == employeeID)
                .findFirst().ifPresent(employeeList::remove);
    }

    public List<Employee> findByGender(String gender) {
        return employeeList.stream()
                .filter(employee ->
                        employee.getGender().equalsIgnoreCase(gender))
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployeesWithPagination(int page, int pageSize) {
        return employeeList.stream().sorted(Comparator.comparing(Employee::getId))
                .skip(page)
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}
