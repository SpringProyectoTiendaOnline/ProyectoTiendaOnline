drop database if exists tiendaOnline;
create database tiendaOnline;
use tiendaOnline;

drop table if exists Cliente;
create table Cliente (
	idCliente bigint not null auto_increment,
	nombre varchar(225) not null,
	apellido varchar(225) not null,
	fnacimiento varchar(225) not null,
	direccion varchar(225) not null,
	email varchar(225) not null unique,
	password varchar(225) not null, 
	PRIMARY KEY(idCliente)
);

Drop table if exists Rol;
CREATE TABLE Rol
(
   idRol bigint NOT NULL AUTO_INCREMENT,
   nombreRol VARCHAR (40) NOT NULL,
   PRIMARY KEY (idRol)
);

Drop table if exists Clientes_rol;
CREATE TABLE Clientes_Rol
(
   idCliente bigint NOT NULL,
   idRol bigint NOT NULL,
   PRIMARY KEY (idCliente, idRol ),
   FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente) ON DELETE CASCADE,
   FOREIGN KEY (idRol) REFERENCES Rol(idRol)
);


drop table if exists Banco;
create table Banco (

	idBanco bigint not null auto_increment,
	nombre varchar(225) not null,
	numTarjeta bigint not null,
	titular varchar(225) not null,
	codSeguridad int(3) not null,
	dirFactura varchar(225) not null,
	idCliente bigint null,
	PRIMARY KEY(idBanco),
	FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente) ON DELETE NO ACTION
);

drop table if exists Productos;
create table Productos ( 
	idProducto bigint not null auto_increment,
	codProducto bigint not null unique, 
	titulo varchar(255) not null, 
	descripcion varchar(255) not null,
	precio float not null, 
	descuento float not null, 
	stock bigint not null,
	primary key (idProducto)
);

drop table if exists LineaCompra;
drop table if exists Compra;
create table Compra (
	idCompra bigint not null auto_increment,
	idCliente bigint null,
	fechaCompra Date null,
	primary key(idCompra) ,
	foreign key (idCliente) REFERENCES Cliente (idCliente)
);


create table LineaCompra (
	idLineaCompra bigint not null auto_increment,
	idCompra bigint null,
	idProducto bigint null,
	cantidad bigint null,
	precioTotal float null,
	primary key(idLineaCompra),
	foreign key (idCompra) REFERENCES Compra(idCompra),
	foreign key (idProducto) REFERENCES Productos(idProducto) ON DELETE NO ACTION
);

Drop table if exists estadoPedido;
CREATE TABLE estadoPedido
(
   idEstado bigint NOT NULL AUTO_INCREMENT,
   estado VARCHAR (40) NOT NULL,
   PRIMARY KEY (idEstado)
);

Drop table if exists estado_LineaCompra;
CREATE TABLE estado_LineaCompra
(
   idLineaCompra bigint NOT NULL auto_increment,
   idEstado bigint NOT NULL,
   PRIMARY KEY (idLineaCompra, idEstado ),
   FOREIGN KEY (idLineaCompra) REFERENCES LineaCompra(idLineaCompra) ON DELETE CASCADE,
   FOREIGN KEY (idEstado) REFERENCES estadoPedido(idEstado)
);

Drop table if exists Preguntas;
CREATE TABLE Preguntas (
	idPregunta bigint not null auto_increment,
	texto varchar(225) not null,
	idCliente bigint,
	idProducto bigint,
	primary key (idPregunta),
	foreign key (idCliente) REFERENCES Cliente (idCliente) On delete cascade,
	foreign key (idProducto) REFERENCES Productos(idProducto) ON DELETE NO ACTION
);

Drop table if exists respuestas;
Create table respuestas (
	idRespuesta bigint not null auto_increment,
	texto varchar(225) not null,
	idCliente bigint,
	idPregunta bigint,
	primary key (idRespuesta),
	foreign key (idCliente) REFERENCES Cliente (idCliente) On delete cascade,
	foreign key (idPregunta) REFERENCES Preguntas(idPregunta) ON DELETE NO ACTION
);
	