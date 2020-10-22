package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository <Employee, Integer>{

    List<Employee> findAll();
    Employee save(Employee employee);
    void deleteById(int employeeID);
    Employee findAllById(int employeeID);

    @Query("select new Employee(id, name, age, gender, salary, company_id) from Employee where gender=:gender")
    List<Employee> findByGender(@Param("gender") String gender);
}
