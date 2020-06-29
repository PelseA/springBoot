INSERT INTO public.role(id, role)
	VALUES
	(1, 'ROLE_USER'),
	(2, 'ROLE_ADMIN');

INSERT INTO public.usr(id, active, email, password, surname, username)
    VALUES
    (1, false, 'sgha@ghh.ru', '12345', 'Пупкин', 'Женя');

INSERT INTO public.usr_roles(users_id, roles_id)
    VALUES
    (1, 1);