package com.example.javaclass.services;

import com.example.javaclass.dto.EmployeeDto;
import com.example.javaclass.dto.mappers.EmployeeMapper;
import com.example.javaclass.exception.CustomException;
import com.example.javaclass.repositories.DepartmentRepository;
import com.example.javaclass.repositories.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {


    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;

    private EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }


    @Override
    @Transactional
    public void save(EmployeeDto employeeDto) {
        var departmentId = employeeDto.getDepartmentId();
        var department = departmentRepository.findById(departmentId);
        if (department.isEmpty()) {
            throw new CustomException(404, "department_id is not valid", "department not found");
        }

        var employee = employeeMapper.toEmployee(employeeDto);
        employee.setDepartment(department.get());
        employeeRepository.save(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployee() {
        var employeeList = employeeRepository.findAll();
        List<EmployeeDto> employeeDtoList = employeeList
                .stream()
                .map(employee ->  employeeMapper.toEmployeeDto(employee))
                .collect(Collectors.toList());
        return employeeDtoList;
    }

    @Override
    @Transactional
    public void deleteEmployee(EmployeeDto employeeDto) {
        employeeRepository.delete(employeeMapper.toEmployee(employeeDto));
    }
}
