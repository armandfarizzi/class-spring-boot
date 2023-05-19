package com.example.javaclass.controllers;


import com.example.javaclass.dto.EmployeeDto;
import com.example.javaclass.dto.mappers.EmployeeMapper;
import com.example.javaclass.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    private EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployee() {
        List<EmployeeDto> employeeList =  employeeService.getAllEmployee();
        return ResponseEntity.ok(employeeList);
    }


    @PostMapping("/employees")
    public ResponseEntity<Object> createEmployee(
            @RequestBody
            @Valid
            EmployeeDto employeeDto
    ) {
        employeeService.save(employeeDto);
        return ResponseEntity.ok("ok");
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Object> updateEmployee(
            @PathVariable
            String id,

            @RequestBody
            EmployeeDto
            employeeDto
    ) {
        employeeService.save(employeeDto);
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<String> deleteEmployee(
            @PathVariable
            String id
    ) {
        employeeService.deleteEmployee(EmployeeDto.builder().id(id).build());
        return ResponseEntity.ok("ok");
    }
}
