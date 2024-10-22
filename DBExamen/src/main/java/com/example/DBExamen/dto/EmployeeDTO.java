package com.example.DBExamen.dto;

import java.time.LocalDate;

public class EmployeeDTO {
    private Long genderId;
    private Long jobId;
    private String name;
    private String lastName;
    private LocalDate birthdate;

    public EmployeeDTO(Long genderId, Long jobId, String name, String lastName, LocalDate birthdate) {
        this.genderId = genderId;
        this.jobId = jobId;
        this.name = name;
        this.lastName = lastName;
        this.birthdate = birthdate;
    }

    public EmployeeDTO() {
		// TODO Auto-generated constructor stub
	}


	public Long getGenderId() {
        return genderId;
    }

    public void setGenderId(Long genderId) {
        this.genderId = genderId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
        this.lastName = lastName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "genderId=" + genderId +
                ", jobId=" + jobId +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }
}
