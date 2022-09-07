create table role (
    id bigint not null,
    role_code varchar(255) not null UNIQUE,
    role_name varchar(255) not null,
    is_user_defined boolean not null default 'true',
    primary key (id)
)
INSERT INTO role(id, role_code, role_name, is_user_defined) VALUES(nextval('np_role_gen_seq'), 'ROLE_ADMIN', 'Admin', true);
INSERT INTO role(id, role_code, role_name, is_user_defined)
VALUES(nextval('np_role_gen_seq'), 'ROLE_SUPER_ADMIN', 'Super Admin', false);