package com.example.javaclass.controllers;


import com.example.javaclass.dto.DepartmentDto;
import com.example.javaclass.dto.mappers.DepartmentMapper;
import com.example.javaclass.entity.Department;
import com.example.javaclass.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    private DepartmentMapper departmentMapper = DepartmentMapper.INSTANCE;

    @GetMapping("")
    public ResponseEntity<List<DepartmentDto>> getAllDepartment() {
        List<Department> departmentList = departmentService.getAllDepartment();
        List<DepartmentDto> departmentDtoList = departmentList.stream().map(department -> departmentMapper.toDepartmentDto(department)).collect(Collectors.toList());
        return ResponseEntity.ok(departmentDtoList);
    }

    @PostMapping("")
    public ResponseEntity<String> createDepartment(
            @Valid
            @RequestBody
            DepartmentDto
            departmentDto
    ){
        Department department = departmentMapper.toDepartment(departmentDto);
        departmentService.save(department);
        return ResponseEntity.ok("ok");
    }


}
