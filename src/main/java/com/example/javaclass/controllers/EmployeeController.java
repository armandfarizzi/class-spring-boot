package com.example.javaclass.controllers;


import com.example.javaclass.dto.EmployeeDto;
import com.example.javaclass.dto.mappers.EmployeeMapper;
import com.example.javaclass.entity.Department;
import com.example.javaclass.entity.Employee;
import com.example.javaclass.services.DepartmentService;
import com.example.javaclass.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    private EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployee() {
        List<Employee> employeeList =  employeeService.getAllEmployee();
        List<EmployeeDto> employeeDtoList = employeeList.stream().map(employee ->  employeeMapper.toEmployeeDto(employee)).collect(Collectors.toList());
        return ResponseEntity.ok(employeeDtoList);
    }


    @PostMapping("/employees")
    public ResponseEntity<Object> createEmployee(
            @RequestBody
            @Valid
            EmployeeDto employeeDto
    ) {
        Optional<Department> department = departmentService.getDepartmentById(employeeDto.getDepartmentId());
        if (department.isEmpty()) {
            Map<String, Object> response = new HashMap<String, Object>() {{
                put("message", "department is not found");
                put("error", "Bad request");
                put("status", 400);
            }};

            return ResponseEntity.badRequest().body(response);
        }

        Employee em = employeeMapper.toEmployee(employeeDto);
        em.setDepartment(department.get());
        employeeService.save(em);
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
        Optional<Department> department = departmentService.getDepartmentById(employeeDto.getDepartmentId());
        if (department.isEmpty()) {
            Map<String, String> response = new HashMap<String, String>() {{
                put("error", "department_id is not valid");
            }};

            return ResponseEntity.badRequest().body(response);
        }

        Employee employee = employeeMapper.toEmployee(employeeDto);
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
