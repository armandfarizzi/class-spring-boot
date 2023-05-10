package com.example.javaclass.services;

import com.example.javaclass.entity.Employee;
import com.example.javaclass.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {


    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void update(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public void deleteEmployee(Employee employee) {
        employeeRepository.delete(employee);
    }
}
