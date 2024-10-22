package repository;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeePaymentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean employeeExists(int employeeId) {
        // Consulta para verificar si el empleado existe
        String sql = "SELECT COUNT(*) FROM EMPLOYEES WHERE ID = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{employeeId}, Integer.class);
        return count != null && count > 0;
    }

    public double getTotalPaymentByDateRange(int employeeId, LocalDate startDate, LocalDate endDate) {
        // Consulta para obtener el total pagado sumando las horas trabajadas por la tarifa por hora
        String sql = "SELECT SUM(EWH.WORKED_HOURS * E.SALARY_PER_HOUR) " +
                     "FROM EMPLOYEE_WORKED_HOURS EWH " +
                     "JOIN EMPLOYEES E ON E.ID = EWH.EMPLOYEE_ID " +
                     "WHERE EWH.EMPLOYEE_ID = ? " +
                     "AND EWH.WORKED_DATE BETWEEN ? AND ?";
        
        Double totalPayment = jdbcTemplate.queryForObject(
            sql,
            new Object[]{employeeId, startDate, endDate},
            Double.class
        );

        return totalPayment != null ? totalPayment : 0.0;
    }
}
