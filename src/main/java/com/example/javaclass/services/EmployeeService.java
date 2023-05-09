package com.example.javaclass.services;

import com.example.javaclass.entity.Employee;

import java.util.List;

public interface EmployeeService {
    void save(Employee employee);
    void update(Employee employee);

    List<Employee> getAllEmployee();
    void deleteEmployee(Employee employee);
}
