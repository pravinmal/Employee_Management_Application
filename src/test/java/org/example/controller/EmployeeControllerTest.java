package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Employee;
import org.example.service.EmployeeService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    // Test Get All Employees
    @Test
    void testGetAllEmployees() throws Exception {

        Employee employee = new Employee();

        employee.setId(1L);
        employee.setFirstName("Pravin");
        employee.setLastName("Mali");
        employee.setEmailId("pravin@gmail.com");

        when(employeeService.getAllEmployees())
                .thenReturn(Arrays.asList(employee));

        mockMvc.perform(
                        get("/api/v1/employees/employeelist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName")
                        .value("Pravin"))
                .andExpect(jsonPath("$[0].lastName")
                        .value("Mali"));
    }

    // Test Create Employee
    @Test
    void testCreateEmployee() throws Exception {

        Employee employee = new Employee();

        employee.setId(1L);
        employee.setFirstName("Pravin");
        employee.setLastName("Mali");
        employee.setEmailId("pravin@gmail.com");

        when(employeeService.createEmployee(
                any(Employee.class)))
                .thenReturn(employee);

        mockMvc.perform(
                        post("/api/v1/employees/addnewemployee")
                                .contentType(
                                        MediaType.APPLICATION_JSON)
                                .content(objectMapper
                                        .writeValueAsString(employee)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName")
                        .value("Pravin"))
                .andExpect(jsonPath("$.emailId")
                        .value("pravin@gmail.com"));
    }

    // Test Get Employee By Id
    @Test
    void testGetEmployeeById() throws Exception {

        Employee employee = new Employee();

        employee.setId(1L);
        employee.setFirstName("Pravin");
        employee.setLastName("Mali");
        employee.setEmailId("pravin@gmail.com");

        when(employeeService.getEmployeeById(1L))
                .thenReturn(employee);

        mockMvc.perform(
                        get("/api/v1/employees/1"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName")
                        .value("Pravin"));
    }

    // Test Update Employee
    @Test
    void testUpdateEmployee() throws Exception {

        Employee employee = new Employee();

        employee.setId(1L);
        employee.setFirstName("Pravin");
        employee.setLastName("Mali");
        employee.setEmailId("pravin@gmail.com");

        when(employeeService.updateEmployee(
                any(Long.class),
                any(Employee.class)))
                .thenReturn(employee);

        mockMvc.perform(
                        put("/api/v1/employees/1")
                                .contentType(
                                        MediaType.APPLICATION_JSON)

                                .content(objectMapper
                                        .writeValueAsString(employee)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName")
                        .value("Pravin"));
    }

    // Test Delete Employee
    @Test
    void testDeleteEmployee() throws Exception {

        doNothing().when(employeeService)
                .deleteEmployee(1L);

        mockMvc.perform(
                        delete("/api/v1/employees/1"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deleted")
                        .value(true));
    }
}