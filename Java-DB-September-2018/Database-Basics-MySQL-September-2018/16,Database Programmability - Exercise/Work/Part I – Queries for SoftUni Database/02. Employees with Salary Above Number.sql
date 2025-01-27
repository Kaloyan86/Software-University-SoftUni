USE soft_uni;

DELIMITER $$
CREATE PROCEDURE usp_get_employees_salary_above(target_salary DOUBLE)
BEGIN
	SELECT first_name, last_name
	FROM employees
	WHERE salary >= target_salary
	ORDER BY first_name, last_name, employee_id;
END $$

CALL usp_get_employees_salary_above(48100);