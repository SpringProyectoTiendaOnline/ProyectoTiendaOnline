

insert into Rol (idRol, nombreRol ) values (1, 'ROL_Usuario');
insert into ROL (idRol, nombreRol ) values (2,'ROL_ADMIN');

insert into estadoPedido (idEstado, estado ) values (1, 'Entregado');
insert into estadoPedido (idEstado, estado ) values (2, 'Devolucion');

INSERT INTO Cliente (nombre,apellido,fnacimiento,direccion,email,password) VALUES('admin','admin','08-07-1999','C/Jose Antonio 2', 'root@root.com','$2a$10$hVhPwe1CPZjUWox7VIjJdeOcCP48eyd8trpMXw/bn/qSWxXhv/Xoe');
INSERT INTO Clientes_rol VALUES  (1,2);


