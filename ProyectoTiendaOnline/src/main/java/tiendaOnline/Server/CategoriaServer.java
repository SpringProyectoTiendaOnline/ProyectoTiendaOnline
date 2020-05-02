package tiendaOnline.Server;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tiendaOnline.Entity.Categoria;
import tiendaOnline.Entity.Productos;

public interface CategoriaServer {

	public Categoria save(Categoria categoria);

	public Categoria edit(Categoria categoria);

	public void delete(Categoria categoria);

	public Categoria findById(long idCategoria);
	
	public Categoria findByNombre(String nombreCategoria);

	public List<Categoria> getAll();

	public Categoria findByProducto(long idProducto);
	
	public Categoria saveProductoCateg(long idCategoria, Productos producto);

	public Categoria eliminarProductoCateg(long idCategoria, long idProducto);
	
	public List<Productos> findCategProductos(long idCategoria);

	public List<Productos> findCategProductosPaginada(long idCategoria, int index, int limit);

	public Page<Productos> findPaginatedCategProductos(Pageable pageable, long idCategoria, int index, int limit);


}
