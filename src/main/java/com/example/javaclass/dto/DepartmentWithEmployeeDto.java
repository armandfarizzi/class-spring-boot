package com.example.javaclass.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentWithEmployeeDto {
    private String id;
    @NotBlank(message = "department_name can not be empty")
    @JsonProperty("department_name")
    private String departmentName;
    @NotBlank(message = "location can not be empty")
    private String location;
    @Min(value = 1, message = "budget must not be zero or empty")
    private long budget;

    @JsonProperty("employees")
    List<EmployeeDto> employeeList;
}
