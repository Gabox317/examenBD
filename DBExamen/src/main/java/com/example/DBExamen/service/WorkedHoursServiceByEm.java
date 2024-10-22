package com.example.DBExamen.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.DBExamen.dto.WorkedHoursByDateRangeRequestDTO;
import com.example.DBExamen.dto.WorkedHoursByDateRangeResponseDTO;

@Service
public class WorkedHoursServiceByEm {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public WorkedHoursByDateRangeResponseDTO getTotalWorkedHoursByDateRange(WorkedHoursByDateRangeRequestDTO request) {


        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }

    
        Double totalWorkedHours = getTotalWorkedHoursByEmployeeAndDateRange(
                ((WorkedHoursByDateRangeRequestDTO) request).getEmployeeId(), request.getStartDate(), request.getEndDate());

        return new WorkedHoursByDateRangeResponseDTO(request.getEmployeeId(), totalWorkedHours);
    }

    private Double getTotalWorkedHoursByEmployeeAndDateRange(Long employeeId, LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT package_name.get_total_worked_hours(?, ?, ?) FROM dual";

        return jdbcTemplate.queryForObject(
                sql, 
                new Object[]{employeeId, startDate, endDate}, 
                Double.class
        );
    }
}
