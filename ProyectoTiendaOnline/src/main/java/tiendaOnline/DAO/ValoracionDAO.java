package tiendaOnline.DAO;

import java.util.List;

import tiendaOnline.Entity.Clientes;
import tiendaOnline.Entity.Productos;
import tiendaOnline.Entity.Valoracion;

public interface ValoracionDAO extends GenericDao<Valoracion>{
	
	public Valoracion findById(long id);

	public List<Valoracion> findByProducto(Productos producto);
	
	public List<Valoracion> findByPuntuacion(long puntuacion);

	public List<Valoracion> getAll();
	
	public List<Valoracion> findByClienteAndProducto(Productos producto, Clientes cliente);

	

}