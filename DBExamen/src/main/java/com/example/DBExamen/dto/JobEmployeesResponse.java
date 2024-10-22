package com.example.DBExamen.dto;

import java.util.List;

public class JobEmployeesResponse {
    private List<EmployeeDTO> employees;
    private boolean success;

    public List<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDTO> employees) {
        this.employees = employees;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
