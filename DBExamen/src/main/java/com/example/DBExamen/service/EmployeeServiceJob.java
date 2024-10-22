package com.example.DBExamen.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DBExamen.dto.JobEmployeesResponse;
import com.example.DBExamen.dto.EmployeeDTO;

@Service
public class EmployeeServiceJob {

    @Autowired
    private DataSource dataSource;

    public JobEmployeesResponse getEmployeesByJob(Long jobId) throws SQLException {
        validateJobId(jobId);

        JobEmployeesResponse response = new JobEmployeesResponse();
        List<EmployeeDTO> employees = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            CallableStatement stmt = connection.prepareCall("{? = call FN_GET_EMPLOYEES_BY_JOB(?)}");
            stmt.registerOutParameter(1, Types.REF_CURSOR); 
            stmt.setLong(2, jobId);
            stmt.execute();

            ResultSet rs = (ResultSet) stmt.getObject(1);

            while (rs.next()) {
                EmployeeDTO employee = new EmployeeDTO();
                employee.setName(rs.getString("name"));
                employee.setLastName(rs.getString("last_name"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate birthdate = LocalDate.parse("01-02-1978", formatter);
                employee.setBirthdate(birthdate);
                
                employees.add(employee);
            }

            response.setEmployees(employees);
            response.setSuccess(true);
        } catch (SQLException e) {
            response.setSuccess(false);
            throw new RuntimeException("Error al obtener los empleados por puesto", e);
        }

        return response;
    }

    private void validateJobId(Long jobId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM JOBS WHERE ID = ?";
        try (Connection connection = dataSource.getConnection();
             CallableStatement stmt = connection.prepareCall("{? = call FN_CHECK_JOB_ID(?)}")) {
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setLong(2, jobId);
            stmt.execute();

            if (stmt.getInt(1) == 0) {
                throw new IllegalArgumentException("El puesto no existe");
            }
        }
    }
}
