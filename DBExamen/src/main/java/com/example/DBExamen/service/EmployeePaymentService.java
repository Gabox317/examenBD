package com.example.DBExamen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DBExamen.dto.EmployeePaymentRequestDTO;
import com.example.DBExamen.dto.EmployeePaymentResponseDTO;

import repository.EmployeePaymentRepository;

@Service
public class EmployeePaymentService {

    @Autowired
    private EmployeePaymentRepository employeePaymentRepository;

    public boolean employeeExists(int employeeId) {
        // Validar si el empleado existe en la base de datos
        return employeePaymentRepository.employeeExists(employeeId);
    }

    public EmployeePaymentResponseDTO getTotalPaymentByDateRange(EmployeePaymentRequestDTO requestDTO) {
        // Obtener el total pagado desde el repositorio
        double totalPayment = employeePaymentRepository.getTotalPaymentByDateRange(
                requestDTO.getEmployeeId(),
                requestDTO.getStartDate(),
                requestDTO.getEndDate()
        );

        // Construir el DTO de respuesta
        EmployeePaymentResponseDTO responseDTO = new EmployeePaymentResponseDTO();
        responseDTO.setEmployeeId(requestDTO.getEmployeeId());
        responseDTO.setTotalPayment(totalPayment);
        responseDTO.setStartDate(requestDTO.getStartDate());
        responseDTO.setEndDate(requestDTO.getEndDate());

        return responseDTO;
    }
}
