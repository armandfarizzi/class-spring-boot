package com.example.javaclass.dto.mappers;

import com.example.javaclass.dto.DepartmentDto;
import com.example.javaclass.dto.DepartmentWithEmployeeDto;
import com.example.javaclass.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepartmentMapper {
    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);
    Department toDepartment(DepartmentDto departmentDto);
    DepartmentDto toDepartmentDto(Department department);
    DepartmentWithEmployeeDto toDepartmentWithEmployeeDto(Department department);
}