package com.example.DBExamen.dto;

public class WorkedHoursByDateRangeResponseDTO {
    private Long employeeId;
    private Double totalWorkedHours;
	public WorkedHoursByDateRangeResponseDTO(Long employeeId2, Double totalWorkedHours2) {
		// TODO Auto-generated constructor stub
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public Double getTotalWorkedHours() {
		return totalWorkedHours;
	}
	public void setTotalWorkedHours(Double totalWorkedHours) {
		this.totalWorkedHours = totalWorkedHours;
	}


}

