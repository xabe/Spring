CREATE TABLE IF NOT EXISTS t_product (
 	id INT(16) UNSIGNED NOT NULL AUTO_INCREMENT,
	nombre	varchar(120) COMMENT 'Literal',
	fecha date,
	numero	INT(11),
	PRIMARY KEY (id)
);