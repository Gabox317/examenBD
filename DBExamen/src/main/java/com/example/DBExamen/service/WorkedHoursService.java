package com.example.DBExamen.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DBExamen.dto.WorkedHoursDTO;

@Service
public class WorkedHoursService {

    @Autowired
    private DataSource dataSource;

    public String addWorkedHours(WorkedHoursDTO workedHoursDTO) throws SQLException {
        validateWorkedHoursData(workedHoursDTO);

        try (Connection connection = dataSource.getConnection()) {
            CallableStatement stmt = connection.prepareCall("{CALL agregar_horas(?, ?, ?)}");
            stmt.setInt(1, workedHoursDTO.getEmployeeId());
            stmt.setDate(2, Date.valueOf(workedHoursDTO.getWorkedDate()));
            stmt.setInt(3, workedHoursDTO.getWorkedHours());

            stmt.execute();

            return "Horas trabajadas agregadas exitosamente.";
        } catch (SQLException e) {
            throw new RuntimeException("Error al agregar horas trabajadas", e);
        }
    }

    private void validateWorkedHoursData(WorkedHoursDTO workedHoursDTO) throws SQLException {
        if (workedHoursDTO.getWorkedHours() < 0) {
            throw new IllegalArgumentException("Las horas trabajadas no pueden ser negativas.");
        }

    }
}
