package tiendaOnline.Server;

import java.util.List;

import tiendaOnline.Entity.Productos;

public interface ProductoServer {

	public Productos save(Productos producto);

	public Productos findById(long id);

	public List<Productos> findByCodProducto(long codProducto);

	public List<Productos> findByNombre(String valor);

	public void delete(Productos producto);

	public Productos update(Productos producto);

	public List<Productos> getAll();

}