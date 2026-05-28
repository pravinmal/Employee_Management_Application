package org.example.controller;

import org.example.entity.Employee;
import org.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Get all employees
    @GetMapping("/employeelist")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // Create employee
    @PostMapping("/addnewemployee")
    public Employee createEmployee(
            @RequestBody Employee employee) {

        return employeeService.createEmployee(employee);
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(
            @PathVariable Long id) {

        Employee employee =
                employeeService.getEmployeeById(id);

        return ResponseEntity.ok(employee);
    }

    // Update employee
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee employeeDetails) {

        Employee updatedEmployee =
                employeeService.updateEmployee(
                        id,
                        employeeDetails);

        return ResponseEntity.ok(updatedEmployee);
    }

    // Delete employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(
            @PathVariable Long id) {

        employeeService.deleteEmployee(id);

        Map<String, Boolean> response =
                new HashMap<>();

        response.put("deleted", Boolean.TRUE);

        return ResponseEntity.ok(response);
    }
}