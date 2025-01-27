CREATE DATABASE IF NOT EXISTS DBExamen;
USE DBExamen;


CREATE TABLE GENDERS (
    ID INT AUTO_INCREMENT PRIMARY KEY, 
    NAME VARCHAR(255) NOT NULL        
);


CREATE TABLE JOBS (
    ID INT AUTO_INCREMENT PRIMARY KEY,  
    NAME VARCHAR(255) NOT NULL,         
    SALARY DECIMAL(9,2) NOT NULL        
);


CREATE TABLE EMPLOYEES (
    ID INT AUTO_INCREMENT PRIMARY KEY,   
    GENDER_ID INT NOT NULL,             
    JOB_ID INT NOT NULL,                 
    NAME VARCHAR(255) NOT NULL,          
    LAST_NAME VARCHAR(255) NOT NULL,     
    BIRTHDATE DATE NOT NULL,             
    CONSTRAINT FK_EMPLOYEE_GENDER FOREIGN KEY (GENDER_ID) REFERENCES GENDERS(ID),
    CONSTRAINT FK_EMPLOYEE_JOB FOREIGN KEY (JOB_ID) REFERENCES JOBS(ID)
);

CREATE TABLE EMPLOYEE_WORKED_HOURS (
    ID INT AUTO_INCREMENT PRIMARY KEY,       
    EMPLOYEE_ID INT NOT NULL,                
    WORKED_HOURS DECIMAL(5,2) NOT NULL,      
    WORKED_DATE DATE NOT NULL,              
    CONSTRAINT FK_WORKED_EMPLOYEE FOREIGN KEY (EMPLOYEE_ID) REFERENCES EMPLOYEES(ID),
    CONSTRAINT UNIQUE_EMPLOYEE_WORKED_DATE UNIQUE (EMPLOYEE_ID, WORKED_DATE)  
);

INSERT INTO GENDERS (ID, NAME) VALUES
(1, 'Masculino'),
(2, 'Femenino');

INSERT INTO JOBS (ID, NAME, SALARY) VALUES
(1, 'Desarrollador de Software', 60000.00),
(2, 'Diseñador Gráfico', 50000.00),
(3, 'Gerente de Proyectos', 80000.00),
(4, 'Analista de Sistemas', 55000.00);

