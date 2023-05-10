package com.example.javaclass.dto;


import com.example.javaclass.dto.validation.ValueOfEnum;
import com.example.javaclass.entity.EmployeeRole;
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
            message = "role must be one" +
                    "of \"DIRECTOR\", \"MANAGER\", "+
                    "\"SENIOR_STAFF\", \"JUNIOR_STAFF\"")
    private String role;
}
