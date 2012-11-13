CREATE TABLE IF NOT EXISTS t_rest (
 	id INT(16) UNSIGNED NOT NULL AUTO_INCREMENT,
	nombre	varchar(120),
	fecha date,
	numero	INT(11),
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS t_permission (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS t_user (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  surname1 varchar(255) DEFAULT NULL,
  email varchar(255) DEFAULT NULL,
  telephone int(11) DEFAULT NULL,
  enable int(1) DEFAULT NULL,
  blocked int(1) DEFAULT NULL,
  attempts_login int(11) DEFAULT NULL,
  surname2 varchar(255) DEFAULT NULL,
  username varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  date_last_password timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  date_last_login timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY username_UNIQUE (username)
);

CREATE TABLE IF NOT EXISTS t_group (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(45) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS t_group_permission (
  id int(11) NOT NULL AUTO_INCREMENT,
  id_group int(11) DEFAULT NULL,
  id_permission int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT permission_group FOREIGN KEY (id_group) REFERENCES t_group (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT permission FOREIGN KEY (id_permission) REFERENCES t_permission (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS t_user_group (
  id int(11) NOT NULL AUTO_INCREMENT,
  id_user int(11) DEFAULT NULL,
  id_group int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_group FOREIGN KEY (id_group) REFERENCES t_group (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES t_user (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE VIEW IF NOT EXISTS v_user_group AS 
	select ug.id AS id,u.id AS id_user,u.username AS name_user,g.id AS id_group,g.name AS name_group 
	from ((t_user_group ug join t_user u on((ug.id_user = u.id))) join t_group g on((ug.id_group = g.id)));

CREATE VIEW IF NOT EXISTS v_group_permission AS 
	select gp.id AS id,g.id AS id_group,g.name AS name_group,p.id AS id_permission,p.name AS name_permission 
	from ((t_group_permission gp join t_group g on((gp.id_group = g.id))) join t_permission p on((gp.id_permission = p.id)));


