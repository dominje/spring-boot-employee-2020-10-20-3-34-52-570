package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public List<Company> getAllCompanies() {
        return companyService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company createCompany(@RequestBody Company company) {
        return companyService.create(company);
    }

//    @GetMapping("/{companyId}")
//    public Company getCompanyById(@PathVariable int companyId) {
//        return companyService.getCompanyById(companyId);
//    }
//
//    @GetMapping("/{companyId}/employees")
//    public List<Employee> getEmployeesByCompanyId(@PathVariable int companyId) {
//        return companyService.getEmployeesByCompanyId(companyId);
//    }
//
//    @PutMapping("/{companyId}")
//    public Company updateCompanyByCompanyId(@PathVariable int companyId, @RequestBody Company updatedCompany) {
//        return companyService.updateByCompanyId(companyId, updatedCompany);
//    }
//
//    @DeleteMapping("/{companyId}")
//    public void deleteEmployeesByCompanyId(@PathVariable int companyId){
//        companyService.deleteEmployeesByCompanyId(companyId);
//    }
//
//    @GetMapping(params = {"page","pageSize"})
//    public List<Company> getCompanyWithPagination(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize){
//        return companyService.setPagination(page, pageSize);
//    }


}
