

insert into Rol (idRol, nombreRol ) values (1, 'ROL_Usuario');
insert into ROL (idRol, nombreRol ) values (2,'ROL_ADMIN');

insert into estadoPedido (idEstado, estado ) values (1, 'Entregado');
insert into estadoPedido (idEstado, estado ) values (2, 'Devolucion');

INSERT INTO Cliente (nombre,apellido,fnacimiento,direccion,email,password) VALUES('admin','admin','08-07-1999','C/Jose Antonio 2', 'root@root.com','$2a$10$hVhPwe1CPZjUWox7VIjJdeOcCP48eyd8trpMXw/bn/qSWxXhv/Xoe');
INSERT INTO Clientes_rol VALUES  (1,2);

INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo,valoracionMedia) VALUES(1,'ProductoPrueba',1,2,5,'1ProductoPrueba', 5);
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo,valoracionMedia) VALUES(2,'ProductoPrueba',2,2,5,'2ProductoPrueba', 4);
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo,valoracionMedia) VALUES(3,'ProductoPrueba',2,2,5,'3ProductoPrueba', 3);
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo,valoracionMedia) VALUES(4,'ProductoPrueba',4,2,5,'4ProductoPrueba', 2);
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo,valoracionMedia) VALUES(5,'ProductoPrueba',5,2,8,'5ProductoPrueba', 1);
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo,valoracionMedia) VALUES(6,'ProductoPrueba',2,2,6,'6ProductoPrueba', 1);
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo,valoracionMedia) VALUES(7,'ProductoPrueba',6,2,3,'7ProductoPrueba', 2);
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo,valoracionMedia) VALUES(8,'ProductoPrueba',7,2,4,'8ProductoPrueba', 2);
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo,valoracionMedia) VALUES(9,'ProductoPrueba',8,2,6,'9ProductoPrueba', 4);
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo,valoracionMedia) VALUES(10,'ProductoPrueba',9,2,3,'10ProductoPrueba', 5);
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo,valoracionMedia) VALUES(11,'ProductoPrueba',2,2,8,'11ProductoPrueba', 4);
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo,valoracionMedia) VALUES(12,'ProductoPrueba',1,2,3,'12ProductoPrueba', 3);
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo,valoracionMedia) VALUES(13,'ProductoPrueba',1,2,2,'13ProductoPrueba', 2);
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo,valoracionMedia) VALUES(14,'ProductoPrueba',1,2,1,'14ProductoPrueba', 1);
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo,valoracionMedia) VALUES(15,'ProductoPrueba',1,2,9,'15ProductoPrueba', 2);
INSERT INTO Productos (codProducto,descripcion,descuento,precio,stock,titulo,valoracionMedia) VALUES(16,'ProductoPrueba',1,2,8,'16ProductoPrueba', 3);

INSERT INTO Categoria (nombre_Categoria) VALUES('CategoriaPrueba1');
INSERT INTO Categoria (nombre_Categoria) VALUES('CategoriaPrueba2');

INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (1,1);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (1,2);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (1,3);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (1,4);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (1,5);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (1,6);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (1,7);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (1,8);

INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (2,9);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (2,10);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (2,11);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (2,12);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (2,13);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (2,14);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (2,15);
INSERT INTO producto_categoria (id_Categoria, idProducto) VALUES (2,16);









