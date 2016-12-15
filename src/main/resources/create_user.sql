CREATE USER 'batacademyuser'@'localhost' IDENTIFIED BY 'batacademypassword';

GRANT ALL PRIVILEGES ON cs548_batacademy.* TO 'batacademyuser'@'localhost' WITH GRANT OPTION;

SHOW GRANTS FOR 'batacademyuser'@'localhost';