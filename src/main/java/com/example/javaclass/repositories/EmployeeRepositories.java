package com.example.javaclass.repositories;

import com.example.javaclass.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepositories extends JpaRepository<Employee, String> {
}
