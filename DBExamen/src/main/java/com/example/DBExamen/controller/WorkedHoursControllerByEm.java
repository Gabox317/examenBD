package com.example.DBExamen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.DBExamen.dto.WorkedHoursByDateRangeRequestDTO;
import com.example.DBExamen.dto.WorkedHoursByDateRangeResponseDTO;
import com.example.DBExamen.service.WorkedHoursServiceByEm;

@RestController
public class WorkedHoursControllerByEm {

    @Autowired
    private WorkedHoursServiceByEm workedHoursService;

    @PostMapping("/workedHoursByDateRange")
    public ResponseEntity<WorkedHoursByDateRangeResponseDTO> getTotalWorkedHoursByDateRange(@RequestBody WorkedHoursByDateRangeRequestDTO requestDTO) {
        WorkedHoursByDateRangeResponseDTO response = workedHoursService.getTotalWorkedHoursByDateRange(requestDTO);
        return ResponseEntity.ok(response);
    }
}
