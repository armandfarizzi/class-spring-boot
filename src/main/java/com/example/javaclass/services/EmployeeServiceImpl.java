package com.example.javaclass.services;

import com.example.javaclass.entity.Employee;
import com.example.javaclass.repositories.EmployeeRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {


    private EmployeeRepositories employeeRepositories;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepositories employeeRepositories) {
        this.employeeRepositories = employeeRepositories;
    }


    @Override
    public void save(Employee employee) {
        employeeRepositories.save(employee);
    }

    @Override
    public void update(Employee employee) {
        employeeRepositories.save(employee);
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepositories.findAll();
    }

    @Override
    public void deleteEmployee(Employee employee) {
        employeeRepositories.delete(employee);
    }
}
