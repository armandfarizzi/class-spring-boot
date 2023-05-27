package com.example.javaclass.controllers;

import com.example.javaclass.dto.DepartmentDto;
import com.example.javaclass.dto.EmployeeDto;
import com.example.javaclass.entity.EmployeeRole;
import com.example.javaclass.services.DepartmentService;
import com.example.javaclass.services.EmployeeService;
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

@WebMvcTest(controllers = { EmployeeController.class })
class EmployeeControllerTest {

        @Autowired
        private MockMvc mvc;

        @MockBean
        private EmployeeService employeeService;

        @MockBean
        private DepartmentService departmentService;

        @InjectMocks
        private EmployeeController employeeController;

        @Test
        void getAllEmployee() throws Exception {
                List<EmployeeDto> oneEmployee = new ArrayList<EmployeeDto>() {
                        {
                                add(EmployeeDto.builder().name("test")
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
                DepartmentDto oneDepartment = DepartmentDto.builder()
                                .id("#department-id")
                                .build();

                EmployeeDto oneEmployee = EmployeeDto.builder().name("test")
                                .email("test@mail.com")
                                .role(String.valueOf(EmployeeRole.DIRECTOR))
                                .id("testID")
                                .department(oneDepartment)
                                .build();

                when(departmentService.getDepartmentById("#department-id")).thenReturn(Optional.of(oneDepartment));

                RequestBuilder doRequest = MockMvcRequestBuilders
                                .post("/api/v1/employees")
                                .content(HelperTest.asJsonString(oneEmployee))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON);

                mvc.perform(doRequest)
                                .andExpect(status().isOk());
        }

        @Test
        void createEmployeeValidationFailed() throws Exception {
                DepartmentDto oneDepartment = DepartmentDto.builder()
                                .id("#department-id")
                                .build();

                EmployeeDto oneEmployee = EmployeeDto.builder()
                                .email("test@mail.com")
                                .role(String.valueOf(EmployeeRole.DIRECTOR))
                                .id("testID")
                                .department(oneDepartment)
                                .build();

                when(departmentService.getDepartmentById("#department-id")).thenReturn(Optional.of(oneDepartment));

                RequestBuilder doRequest = MockMvcRequestBuilders
                                .post("/api/v1/employees")
                                .content(HelperTest.asJsonString(oneEmployee))
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
                DepartmentDto oneDepartment = DepartmentDto.builder()
                                .id("department-id")
                                .build();

                EmployeeDto oneEmployee = EmployeeDto.builder().name("test")
                                .email("test@mail.com")
                                .role(String.valueOf(EmployeeRole.DIRECTOR))
                                .id("testID")
                                .department(oneDepartment)
                                .build();

                when(departmentService.getDepartmentById("department-id")).thenReturn(Optional.of(oneDepartment));

                RequestBuilder doRequest = MockMvcRequestBuilders
                                .put("/api/v1/employees/department-id")
                                .content(HelperTest.asJsonString(oneEmployee))
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