package com.example.DBExamen.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.DBExamen.dto.EmployeeDTO;
import com.example.DBExamen.dto.EmployeeResponse;
import com.example.DBExamen.service.EmployeeService;

/**
 * Clase encargada de generar el controlador para los servicios
 */

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/salary-range")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesBySalaryRange(
            @RequestParam Double min_salary,
            @RequestParam Double max_salary,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "10") int size) {
        
        List<EmployeeDTO> employees = employeeService.getEmployeesBySalaryRange(min_salary, max_salary, order, size);
        
        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(employees);
    }
    
    @PostMapping
    public ResponseEntity<EmployeeResponse> addEmployee(@RequestBody EmployeeDTO employeeDTO) throws SQLException {
        try {
            EmployeeResponse response = employeeService.createEmployee(employeeDTO);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new EmployeeResponse(null, false));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new EmployeeResponse(null, false));
        }
    }
}
