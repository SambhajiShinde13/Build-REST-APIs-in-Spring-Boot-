package com.EmpDetails.Emp.controller;


import com.EmpDetails.Emp.dto.EmployeeDto;
import com.EmpDetails.Emp.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {


    private  final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(path="{id}")
    public EmployeeDto getEmployeeById(@PathVariable("id") Long id){
        return employeeService.getEmployeeById(id);
    }

    @PostMapping()
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto)
    {
        return employeeService.createEmployee(employeeDto);

    }
    @GetMapping(path = "/allemp")
    public List<EmployeeDto> getAllEmployees(EmployeeService employeeService )
    {
        return employeeService.getAllEmployees();
    }

    @DeleteMapping(path = "/{id}")
    public boolean deleteEmployeeById(@PathVariable Long id){
        return employeeService.deleteEmployeeById(id);

    }




}
