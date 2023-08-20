CREATE TABLE users (
    id serial PRIMARY KEY ,
    username varchar(255) NOT NULL ,
    password varchar(255) NOT NULL
);

CREATE TABLE roles (
    id serial PRIMARY KEY ,
    title varchar(255) NOT NULL
);

CREATE TABLE users_roles (
    user_id int NOT NULL ,
    role_id int NOT NULL ,
    PRIMARY KEY (user_id, role_id),
    foreign key (user_id) references users(id),
    foreign key (role_id) references roles(id)
);

INSERT INTO users(username, password) VALUES ('user', '100'), ('admin', '200');
INSERT INTO roles(title) VALUES ('ROLE_USER'), ('ROLE_ADMIN');

INSERT INTO users_roles(user_id, role_id) VALUES (1, 1), (2, 2)