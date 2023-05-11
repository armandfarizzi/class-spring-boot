package com.example.javaclass.controllers;


import com.example.javaclass.dto.DepartmentDto;
import com.example.javaclass.dto.DepartmentWithEmployeeDto;
import com.example.javaclass.exception.CustomException;
import com.example.javaclass.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("")
    public ResponseEntity<List<DepartmentDto>> getAllDepartment() {
        List<DepartmentDto> departmentDtoList = departmentService.getAllDepartment();
        return ResponseEntity.ok(departmentDtoList);
    }

    @PostMapping("")
    public ResponseEntity<String> createDepartment(
            @Valid @RequestBody
            DepartmentDto departmentDto
    ){
        departmentService.save(departmentDto);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDepartmentEmployee(
            @PathVariable
            String id
    ){
        Optional<DepartmentWithEmployeeDto> departmentWithEmployeeDto = departmentService.getDepartmentByIdWithEmployee(id);
        if (departmentWithEmployeeDto.isEmpty()) {
            throw new CustomException(400, "bad request", "department is not found");
        }

        return ResponseEntity.ok(departmentWithEmployeeDto.get());
    }
}
