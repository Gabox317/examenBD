package com.example.DBExamen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.DBExamen.dto.EmployeePaymentRequestDTO;
import com.example.DBExamen.dto.EmployeePaymentResponseDTO;
import com.example.DBExamen.service.EmployeePaymentService;

@RestController
@RequestMapping("/employeePayment")
public class EmployeePaymentController {

    @Autowired
    private EmployeePaymentService employeePaymentService;

    @PostMapping("/totalPaymentByDateRange")
    public ResponseEntity<EmployeePaymentResponseDTO> getTotalPaymentByDateRange(
            @RequestBody EmployeePaymentRequestDTO requestDTO) {

        if (!employeePaymentService.employeeExists(requestDTO.getEmployeeId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (requestDTO.getStartDate().isAfter(requestDTO.getEndDate())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        EmployeePaymentResponseDTO responseDTO = employeePaymentService.getTotalPaymentByDateRange(requestDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}

