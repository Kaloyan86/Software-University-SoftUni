USE soft_uni;

SELECT concat_ws(' ', first_name, middle_name, last_name) AS 'Full Name'
FROM employees
WHERE salary IN (25000, 14000, 12500, 23600);