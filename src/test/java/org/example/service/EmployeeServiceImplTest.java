package org.example.service;

import org.example.entity.Employee;
import org.example.exception.ResourceNotFoundException;
import org.example.repository.EmployeeRepository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {

        employee = new Employee();

        employee.setId(1L);
        employee.setFirstName("Pravin");
        employee.setLastName("Mali");
        employee.setEmailId("pravin@gmail.com");
    }

    @Test
    void testGetAllEmployees() {

        when(employeeRepository.findAll())
                .thenReturn(Arrays.asList(employee));

        List<Employee> employeeList =
                employeeService.getAllEmployees();

        assertEquals(1, employeeList.size());
    }

    @Test
    void testCreateEmployee() {

        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(employee);

        Employee savedEmployee =
                employeeService.createEmployee(employee);

        assertNotNull(savedEmployee);

        assertEquals("Pravin",
                savedEmployee.getFirstName());
    }

    @Test
    void testGetEmployeeById() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        Employee foundEmployee =
                employeeService.getEmployeeById(1L);

        assertEquals("Pravin",
                foundEmployee.getFirstName());
    }

    @Test
    void testGetEmployeeById_NotFound() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> employeeService.getEmployeeById(1L)
        );
    }

    @Test
    void testDeleteEmployee() {

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(1L);

        verify(employeeRepository,
                times(1)).delete(employee);
    }
}