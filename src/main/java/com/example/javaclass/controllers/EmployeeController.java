package com.example.javaclass.controllers;

import com.example.javaclass.dto.EmployeeDto;
import com.example.javaclass.services.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployee() {
        List<EmployeeDto> employeeList = employeeService.getAllEmployee();
        return ResponseEntity.ok(employeeList);
    }

    @PostMapping("/employees/bulk")
    public ResponseEntity<Object> bulkCreateEmployee(
            @RequestBody @NotEmpty(message = "Employee list cannot be empty.") List<@Valid EmployeeDto> employees) {
        employeeService.saveBulk(employees);
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/employees")
    public ResponseEntity<Object> createEmployee(
            @RequestBody @Valid EmployeeDto employeeDto) {
        employeeDto.setId(null);
        EmployeeDto responseEmployee = employeeService.save(employeeDto);
        return ResponseEntity.ok(responseEmployee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Object> updateEmployee(
            @PathVariable String id,
            @RequestBody @Valid EmployeeDto employeeDto) {
        EmployeeDto responseEmployee = employeeService.save(employeeDto);
        return ResponseEntity.ok(responseEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<String> deleteEmployee(
            @PathVariable String id) {
        employeeService.deleteEmployee(EmployeeDto.builder().id(id).build());
        return ResponseEntity.ok("ok");
    }
}
