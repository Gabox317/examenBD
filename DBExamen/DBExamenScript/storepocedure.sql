DELIMITER $$

CREATE FUNCTION FNINSERT_EMPLOYEE(
    p_name VARCHAR(255),
    p_last_name VARCHAR(255),
    p_birthdate DATE,
    p_gender_id INT,
    p_job_id INT
) RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE v_employee_id INT;

    INSERT INTO EMPLOYEES (NAME, LAST_NAME, BIRTHDATE, GENDER_ID, JOB_ID)
    VALUES (p_name, p_last_name, p_birthdate, p_gender_id, p_job_id);

    SET v_employee_id = LAST_INSERT_ID();

    RETURN v_employee_id;

END $$

DELIMITER ;





DELIMITER $$


DROP PROCEDURE IF EXISTS agregar_horas $$


CREATE PROCEDURE agregar_horas(
    IN p_empleado_id INT, 
    IN p_fecha DATE, 
    IN p_horas INT
)
BEGIN
    DECLARE v_count INT;


    SELECT COUNT(*) INTO v_count 
    FROM employees 
    WHERE id = p_empleado_id;

    IF v_count = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Empleado no existe.';
    END IF;


    IF p_horas > 20 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El total de horas trabajadas no puede ser mayor a 20.';
    END IF;


    IF p_fecha > CURDATE() THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La fecha no puede ser mayor a la actual.';
    END IF;


    SELECT COUNT(*) INTO v_count 
    FROM EMPLOYEE_WORKED_HOURS
    WHERE EMPLOYEE_ID = p_empleado_id AND WORKED_DATE = p_fecha;

    IF v_count > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Ya existe un registro de horas trabajadas para este empleado en la misma fecha.';
    END IF;


    INSERT INTO EMPLOYEE_WORKED_HOURS (EMPLOYEE_ID, WORKED_HOURS, WORKED_DATE) 
    VALUES (p_empleado_id, p_horas, p_fecha);
    
    SELECT 'Horas trabajadas agregadas exitosamente.' AS mensaje;
END $$


DELIMITER ;











DELIMITER $$

CREATE FUNCTION FN_CHECK_JOB_ID(p_job_id INT)
RETURNS TINYINT
DETERMINISTIC
BEGIN
    DECLARE v_count INT;

    -- Contar el número de registros con el job_id proporcionado
    SELECT COUNT(*) INTO v_count 
    FROM jobs 
    WHERE id = p_job_id;

    -- Retornar 1 si el puesto existe, 0 si no
    RETURN IF(v_count > 0, 1, 0);
END $$

DELIMITER ;



DELIMITER $$

CREATE PROCEDURE FN_GET_EMPLOYEES_BY_JOB(p_job_id INT)
BEGIN
    -- Retornar los empleados que tienen el job_id proporcionado
    SELECT 
        e.id,
        e.name,
        e.last_name,
        e.birthdate,
        j.id AS job_id,
        j.name AS job_name,
        j.salary,
        g.id AS gender_id,
        g.name AS gender_name
    FROM 
        employees e
    JOIN 
        jobs j ON e.job_id = j.id
    JOIN 
        genders g ON e.gender_id = g.id
    WHERE 
        j.id = p_job_id;
END $$

DELIMITER ;







