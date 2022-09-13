insert into users(id,fullname, username, password, role) values(1,"Administrator", "admin", "$2a$12$cb7ueQ8nANKRWJrzyeAOfu4f8KEvZhSmpdJBCz4dxb3dy6C0ABIqS", "ADMIN");
insert into users(id,fullname, username, password, role) values(2,"User", "user", "$2a$12$rSu9Opw7SEUtor0ltV5xdu3FYQ/nOohntCivEyxezPGjxaTPlEc6W", "USER");

INSERT INTO `curve_point` ( `as_of_date`, `creation_date`, `curve_id`, `term`, `value`) VALUES ('2022-07-26 09:00:55', '2022-07-26 09:00:55', '1', '30', '50');
INSERT INTO `curve_point` ( `as_of_date`, `creation_date`, `curve_id`, `term`, `value`) VALUES ('2022-07-29 09:00:55', '2022-07-04 09:09:50', '2', '10', '100');

INSERT INTO `rating` (`fitch_rating`, `moodys_rating`, `order_number`, `sandprating`) VALUES ('aaa', 'bbb', '1', 'ccc');
INSERT INTO `rating` (`fitch_rating`, `moodys_rating`, `order_number`, `sandprating`) VALUES ('dd', 'eee', '2', 'fff');

INSERT INTO `rule_name` (`description`, `json`, `name`, `sql_part`, `sql_str`, `template`) VALUES ('Test', 'json.txt', 'Test', 'sql_part.txt', 'sql.txt', 'Test');
INSERT INTO `rule_name` (`description`, `json`, `name`, `sql_part`, `sql_str`, `template`) VALUES ('Test2', 'json2.txt', 'Test2', 'sql_part2.txt', 'sql2.txt', 'Test2');

INSERT INTO `bidlist` ( `account`,`bid_quantity`, `type`) VALUES ('1',2,'Test');
INSERT INTO `bidlist` ( `account`,`bid_quantity`, `type`) VALUES ('5',3,'Test2');

INSERT INTO `trade` (`account`, `buy_quantity`, `type`) VALUES ('Test', '100', "Fruit");
INSERT INTO `trade` (`account`, `buy_quantity`, `type`) VALUES ('Test2', '300', "Légume");