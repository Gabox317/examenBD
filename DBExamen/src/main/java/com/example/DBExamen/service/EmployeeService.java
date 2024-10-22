package com.example.DBExamen.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DBExamen.dto.EmployeeDTO;
import com.example.DBExamen.dto.EmployeeResponse;

@Service
public class EmployeeService {

    @Autowired
    private DataSource dataSource;

    public EmployeeResponse createEmployee(EmployeeDTO employeeDTO) throws SQLException {
        validateEmployeeData(employeeDTO);

        try (Connection connection = dataSource.getConnection()) {
            CallableStatement stmt = connection.prepareCall("{? = call FNINSERT_EMPLOYEE(?,?,?,?,?)}");
            stmt.registerOutParameter(1, Types.INTEGER); 
            stmt.setString(2, employeeDTO.getName());
            stmt.setString(3, employeeDTO.getLastName());
            stmt.setDate(4, Date.valueOf(employeeDTO.getBirthdate()));
            stmt.setLong(5, employeeDTO.getGenderId());
            stmt.setLong(6, employeeDTO.getJobId());

            stmt.execute();

            Long insertedId = stmt.getLong(1);

            return insertedId != null 
                ? new EmployeeResponse(insertedId, true)
                : new EmployeeResponse(null, false);
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar el empleado", e);
        }
    }

    private void validateEmployeeData(EmployeeDTO employeeDTO) throws SQLException {
        if (employeeExists(employeeDTO.getName(), employeeDTO.getLastName())) {
            throw new IllegalArgumentException("El empleado ya existe");
        }
        if (!isOfAge(employeeDTO.getBirthdate())) {
            throw new IllegalArgumentException("El empleado debe ser mayor de 18 años");
        }
        if (employeeDTO.getGenderId() == null || !genderExists(employeeDTO.getGenderId())) {
            throw new IllegalArgumentException("El género no existe");
        }
        if (employeeDTO.getJobId() == null || !jobExists(employeeDTO.getJobId())) {
            throw new IllegalArgumentException("El puesto no existe");
        }
    }

    private boolean isOfAge(LocalDate birthdate) {
        return Period.between(birthdate, LocalDate.now()).getYears() >= 18;
    }

    private boolean employeeExists(String name, String lastName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM EMPLOYEES WHERE NAME = ? AND LAST_NAME = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; 
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return false; 
    }

    private boolean genderExists(Long genderId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM GENDERS WHERE ID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, genderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; 
    }

    private boolean jobExists(Long jobId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM JOBS WHERE ID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, jobId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; 
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return false;
    }
    
    public List<EmployeeDTO> getEmployeesBySalaryRange(Double minSalary, Double maxSalary, String order, int size) {
        String orderBy = "ASC";
        if ("desc".equalsIgnoreCase(order)) {
            orderBy = "DESC";
        }

        String query = "SELECT * FROM EMPLOYEES WHERE SALARY BETWEEN ? AND ? ORDER BY SALARY " + orderBy + " LIMIT ?";

        List<EmployeeDTO> employeeList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDouble(1, minSalary);
            preparedStatement.setDouble(2, maxSalary);
            preparedStatement.setInt(3, size);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    EmployeeDTO employee = new EmployeeDTO();
                    employee.setName(resultSet.getString("name")); // Ajusta según el nombre de la columna
                    employeeList.add(employee);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return employeeList;
    }

}
