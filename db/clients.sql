CREATE TABLE clients (
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
    occupation VARCHAR(50) NOT NULL,
    income DECIMAL(10, 2) NOT NULL,
    card_holder_name VARCHAR(100) NOT NULL,
    card_number VARCHAR(16) NOT NULL,
    expiration_date VARCHAR(10) NOT NULL,
    security_code VARCHAR(3) NOT NULL,
    billing_address VARCHAR(200) NOT NULL
);




INSERT INTO clients (cpf, username, first_name, last_name, email, password, phone_number, birth_date, gender, city, state, country, occupation, income, card_holder_name, card_number, expiration_date, security_code, billing_address, trainer_id)
VALUES
    ('12345678901', 'johnDoe', 'John', 'Doe', 'john@example.com', 'password1', '1234567890', '1990-01-01', 'MALE', 'New York', 'New York', 'USA', 'Engineer', 5000.00, 'John Doe', '1111222233334444', '12/23', '123', '123 Main St', 12345678901),
    ('98765432109', 'janeSmith', 'Jane', 'Smith', 'jane@example.com', 'password2', '0987654321', '1995-05-10', 'FEMALE', 'Los Angeles', 'California', 'USA', 'Teacher', 4000.00, 'Jane Smith', '5555666677778888', '06/25', '789', '456 Elm St', 12345678901),
    ('45678912304', 'robertJohnson', 'Robert', 'Johnson', 'robert@example.com', 'password3', '1112223334', '1985-09-20', 'MALE', 'Chicago', 'Illinois', 'USA', 'Accountant', 6000.00, 'Robert Johnson', '9999000011112222', '09/24', '456', '789 Oak St', 12345678901),
    ('65432198703', 'lisaWilliams', 'Lisa', 'Williams', 'lisa@example.com', 'password4', '4445556667', '1998-07-15', 'FEMALE', 'Miami', 'Florida', 'USA', 'Nurse', 3500.00, 'Lisa Williams', '3333444455556666', '03/27', '789', '321 Pine St', 23456789012),
    ('98765432102', 'michaelSmith', 'Michael', 'Smith', 'michael@example.com', 'password5', '7778889990', '1992-03-28', 'MALE', 'San Francisco', 'California', 'USA', 'Software Developer', 8000.00, 'Michael Smith', '3333444455556666', '03/27', '789', '321 Pine St', 23456789012),
    ('12345678902', 'sarahJohnson', 'Sarah', 'Johnson', 'sarah@example.com', 'password6', '2223334445', '1993-12-05', 'FEMALE', 'Chicago', 'Illinois', 'USA', 'Marketing Manager', 7000.00, 'Sarah Johnson', '1111222233334444', '12/23', '123', '456 Main St', 45678901234),
    ('98765432108', 'jamesSmith', 'James', 'Smith', 'james@example.com', 'password7', '5556667778', '1994-08-15', 'MALE', 'San Diego', 'California', 'USA', 'Graphic Designer', 4500.00, 'James Smith', '5555666677778888', '06/25', '789', '789 Elm St', 45678901234)
    CREATE OR REPLACE FUNCTION create_client(
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
    occupation VARCHAR(50) DEFAULT NULL,
    income DECIMAL(10, 2) DEFAULT NULL,
    card_holder_name VARCHAR(100) DEFAULT NULL,
    card_number VARCHAR(16) DEFAULT NULL,
    expiration_date VARCHAR(10) DEFAULT NULL,
    security_code VARCHAR(3) DEFAULT NULL,
    billing_address VARCHAR(200) DEFAULT NUL
)
RETURNS VOID AS
$$
BEGIN
    INSERT INTO clients (cpf, username, first_name, last_name, email, password, phone_number, birth_date, gender, city, state, country, occupation, income, card_holder_name, card_number, expiration_date, security_code, billing_address)
    VALUES (cpf, username, first_name, last_name, email, password, phone_number, birth_date, gender, city, state, country, occupation, income, card_holder_name, card_number, expiration_date, security_code, billing_address);
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION delete_client(p_cpf VARCHAR(11))
RETURNS VOID AS
$$
BEGIN
    DELETE FROM clients WHERE cpf = p_cpf;
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION update_client(
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
    new_occupation VARCHAR(50) DEFAULT NULL,
    new_income DECIMAL(10, 2) DEFAULT NULL,
    new_card_holder_name VARCHAR(100) DEFAULT NULL,
    new_card_number VARCHAR(16) DEFAULT NULL,
    new_expiration_date VARCHAR(10) DEFAULT NULL,
    new_security_code VARCHAR(3) DEFAULT NULL,
    new_billing_address VARCHAR(200) DEFAULT NULL
)
RETURNS VOID AS
$$
BEGIN
    UPDATE clients SET
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
        occupation = COALESCE(new_occupation, occupation),
        income = COALESCE(new_income, income),
        card_holder_name = COALESCE(new_card_holder_name, card_holder_name),
        card_number = COALESCE(new_card_number, card_number),
        expiration_date = COALESCE(new_expiration_date, expiration_date),
        security_code = COALESCE(new_security_code, security_code),
        billing_address = COALESCE(new_billing_address, billing_address)
    WHERE cpf = cpf_to_update;
END;
$$
LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION search_client_by_cpf(cpf_to_search VARCHAR(11))
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
    occupation VARCHAR(50),
    income DECIMAL(10, 2),
    card_holder_name VARCHAR(100),
    card_number VARCHAR(16),
    expiration_date VARCHAR(10),
    security_code VARCHAR(3),
    billing_address VARCHAR(200)
)
AS $$
BEGIN
    RETURN QUERY
    SELECT * FROM clients WHERE clients.cpf = search_client_by_cpf.cpf_to_search;
END;
$$
LANGUAGE plpgsql;



