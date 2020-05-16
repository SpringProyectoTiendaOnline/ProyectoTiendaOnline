package tiendaOnline.Server;

import java.util.List;

import tiendaOnline.Entity.Clientes;
import tiendaOnline.Entity.Productos;
import tiendaOnline.Entity.Valoracion;

public interface ValoracionServer {

	public Valoracion save(Valoracion valoracion);

	public List<Valoracion> findByProducto(Productos producto);

	public Valoracion find(Valoracion valoracion);

	public Valoracion findById(long id);
	
	public List<Valoracion> findByProductoAndCliente(Productos producto, Clientes cliente);

	public double obtenerValoracionMediaPorProducto(Productos producto);


}