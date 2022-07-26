insert into users(id,fullname, username, password, role) values(1,"Administrator", "admin", "$2a$12$cb7ueQ8nANKRWJrzyeAOfu4f8KEvZhSmpdJBCz4dxb3dy6C0ABIqS", "ADMIN");
insert into users(id,fullname, username, password, role) values(2,"User", "user", "$2a$12$rSu9Opw7SEUtor0ltV5xdu3FYQ/nOohntCivEyxezPGjxaTPlEc6W", "USER");

INSERT INTO `curvepoint` (`id`, `as_of_date`, `creation_date`, `curve_id`, `term`, `value`) VALUES ('1', '2022-07-26 09:00:55', '2022-07-26 09:00:55', '1', '30', '50');
INSERT INTO `curvepoint` (`id`, `as_of_date`, `creation_date`, `curve_id`, `term`, `value`) VALUES ('2', '2022-07-29 09:00:55', '2022-07-04 09:09:50', '2', '10', '100');