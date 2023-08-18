CREATE TABLE employee
(
    employee_id BIGSERIAL PRIMARY KEY,
    email       VARCHAR UNIQUE NOT NULL,
    first_name  VARCHAR        NOT NULL,
    last_name   VARCHAR        NOT NULL
);

CREATE TABLE project
(
    project_id  BIGSERIAL PRIMARY KEY,
    name        VARCHAR(200)  NOT NULL,
    stage       VARCHAR(100)  NOT NULL,
    description VARCHAR(2000) NOT NULL
);

CREATE TABLE project_emp
(
    project_id  BIGSERIAL REFERENCES project,
    employee_id BIGSERIAL references employee
);