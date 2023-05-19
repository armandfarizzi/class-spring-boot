package com.example.javaclass.controllers;

import com.example.javaclass.dto.DepartmentDto;
import com.example.javaclass.dto.DepartmentWithEmployeeDto;
import com.example.javaclass.dto.EmployeeDto;
import com.example.javaclass.entity.EmployeeRole;
import com.example.javaclass.services.DepartmentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = { DepartmentController.class })
class DepartmentControllerTest {
        @Autowired
        private MockMvc mvc;

        @MockBean
        private DepartmentService departmentService;

        @InjectMocks
        private DepartmentController departmentController;

        @Test
        void getAllDepartment() throws Exception {
                List<DepartmentDto> departmentDtoList = new ArrayList<>() {
                        {
                                add(DepartmentDto
                                                .builder()
                                                .departmentName("sales team")
                                                .budget(50L)
                                                .id("jakarta-id-sale")
                                                .location("Jakarta")
                                                .build());
                        }
                };
                when(departmentService.getAllDepartment()).thenReturn(departmentDtoList);
                RequestBuilder doRequest = MockMvcRequestBuilders.get("/api/v1/department");
                mvc.perform(doRequest)
                                .andExpect(MockMvcResultMatchers.jsonPath("$[0].department_name").value("sales team"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$[0].budget").value(50))
                                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("jakarta-id-sale"))
                                .andExpect(status().isOk());
        }

        @Test
        void createDepartment() throws Exception {
                DepartmentDto departmentDto = DepartmentDto
                                .builder()
                                .departmentName("sales team")
                                .budget(50L)
                                .id("jakarta-id-sale")
                                .location("Jakarta")
                                .build();

                RequestBuilder postRequest = HelperTest.createPostWithURLAndBody("/api/v1/department", departmentDto);
                mvc.perform(postRequest)
                                .andExpect(status().isOk());
                verify(departmentService, times(1)).save(departmentDto);
        }

        @Test
        void getDepartmentEmployee() throws Exception {
                DepartmentDto oneDepartment = DepartmentDto
                                .builder()
                                .departmentName("sales team")
                                .budget(50L)
                                .id("jakarta-id-sale")
                                .location("Jakarta")
                                .build();

                EmployeeDto oneEmployee = EmployeeDto.builder()
                                .email("test@mail.com")
                                .role(String.valueOf(EmployeeRole.DIRECTOR))
                                .id("testID")
                                .name("testname")
                                .department(oneDepartment)
                                .build();

                DepartmentWithEmployeeDto departmentWithEmployeeDto = DepartmentWithEmployeeDto
                                .builder()
                                .departmentName("sales team")
                                .budget(50L)
                                .id("jakarta-id-sale")
                                .location("Jakarta")
                                .employeeList(List.of(oneEmployee))
                                .build();

                when(departmentService.getDepartmentByIdWithEmployee("jakarta-id-sale"))
                                .thenReturn(Optional.of(departmentWithEmployeeDto));
                RequestBuilder postRequest = HelperTest.createGetRequest("/api/v1/department/jakarta-id-sale");
                mvc.perform(postRequest)
                                .andExpect(jsonPath("$.employees[0].name").value("testname"))
                                .andExpect(status().isOk());
                verify(departmentService, times(1)).getDepartmentByIdWithEmployee("jakarta-id-sale");
        }
}