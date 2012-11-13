INSERT INTO t_group VALUES (1,'admin'),(2,'user');
INSERT INTO t_user VALUES (1,'admin','admin','admin@indizen.com',0,1,0,0,'admin','admin','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','2012-01-01','2012-01-01'),(2,'user','user','user@indizen.com',0,1,0,0,'user','user','04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb','2012-01-01','2012-01-01');
INSERT INTO t_permission VALUES (1,'ROLE_ACCESO_SISTEMA'),(2,'ROLE_ADMIN'),(3,'ROLE_USER');
INSERT INTO t_user_group VALUES (1,1,1),(2,2,2);
INSERT INTO t_group_permission VALUES (1,1,1),(2,1,2),(3,2,3),(4,2,1);
insert into t_rest (nombre,fecha,numero) values ('chabir','1985-03-15',22);
