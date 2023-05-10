package com.example.javaclass.repositories;

import com.example.javaclass.entity.Department;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, String> {
    @EntityGraph(attributePaths = {"employeeList"})
    Optional<Department> findEagerById(String id);
}
