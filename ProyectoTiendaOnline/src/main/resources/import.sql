

insert into Rol (idRol, nombreRol ) values (1, 'ROL_Usuario');
insert into ROL (idRol, nombreRol ) values (2,'ROL_ADMIN');

insert into estadoPedido (idEstado, estado ) values (1, 'Entregado');
insert into estadoPedido (idEstado, estado ) values (2, 'Devolucion');

INSERT INTO Cliente (nombre,apellido,fnacimiento,direccion,email,password) VALUES('admin','admin','08-07-1999','C/Jose Antonio 2', 'root@root.com','$2a$10$hVhPwe1CPZjUWox7VIjJdeOcCP48eyd8trpMXw/bn/qSWxXhv/Xoe');
INSERT INTO Clientes_rol VALUES  (1,2);


INSERT INTO Productos (codProducto,titulo,descripcion,precio,descuento,stock) VALUES(123,'x1','o1',123,0,100);
INSERT INTO Productos (codProducto,titulo,descripcion,precio,descuento,stock) VALUES(1223,'x2','o2',123,0,100);
INSERT INTO Productos (codProducto,titulo,descripcion,precio,descuento,stock) VALUES(1243,'x3','o3',123,0,100);
INSERT INTO Productos (codProducto,titulo,descripcion,precio,descuento,stock) VALUES(1253,'x4','o4',123,0,100);
INSERT INTO Productos (codProducto,titulo,descripcion,precio,descuento,stock) VALUES(1263,'x5','o5',123,0,100);
INSERT INTO Productos (codProducto,titulo,descripcion,precio,descuento,stock) VALUES(1273,'x6','o6',123,0,100);
INSERT INTO Productos (codProducto,titulo,descripcion,precio,descuento,stock) VALUES(1283,'x7','o7',123,0,100);
INSERT INTO Productos (codProducto,titulo,descripcion,precio,descuento,stock) VALUES(1293,'x8','o8',123,0,100);
INSERT INTO Productos (codProducto,titulo,descripcion,precio,descuento,stock) VALUES(12123,'x9','o9',123,0,100);
