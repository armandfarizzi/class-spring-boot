package com.example.javaclass.services;

import com.example.javaclass.dto.DepartmentDto;
import com.example.javaclass.dto.DepartmentWithEmployeeDto;
import com.example.javaclass.dto.mappers.DepartmentMapper;
import com.example.javaclass.entity.Department;
import com.example.javaclass.repositories.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements  DepartmentService{

    @Autowired
    private DepartmentRepository departmentRepository;
    private DepartmentMapper departmentMapper = DepartmentMapper.INSTANCE;

    @Override
    public Optional<DepartmentWithEmployeeDto> getDepartmentByIdWithEmployee(String id) {
        var department = departmentRepository.findEagerById(id);
        if (department.isEmpty()) {
            return Optional.empty();
        }

        var departmentWithEmployeeDto = departmentMapper.toDepartmentWithEmployeeDto(department.get());
        return Optional.of(departmentWithEmployeeDto);
    }

    @Override
    public Optional<DepartmentDto> getDepartmentById(String id) {
        var department = departmentRepository.findById(id);
        if (department.isEmpty()) {
            return Optional.empty();
        }

        var departMentDto = departmentMapper.toDepartmentDto(department.get());
        return Optional.of(departMentDto);
    }

    @Override
    public List<DepartmentDto> getAllDepartment() {
        var departmentList =  departmentRepository.findAll();
        List<DepartmentDto> departmentDtoList = departmentList.stream().map(department -> departmentMapper.toDepartmentDto(department)).collect(Collectors.toList());
        return departmentDtoList;
    }

    @Override
    @Transactional
    public void save(DepartmentDto departmentDto) {
        Department department = departmentMapper.toDepartment(departmentDto);
        departmentRepository.save(department);
    }

    @Override
    @Transactional
    public void deleteDepartment(DepartmentDto departmentDto) {
        Department department = departmentMapper.toDepartment(departmentDto);
        departmentRepository.delete(department);
    }


}
