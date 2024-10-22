package com.example.DBExamen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.DBExamen.dto.JobEmployeesResponse;
import com.example.DBExamen.service.EmployeeServiceJob;

@RestController
@RequestMapping("/api/employees")
public class EmployeeControllerJob {

    @Autowired
    private EmployeeServiceJob employeeService;

    @PostMapping("/byJob")
    public ResponseEntity<JobEmployeesResponse> getEmployeesByJob(@RequestBody Long jobId) {
        JobEmployeesResponse response;
        try {
            response = employeeService.getEmployeesByJob(jobId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new JobEmployeesResponse());
        }
    }
}

