package com.example.javaclass.services;

import com.example.javaclass.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    void save(EmployeeDto employee);
    List<EmployeeDto> getAllEmployee();
    void deleteEmployee(EmployeeDto employee);
}
