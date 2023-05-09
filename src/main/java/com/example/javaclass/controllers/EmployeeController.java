package com.example.javaclass.controllers;


import com.example.javaclass.dto.EmployeeDto;
import com.example.javaclass.dto.mappers.EmployeeMapper;
import com.example.javaclass.entity.Employee;
import com.example.javaclass.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    private EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployee() {
        List<Employee> employeeList =  employeeService.getAllEmployee();
        List<EmployeeDto> employeeDtoList = employeeList.stream().map(employee ->  employeeMapper.employeeToEmployeeDto(employee)).collect(Collectors.toList());
        return ResponseEntity.ok(employeeDtoList);
    }


    @PostMapping("/employees")
    public ResponseEntity<String> createEmployee(
            @RequestBody
            @Valid
            EmployeeDto employeeDto
    ) {
        Employee em = employeeMapper.employeeDtoToEmployee(employeeDto);
        employeeService.save(em);
        return ResponseEntity.ok("ok");
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<String> updateEmployee(
            @PathVariable
            String id,

            @RequestBody
            EmployeeDto
            employeeDto
    ) {
        Employee employee = employeeMapper.employeeDtoToEmployee(employeeDto);
        employee.setId(id);
        employeeService.update(employee);
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<String> deleteEmployee(
            @PathVariable
            String id
    ) {
        employeeService.deleteEmployee(Employee.builder().id(id).build());
        return ResponseEntity.ok("ok");
    }

}
