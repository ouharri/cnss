-- Drop the database if it exists
DROP DATABASE IF EXISTS macnss;

-- Create a new database
CREATE DATABASE macnss
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

-- Drop ENUM types
DROP TYPE IF EXISTS Gender;
DROP TYPE IF EXISTS AgentStatus;
DROP TYPE IF EXISTS FileStatus;
DROP TYPE IF EXISTS AuthStatus;
DROP TYPE IF EXISTS employeeType;
DROP TYPE IF EXISTS month;
DROP DOMAIN IF EXISTS year;

-- Drop tables
DROP TABLE IF EXISTS salary;
DROP TABLE IF EXISTS employees_company;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS company;
DROP TABLE IF EXISTS analytics_documents;
DROP TABLE IF EXISTS radios_documents;
DROP TABLE IF EXISTS visits_documents;
DROP TABLE IF EXISTS scanners_documents;
DROP TABLE IF EXISTS medicine_barcodes;
DROP TABLE IF EXISTS files_medicines;
DROP TABLE IF EXISTS medicines;
DROP TABLE IF EXISTS abstract_documents;
DROP TABLE IF EXISTS refund_history;
DROP TABLE IF EXISTS refund_files;
DROP TABLE IF EXISTS patients;
DROP TABLE IF EXISTS authentication_administrators_history;
DROP TABLE IF EXISTS authentication_agents_cnss_history;
DROP TABLE IF EXISTS abstract_authentication_history;
DROP TABLE IF EXISTS verification_agents_cnss_codes;
DROP TABLE IF EXISTS verification_administrators_codes;
DROP TABLE IF EXISTS abstract_verification_codes;
DROP TABLE IF EXISTS agents_cnss;
DROP TABLE IF EXISTS administrators;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS abstract_users;


CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Define an ENUM type for gender
CREATE TYPE Gender AS ENUM ('MALE', 'FEMALE');

-- Define an ENUM type for Agent status
CREATE TYPE AgentStatus AS ENUM ('ACTIVE', 'INACTIVE');

-- Define an ENUM type FileStatus for file status
CREATE TYPE FileStatus AS ENUM ('PENDING', 'APPROVED', 'REJECTED');

-- Define an ENUM type for Auth status
CREATE TYPE AuthStatus AS ENUM ('SUCCESS', 'FAILED');

-- Define an ENUM type employeeType for employee type
CREATE TYPE employeeType AS ENUM ('AGENT_CNSS', 'ADMINISTRATOR');

CREATE TYPE month AS ENUM ('JANUARY', 'FEBRUARY', 'MARCH', 'APRIL', 'MAY', 'JUNE',
    'JULY', 'AUGUST', 'SEPTEMBER', 'OCTOBER', 'NOVEMBER', 'DECEMBER');

CREATE DOMAIN year AS int CHECK (VALUE >= 2000 AND VALUE <= EXTRACT(YEAR FROM CURRENT_DATE));

-- Users table (abstract class)
CREATE TABLE abstract_users
(
    cnie       VARCHAR(50) PRIMARY KEY,      -- Unique identification user
    first_name VARCHAR(100) NOT NULL,        -- User's first name
    last_name  VARCHAR(100) NOT NULL,        -- User's last name
    email      VARCHAR(200) NOT NULL UNIQUE, -- Email address (unique)
    phone      VARCHAR(20),                  -- Phone number (optional)
    birthday   DATE,                         -- Date of birth (optional)
    pwd_hash   VARCHAR(255) NOT NULL,        -- Password hash
    gender     Gender                        -- User's gender (uses the ENUM Gender)
);

-- Employees table and its associated tables
CREATE TABLE employees
(
    email_verified_at TIMESTAMP DEFAULT NULL -- Email verification date
) INHERITS (abstract_users);

CREATE TABLE administrators
(
    administrator_id uuid DEFAULT uuid_generate_v4 () PRIMARY KEY
) INHERITS (employees);

CREATE TABLE agents_cnss
(
    agent_id uuid DEFAULT uuid_generate_v4 () PRIMARY KEY
) INHERITS (employees);

CREATE TABLE abstract_verification_codes
(
    verification_code VARCHAR(6) NOT NULL,
    code_generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE verification_administrators_codes
(
    administrator_id INT REFERENCES administrators (administrator_id) ON DELETE CASCADE ON UPDATE CASCADE
) INHERITS (abstract_verification_codes);

CREATE TABLE verification_agents_cnss_codes
(
    agent_id INT REFERENCES agents_cnss (agent_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE abstract_authentication_history
(
    authentication_id uuid DEFAULT uuid_generate_v4 () PRIMARY KEY,
    authenticated_at  TIMESTAMP           DEFAULT CURRENT_TIMESTAMP,
    auth_status       AuthStatus NOT NULL DEFAULT 'SUCCESS'
);

CREATE TABLE authentication_agents_cnss_history
(
    agent_cnss_id INT REFERENCES agents_cnss (agent_id)
) INHERITS (abstract_authentication_history);

CREATE TABLE authentication_administrators_history
(
    administrator_id INT REFERENCES administrators (administrator_id)
) INHERITS (abstract_authentication_history);

-- Patients table and its associated tables
CREATE TABLE patients
(
    matriculate VARCHAR(255) PRIMARY KEY -- Unique matriculation number for patients
) INHERITS (abstract_users);

CREATE TABLE refund_files
(
    refund_file_id      uuid DEFAULT uuid_generate_v4 () PRIMARY KEY,
    matriculate         VARCHAR(255)   NOT NULL UNIQUE, -- Unique matriculation number for refund files
    patient_matriculate VARCHAR(255)   NOT NULL,
    file_status         FileStatus     NOT NULL,        -- File status
    total_amount        DECIMAL(10, 2) NOT NULL,        -- Total amount
    FOREIGN KEY (patient_matriculate) REFERENCES patients (matriculate)
);

CREATE TABLE refund_history
(
    refund_history_id uuid DEFAULT uuid_generate_v4 () PRIMARY KEY,
    refund_file_id    INT NOT NULL,
    validation_date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    refund_amount     DECIMAL(10, 2),
    FOREIGN KEY (refund_file_id) REFERENCES refund_files (refund_file_id)
);

-- Documents table and its associated tables
CREATE TABLE abstract_documents
(
    document_id  uuid DEFAULT uuid_generate_v4 () PRIMARY KEY,
    code         VARCHAR(150) NOT NULL UNIQUE,
    description  VARCHAR(150),
    paid_price   DECIMAL(10, 2),
    document_url VARCHAR(250)
);

-- Tables for document types (Scanner, Visit, Radio, Analysis)
CREATE TABLE scanner_types
(
    scanner_type_id            uuid DEFAULT uuid_generate_v4 () PRIMARY KEY,
    scanner_type               VARCHAR(200) UNIQUE, -- Scanner type name
    scanner_reimbursement_rate DECIMAL(5, 2)        -- Reimbursement rate for scanner
);

CREATE TABLE visit_types
(
    visit_type_id            uuid DEFAULT uuid_generate_v4 () PRIMARY KEY,
    visit_type               VARCHAR(200) UNIQUE, -- Visit type name
    visit_reimbursement_rate DECIMAL(5, 2)        -- Reimbursement rate for visit
);

CREATE TABLE radio_types
(
    radio_type_id            uuid DEFAULT uuid_generate_v4 () PRIMARY KEY,
    radio_type               VARCHAR(200) UNIQUE, -- Radio type name
    radio_reimbursement_rate DECIMAL(5, 2)        -- Reimbursement rate for radio
);

CREATE TABLE analysis_types
(
    analysis_type_id            uuid DEFAULT uuid_generate_v4 () PRIMARY KEY,
    analysis_type               VARCHAR(200) UNIQUE, -- Analysis type name
    analysis_reimbursement_rate DECIMAL(5, 2)        -- Reimbursement rate for analysis
);

-- Table for medication information
CREATE TABLE medicines
(
    code               VARCHAR(200) PRIMARY KEY, -- Unique medication code
    name               VARCHAR(255) NOT NULL,    -- Medication name
    dc1                VARCHAR(200),             -- Medication DC1
    dosage             DECIMAL(10, 2),           -- Medication dosage
    dosage_unit        VARCHAR(100),             -- Dosage unit
    form               VARCHAR(100),             -- Medication form
    presentation       VARCHAR(100),             -- Medication presentation
    ppv                DECIMAL(10, 2),           -- Public selling price
    ph                 VARCHAR(150),             -- Medication PH
    gross_price        DECIMAL(10, 2),           -- Gross price
    generic_principles VARCHAR(255),             -- Generic principles
    reimbursement_rate DECIMAL(5, 2)             -- Reimbursement rate
);

-- Table for medication files
CREATE TABLE files_medicines
(
    medicine_id    VARCHAR(200) NOT NULL,
    FOREIGN KEY (medicine_id) REFERENCES medicines (code) ON DELETE CASCADE ON UPDATE CASCADE,
    refund_file_id INT, -- Added foreign key for relationship with refund_files
    FOREIGN KEY (refund_file_id) REFERENCES refund_files (refund_file_id)
) INHERITS (abstract_documents);


-- Table for medication barcodes
CREATE TABLE medicine_barcodes
(
    medicine_id    VARCHAR(200),
    barcode        VARCHAR(150) NOT NULL,
    FOREIGN KEY (medicine_id) REFERENCES medicines (code) ON DELETE CASCADE ON UPDATE CASCADE,
    refund_file_id INT, -- Added foreign key for relationship with refund_files
    FOREIGN KEY (refund_file_id) REFERENCES refund_files (refund_file_id)
) INHERITS (abstract_documents);


-- Tables for scanner, visit, radio, and analysis information
CREATE TABLE scanners_documents
(
    scanner_id      uuid DEFAULT uuid_generate_v4 () PRIMARY KEY,
    scanner_type_id INT  NOT NULL,
    scanner_date    DATE NOT NULL,
    scanner_result  VARCHAR(250),
    refund_file_id  INT, -- Added foreign key for relationship with refund_files
    FOREIGN KEY (scanner_type_id) REFERENCES scanner_types (scanner_type_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (refund_file_id) REFERENCES refund_files (refund_file_id)
) INHERITS (abstract_documents);


CREATE TABLE visits_documents
(
    visit_id       uuid DEFAULT uuid_generate_v4 () PRIMARY KEY,
    visit_type_id  INT  NOT NULL,
    physician      VARCHAR(150),
    visit_date     DATE NOT NULL,
    visit_reason   VARCHAR(250),
    refund_file_id INT, -- Added foreign key for relationship with refund_files
    FOREIGN KEY (visit_type_id) REFERENCES visit_types (visit_type_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (refund_file_id) REFERENCES refund_files (refund_file_id)
) INHERITS (abstract_documents);


CREATE TABLE radios_documents
(
    radio_id       uuid DEFAULT uuid_generate_v4 () PRIMARY KEY,
    radio_type_id  INT  NOT NULL,
    radiologist    VARCHAR(200),
    radio_date     DATE NOT NULL,
    radio_result   VARCHAR(250),
    refund_file_id INT, -- Added foreign key for relationship with refund_files
    FOREIGN KEY (radio_type_id) REFERENCES radio_types (radio_type_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (refund_file_id) REFERENCES refund_files (refund_file_id)
) INHERITS (abstract_documents);


CREATE TABLE analytics_documents
(
    analytical_id    uuid DEFAULT uuid_generate_v4 () PRIMARY KEY,
    analysis_type_id INT  NOT NULL,
    laboratory       VARCHAR(200),
    lab_address      VARCHAR(200),
    analysis_date    DATE NOT NULL,
    refund_file_id   INT, -- Added foreign key for relationship with refund_files
    FOREIGN KEY (analysis_type_id) REFERENCES analysis_types (analysis_type_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (refund_file_id) REFERENCES refund_files (refund_file_id)
) INHERITS (abstract_documents);

CREATE TABLE company
(
    company_id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    name       VARCHAR(200) NOT NULL,
    address    VARCHAR(200) NOT NULL,
    city       VARCHAR(200) NOT NULL,
    country    VARCHAR(200) NOT NULL,
    postalCode VARCHAR(200) NOT NULL,
    phone      VARCHAR(20)  NOT NULL,
    email      VARCHAR(200) NOT NULL,
    website    VARCHAR(200) NOT NULL
);

CREATE TABLE employees
(
    employee_id uuid DEFAULT uuid_generate_v4 () PRIMARY KEY
) INHERITS (abstract_users);

CREATE TABLE employees_company
(
    employee            employees,
    company             company,
    employee_type       employeeType NOT NULL,
    start_date          DATE         NOT NULL,
    end_date            DATE,
    salary              DECIMAL(10, 4) NOT NULL,
    contribution        DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (employee, company,end_date)
);

CREATE TABLE salary
(
    employee_company employees_company,
    work_month       month NOT NULL,
    work_year        year  NOT NULL,
    work_day         INT   NOT NULL DEFAULT 26,
    salaryObtained   DECIMAL(10, 4) NOT NULL,
    PRIMARY KEY (employee_company, work_month, work_year)
);