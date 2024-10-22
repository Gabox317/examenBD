package com.example.DBExamen.controller;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.DBExamen.dto.WorkedHoursDTO;
import com.example.DBExamen.service.WorkedHoursService;

@RestController
@RequestMapping("/api/hours")
public class WorkedHoursController {

    private  WorkedHoursService workedHoursService;

    public WorkedHoursController(WorkedHoursService workedHoursService) {
        this.workedHoursService = workedHoursService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> agregarHoras(@RequestBody WorkedHoursDTO workedHoursDTO) throws SQLException {
        String mensaje = workedHoursService.addWorkedHours(workedHoursDTO);
        return ResponseEntity.ok(mensaje);
    }
}
