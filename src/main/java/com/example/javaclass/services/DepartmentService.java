package com.example.javaclass.services;

import com.example.javaclass.dto.DepartmentDto;
import com.example.javaclass.dto.DepartmentWithEmployeeDto;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    Optional<DepartmentWithEmployeeDto> getDepartmentByIdWithEmployee(String id);

    Optional<DepartmentDto> getDepartmentById(String id);
    List<DepartmentDto> getAllDepartment();
    void save(DepartmentDto DepartmentDto);
    void deleteDepartment(DepartmentDto department);
}
