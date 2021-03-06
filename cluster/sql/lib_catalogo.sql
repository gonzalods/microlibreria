CREATE DATABASE lib_catalogo;
CREATE DATABASE lib_cliente;
CREATE DATABASE lib_acceso;

USE lib_catalogo;

CREATE TABLE CAT_LIBRO(
	ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
	TITULO VARCHAR(100) NOT NULL,
	DESCRIPCION VARCHAR(250),
	PRECIO DECIMAL(6,2),
	ANNO SMALLINT,
	ISBN VARCHAR(13),
	CATEGORIA INTEGER);
CREATE TABLE CAT_CATEGORIA(
	ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
	DESCRIPCION VARCHAR(50) NOT NULL);
CREATE TABLE CAT_AUTORES(
	ID_LIBRO INTEGER NOT NULL,
	NOMBRE VARCHAR(50),
	PRIMARY KEY(ID_LIBRO,NOMBRE));

ALTER TABLE CAT_LIBRO ADD FOREIGN KEY(CATEGORIA) REFERENCES CAT_CATEGORIA(ID);
ALTER TABLE CAT_AUTORES ADD FOREIGN KEY(ID_LIBRO) REFERENCES CAT_LIBRO(ID);

SET NAMES utf8;

INSERT INTO CAT_CATEGORIA (DESCRIPCION) VALUES('Literatura y ficción');
INSERT INTO CAT_CATEGORIA (DESCRIPCION) VALUES('Tecnología e ingienería');

INSERT INTO CAT_LIBRO (TITULO,DESCRIPCION,PRECIO,ANNO,ISBN,CATEGORIA) VALUES('EL MOZARABE','Cuando en 939 el emir Abd-al-Rahman III se erige como califa, la España musulmana comienza una etapa de esplendor inigualable cuyo estandarte será su capital, Córdoba',19.00,2011,'546564464848',1);
INSERT INTO CAT_LIBRO (TITULO,DESCRIPCION,PRECIO,ANNO,ISBN,CATEGORIA) VALUES('Don Quijote de la Mancha. Primera parte: Versión original abreviada',NULL,3.31,0,'8480637471',1);
INSERT INTO CAT_LIBRO (TITULO,DESCRIPCION,PRECIO,ANNO,ISBN,CATEGORIA) VALUES('Física Teórica. Mecánica','Este primer tomo del Curso de Física teórica está dedicado naturalmente a los fundamentos del tema, es decir a la Mecánica clásica newtoniana y contiene los problemas fundamentales de la Mecánica teórica:',26.19,1970,'8429140816',2);
INSERT INTO CAT_LIBRO (TITULO,DESCRIPCION,PRECIO,ANNO,ISBN,CATEGORIA) VALUES('Electrónica digital y microprocesadores',NULL,15.65,1993,'8487840337',2);

INSERT INTO CAT_AUTORES (ID_LIBRO, NOMBRE) VALUES(1,'Jesus Sanchez Adalid');
INSERT INTO CAT_AUTORES (ID_LIBRO, NOMBRE) VALUES(2,'Miguel de Cervantes');
INSERT INTO CAT_AUTORES (ID_LIBRO, NOMBRE) VALUES(3,'L. D. Landau');
INSERT INTO CAT_AUTORES (ID_LIBRO, NOMBRE) VALUES(4,'Eduardo Santamaría');


USE lib_cliente;

CREATE TABLE CLI_CLIENTE(
	ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
	NOMBRE VARCHAR(25),
	APELLIDOS VARCHAR(50),
	FECHANACIMIENTO DATE,
	FECHAALTA DATETIME DEFAULT CURRENT_TIMESTAMP,
	EMAIL VARCHAR(50),
	CALLE VARCHAR(50),
	NUMERO VARCHAR(4),
	PISO VARCHAR(10),
	CIUDAD VARCHAR(25),
	CODIGOPOSTAL VARCHAR(10),
	NOMBREUSUARIO VARCHAR(250) NOT NULL);
	
INSERT INTO CLI_CLIENTE (NOMBRE,APELLIDOS,FECHANACIMIENTO,FECHAALTA,EMAIL,CALLE,NUMERO,PISO,CIUDAD,CODIGOPOSTAL,NOMBREUSUARIO) VALUES('Usuario Uno','Libreria','1981-09-03', '2013-10-24 00:00:00','usuario.libreria@viewnext.com','Calle','4','1º Dch.','Madrid','28021','usuario');

USE lib_acceso;

create table users (
  username varchar(256),
  password varchar(256),
  enabled boolean
);

create table authorities (
  username varchar(256),
  authority varchar(256)
);

insert into users (username, password, enabled) values ('usuario', 'viewnext', true);
insert into authorities (username, authority) values ('usuario', 'ROLE_USUARIO');
insert into users (username, password, enabled) values ('admin', 'viewnext', true);
insert into authorities (username, authority) values ('admin', 'ROLE_ADMIN');