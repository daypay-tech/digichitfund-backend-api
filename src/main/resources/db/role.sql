INSERT INTO role(id, role_code, role_name, is_user_defined) VALUES(nextval('role_gen_seq'), 'ROLE_ADMIN', 'Admin', true);
INSERT INTO role(id, role_code, role_name, is_user_defined)
VALUES(nextval('role_gen_seq'), 'ROLE_SUPER_ADMIN', 'Super Admin', false);