package com.example.javaclass.controllers;

import com.example.javaclass.dto.mappers.EmployeeMapper;
import com.example.javaclass.entity.Department;
import com.example.javaclass.entity.Employee;
import com.example.javaclass.entity.EmployeeRole;
import com.example.javaclass.services.DepartmentService;
import com.example.javaclass.services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = {EmployeeController.class})
class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private DepartmentService departmentService;

    @InjectMocks
    private EmployeeController employeeController;

    private EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getAllEmployee() throws Exception {
        List<Employee> oneEmployee = new ArrayList<Employee>(){
            {
                add(Employee.builder().
                        name("test")
                            .email("test@mail.com")
                            .id("testID")
                            .build());
            }
        };

        when(employeeService.getAllEmployee()).thenReturn(oneEmployee);

        RequestBuilder doRequest = MockMvcRequestBuilders.get("/api/v1/employees");
        mvc.perform(doRequest)
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("testID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("test@mail.com"))
                .andExpect(status().isOk());
    }

    @Test
    void createEmployee() throws Exception {
        Department oneDepartment = Department.builder()
                .id("#department-id")
                .build();

        Employee oneEmployee = Employee.builder().
                        name("test")
                        .email("test@mail.com")
                        .role(EmployeeRole.DIRECTOR)
                        .id("testID")
                        .department(oneDepartment)
                        .build();

        when(departmentService.getDepartmentById("#department-id")).thenReturn(Optional.of(oneDepartment));

        RequestBuilder doRequest = MockMvcRequestBuilders
                .post("/api/v1/employees")
                .content(asJsonString(employeeMapper.toEmployeeDto(oneEmployee)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(doRequest)
                .andExpect(status().isOk());
    }

    @Test
    void createEmployeeValidationFailed() throws Exception {
        Department oneDepartment = Department.builder()
                .id("#department-id")
                .build();

        Employee oneEmployee = Employee.builder()
                .email("test@mail.com")
                .role(EmployeeRole.DIRECTOR)
                .id("testID")
                .department(oneDepartment)
                .build();

        when(departmentService.getDepartmentById("#department-id")).thenReturn(Optional.of(oneDepartment));

        RequestBuilder doRequest = MockMvcRequestBuilders
                .post("/api/v1/employees")
                .content(asJsonString(employeeMapper.toEmployeeDto(oneEmployee)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(doRequest)
                .andExpect(jsonPath("$.errors[0]", containsString("name can not be empty")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.message").value("Invalid request"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateEmployee() throws Exception {
        Department oneDepartment = Department.builder()
                .id("department-id")
                .build();

        Employee oneEmployee = Employee.builder().
                name("test")
                .email("test@mail.com")
                .role(EmployeeRole.DIRECTOR)
                .id("testID")
                .department(oneDepartment)
                .build();

        when(departmentService.getDepartmentById("department-id")).thenReturn(Optional.of(oneDepartment));

        RequestBuilder doRequest = MockMvcRequestBuilders
                .put("/api/v1/employees/department-id")
                .content(asJsonString(employeeMapper.toEmployeeDto(oneEmployee)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(doRequest)
                .andExpect(status().isOk());
    }

    @Test
    void deleteEmployee() throws Exception {
        RequestBuilder doRequest = MockMvcRequestBuilders
                .delete("/api/v1/employees/department-id")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(doRequest)
                .andExpect(status().isOk());
    }
}