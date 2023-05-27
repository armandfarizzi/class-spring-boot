package com.example.javaclass.services;

import com.example.javaclass.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto save(EmployeeDto employee);

    List<EmployeeDto> getAllEmployee();

    void deleteEmployee(EmployeeDto employee);

    void saveBulk(List<EmployeeDto> employees);
}
