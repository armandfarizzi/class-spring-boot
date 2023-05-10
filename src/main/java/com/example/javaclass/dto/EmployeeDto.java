package com.example.javaclass.dto;


import com.example.javaclass.dto.validation.ValueOfEnum;
import com.example.javaclass.entity.EmployeeRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeDto {
    private String id;
    @NotBlank(message = "name can not be empty")
    private String name;
    @NotBlank(message = "email can not be empty")
    private String email;
    @NotBlank(message = "role can not be empty")
    @ValueOfEnum(enumClass= EmployeeRole.class,
            message = "role must be one " +
                    "of 'DIRECTOR', 'MANAGER', "+
                    "'SENIOR_STAFF', 'JUNIOR_STAFF'")
    private String role;

    @JsonProperty("department_id")
    @NotBlank(message = "department_id can not be empty")
    private String departmentId;

    public String getDepartmentId(){
        if (departmentId != null) {
            return departmentId;
        }
        if (department != null) {
            return department.getId();
        }
        return null;
    }

    public void setDepartmentId(String id){
        this.departmentId = id;
    }

    @JsonIgnore
    private DepartmentDto department;
}
