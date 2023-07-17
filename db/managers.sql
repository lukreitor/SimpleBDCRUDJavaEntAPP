    CREATE TABLE managers (
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
        hire_date DATE NOT NULL,
        salary DECIMAL(10, 2) NOT NULL,
        gym VARCHAR(50) NOT NULL
        
    );

    INSERT INTO managers (cpf, username, first_name, last_name, email, password, phone_number,  birth_date, gender, city, state, country, hire_date, salary, gym)
    VALUES 
        ( '12345678901', 'manager1', 'John', 'Doe', 'john.doe@example.com', 'password123', '1234567890', '1990-01-01', 'MALE', 'City1', 'State1', 'Country1', '2022-01-01', 5000.00, 'Gym1'),
        ('23456789012', 'manager2', 'Jane', 'Smith', 'jane.smith@example.com', 'password456', '0987654321', '1990-01-01','FEMALE', 'City2', 'State2', 'Country2', '2022-02-01', 5500.00, 'Gym2'),
        ( '34567890123', 'manager3', 'David', 'Johnson', 'david.johnson@example.com', 'password789', '9876543210', '1990-01-01','MALE', 'City3', 'State3', 'Country3', '2022-03-01', 6000.00, 'Gym3'),
        ( '45678901234', 'manager4', 'Emily', 'Davis', 'emily.davis@example.com', 'passwordabc', '0123456789', '1990-01-01','FEMALE', 'City4', 'State4', 'Country4', '2022-04-01', 6500.00, 'Gym4'),
        ( '56789012345', 'manager5', 'Michael', 'Wilson', 'michael.wilson@example.com', 'passwordxyz', '5432109876', '1990-01-01','MALE', 'City5', 'State5', 'Country5', '2022-05-01', 7000.00, 'Gym5');

    CREATE OR REPLACE FUNCTION create_manager(
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
        hire_date DATE DEFAULT NULL,
        salary DECIMAL(10, 2) DEFAULT NULL,
        gym VARCHAR(50) DEFAULT NULL
    )
    RETURNS VOID AS
    $$
    BEGIN 
    INSERT INTO managers (cpf, username, first_name, last_name, email, password, phone_number, birth_date, gender, city, state, country, hire_date, salary, gym)
        VALUES (cpf, username, first_name, last_name, email, password, phone_number, birth_date, gender, city, state, country, hire_date, salary, gym);
    END;
    $$
    LANGUAGE plpgsql;  

    CREATE OR REPLACE FUNCTION delete_manager(p_cpf VARCHAR(11))
    RETURNS VOID AS
    $$
    BEGIN
        DELETE FROM managers WHERE cpf = p_cpf;
    END;
    $$
    LANGUAGE plpgsql;


    CREATE OR REPLACE FUNCTION update_manager(
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
        new_hire_date DATE DEFAULT NULL,
        new_salary DECIMAL(10, 2) DEFAULT NULL,
        new_gym VARCHAR(50) DEFAULT NULL
    )
    RETURNS VOID AS
    $$
    BEGIN
        UPDATE managers SET
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
            hire_date = COALESCE(new_hire_date, hire_date),
            salary = COALESCE(new_salary, salary),
            gym = COALESCE(new_gym, gym)
        WHERE cpf = cpf_to_update;
    END;
    $$
    LANGUAGE plpgsql;



    CREATE OR REPLACE FUNCTION search_manager_by_cpf(cpf_to_search VARCHAR(11))
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
        hire_date DATE,
        salary DECIMAL(10, 2),
        gym VARCHAR(50)
    )
    AS $$
    BEGIN
        RETURN QUERY
        SELECT * FROM managers WHERE managers.cpf = search_manager_by_cpf.cpf_to_search;
    END;
    $$
    LANGUAGE plpgsql;