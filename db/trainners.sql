

 CREATE TABLE trainers (
    id SERIAL ,
    cpf VARCHAR(11) NOT NULL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    birth_date DATE NOT NULL,
    gender VARCHAR(10) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    country VARCHAR(50) NOT NULL,
    certification VARCHAR(100) NOT NULL,
    speciality VARCHAR(100) NOT NULL,
    gym VARCHAR(50) NOT NULL,
    hourly_rate DECIMAL(10, 2) NOT NULL,
    years_of_experience INT NOT NULL,
    hire_date DATE NOT NULL,
    salary DECIMAL(10, 2) NOT NULL
);

INSERT INTO trainers (cpf, username, first_name, last_name, email, password, phone_number, birth_date, gender, city, state, country, certification, speciality, gym, hourly_rate, years_of_experience, hire_date, salary)
VALUES 
    ('12345678901', 'trainer1', 'John', 'Doe', 'john.doe@example.com', 'password1', '1234567890', '1990-01-01', 'MALE', 'City1', 'State1', 'Country1', 'Cert1', 'Speciality1', 'Gym1', 50.00, 2, '2023-05-01', 5000.00),
    ('23456789012', 'trainer2', 'Jane', 'Smith', 'jane.smith@example.com', 'password2', '9876543210', '1995-05-10', 'FEMALE', 'City2', 'State2', 'Country2', 'Cert2', 'Speciality2', 'Gym2', 60.00, 3, '2023-05-02', 6000.00),
    ('34567890123', 'trainer3', 'Michael', 'Johnson', 'michael.johnson@example.com', 'password3', '4567890123', '1988-09-15', 'MALE', 'City3', 'State3', 'Country3', 'Cert3', 'Speciality3', 'Gym3', 45.00, 5, '2023-05-03', 4500.00),
    ('45678901234', 'trainer4', 'Emily', 'Wilson', 'emily.wilson@example.com', 'password4', '8901234567', '1992-12-20', 'FEMALE', 'City4', 'State4', 'Country4', 'Cert4', 'Speciality4', 'Gym4', 55.00, 4, '2023-05-04', 5500.00),
    ('56789012345', 'trainer5', 'David', 'Brown', 'david.brown@example.com', 'password5', '2345678901', '1998-06-25', 'MALE', 'City5', 'State5', 'Country5', 'Cert5', 'Speciality5', 'Gym5', 50.00, 1, '2023-05-05', 5000.00);






CREATE OR REPLACE FUNCTION create_trainer(
    cpf VARCHAR(11) DEFAULT NULL,
    username VARCHAR(50) DEFAULT NULL,
    first_name VARCHAR(50) DEFAULT NULL,
    last_name VARCHAR(50) DEFAULT NULL,
    email VARCHAR(100) DEFAULT NULL,
    password VARCHAR(100) DEFAULT NULL,
    phone_number VARCHAR(20) DEFAULT NULL,
    birth_date DATE DEFAULT NULL,
    gender VARCHAR(10) DEFAULT NULL,
    city VARCHAR(50) DEFAULT NULL,
    state VARCHAR(50) DEFAULT NULL,
    country VARCHAR(50) DEFAULT NULL,
    certification VARCHAR(100) DEFAULT NULL,
    speciality VARCHAR(100) DEFAULT NULL,
    gym VARCHAR(50) DEFAULT NULL,
    hourly_rate DECIMAL(10, 2) DEFAULT NULL,
    years_of_experience INT DEFAULT NULL,
    hire_date DATE DEFAULT NULL,
    salary DECIMAL(10, 2) DEFAULT NULL
)
RETURNS VOID AS
$$
BEGIN 
INSERT INTO trainers (cpf, username, first_name, last_name, email, password, phone_number, birth_date, gender, city, state, country, certification, speciality, gym, hourly_rate, years_of_experience, hire_date, salary)
    VALUES (cpf, username, first_name, last_name, email, password, phone_number, birth_date, gender, city, state, country, certification, speciality, gym, hourly_rate, years_of_experience, hire_date, salary);
END;
$$
LANGUAGE plpgsql;  

CREATE OR REPLACE FUNCTION delete_trainer(p_cpf VARCHAR(11))
RETURNS VOID AS
$$
BEGIN
    DELETE FROM trainers WHERE cpf = p_cpf;
END;
$$
LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION update_trainer(
    cpf_to_update VARCHAR(11),
    new_cpf VARCHAR(11) DEFAULT NULL,
    new_username VARCHAR(50) DEFAULT NULL,
    new_first_name VARCHAR(50) DEFAULT NULL,
    new_last_name VARCHAR(50) DEFAULT NULL,
    new_email VARCHAR(100) DEFAULT NULL,
    new_password VARCHAR(100) DEFAULT NULL,
    new_phone_number VARCHAR(20) DEFAULT NULL,
    new_birth_date DATE DEFAULT NULL,
    new_gender VARCHAR(10) DEFAULT NULL,
    new_city VARCHAR(50) DEFAULT NULL,
    new_state VARCHAR(50) DEFAULT NULL,
    new_country VARCHAR(50) DEFAULT NULL,
    new_certification VARCHAR(100) DEFAULT NULL,
    new_speciality VARCHAR(100) DEFAULT NULL,
    new_gym VARCHAR(50) DEFAULT NULL,
    new_hourly_rate DECIMAL(10, 2) DEFAULT NULL,
    new_years_of_experience INT DEFAULT NULL,
    new_hire_date DATE DEFAULT NULL,
    new_salary DECIMAL(10, 2) DEFAULT NULL
)
RETURNS VOID AS
$$
BEGIN
    UPDATE trainers SET
        cpf = COALESCE(new_cpf, cpf),
        username = COALESCE(new_username, username),
        first_name = COALESCE(new_first_name, first_name),
        last_name = COALESCE(new_last_name, last_name),
        email = COALESCE(new_email, email),
        password = COALESCE(new_password, password),
        phone_number = COALESCE(new_phone_number, phone_number),
        birth_date = COALESCE(new_birth_date, birth_date),
        gender = COALESCE(new_gender, gender),
        city = COALESCE(new_city, city),
        state = COALESCE(new_state, state),
        country = COALESCE(new_country, country),
        certification = COALESCE(new_certification, certification),
        speciality = COALESCE(new_speciality, speciality),
        gym = COALESCE(new_gym, gym),
        hourly_rate = COALESCE(new_hourly_rate, hourly_rate),
        years_of_experience = COALESCE(new_years_of_experience, years_of_experience),
        hire_date = COALESCE(new_hire_date, hire_date),
        salary = COALESCE(new_salary, salary)
    WHERE cpf = cpf_to_update;
END;
$$
LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION search_trainer_by_cpf(cpf_to_search VARCHAR(11))
RETURNS TABLE (
    id INT,
    cpf VARCHAR(11),
    username VARCHAR(50),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100),
    password VARCHAR(100),
    phone_number VARCHAR(20),
    birth_date DATE,
    gender VARCHAR(10),
    city VARCHAR(50),
    state VARCHAR(50),
    country VARCHAR(50),
    certification VARCHAR(100),
    speciality VARCHAR(100),
    gym VARCHAR(50),
    hourly_rate DECIMAL(10, 2),
    years_of_experience INT,
    hire_date DATE,
    salary DECIMAL(10, 2)
)
AS $$
BEGIN
    RETURN QUERY
    SELECT * FROM trainers WHERE trainers.cpf = search_trainer_by_cpf.cpf_to_search;
END;
$$
LANGUAGE plpgsql;