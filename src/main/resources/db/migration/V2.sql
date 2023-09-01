CREATE TABLE users
(
    id        BIGSERIAL PRIMARY KEY,
    username  VARCHAR        NOT NULL,
    email     VARCHAR UNIQUE NOT NULL,
    password  VARCHAR        NOT NULL,
    is_active BOOLEAN        NOT NULL
);

CREATE TABLE roles
(
    id   BIGSERIAL PRIMARY KEY,
    role VARCHAR NOT NULL
);

CREATE TABLE users_roles
(
    user_id BIGSERIAL REFERENCES users,
    role_id BIGSERIAL REFERENCES roles
);