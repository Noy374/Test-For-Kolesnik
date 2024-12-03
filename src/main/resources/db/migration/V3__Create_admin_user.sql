DO $$
    DECLARE
        admin_department_id BIGINT;
    BEGIN
        INSERT INTO departments (id, name)
        VALUES (nextval('departments_id_seq'), 'Администраторы')
        RETURNING id INTO admin_department_id;

        INSERT INTO users (id, username, password, department_id)
        VALUES (
                   nextval('users_id_seq'),
                   'admin',
                   '$2a$10$DPXTgmKvoCmzt3funQSCquW0yEM7M6J7XlFJo1spJTkCQO8zJjjEm',
                   admin_department_id
               );

        INSERT INTO user_roles (user_id, role)
        VALUES (
                   currval('users_id_seq'),
                   'ADMIN'
               );
    END $$;
