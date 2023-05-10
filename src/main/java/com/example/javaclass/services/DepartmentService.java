package com.example.javaclass.services;

import com.example.javaclass.entity.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    Optional<Department> getDepartmentByIdWithEmployee(String id);

    Optional<Department> getDepartmentById(String id);
    List<Department> getAllDepartment();
    void save(Department department);
    void update(Department department);
    void deleteDepartment(Department department);
}
