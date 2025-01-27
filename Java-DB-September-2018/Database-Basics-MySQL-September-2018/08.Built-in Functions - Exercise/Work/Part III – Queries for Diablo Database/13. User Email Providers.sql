USE diablo;

SELECT user_name, 
       substr(email, position('@' IN email) + 1) AS 'email_provider'
FROM users
ORDER BY email_provider, user_name;