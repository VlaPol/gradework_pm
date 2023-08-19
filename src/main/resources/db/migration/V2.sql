CREATE TABLE account
(
    id        BIGSERIAL PRIMARY KEY,
    username  VARCHAR        NOT NULL,
    email     VARCHAR UNIQUE NOT NULL,
    password  VARCHAR        NOT NULL,
    is_active BOOLEAN        NOT NULL
);