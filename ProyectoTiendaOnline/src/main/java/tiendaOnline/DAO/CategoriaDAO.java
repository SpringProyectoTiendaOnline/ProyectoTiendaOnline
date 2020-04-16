package tiendaOnline.DAO;

import java.util.List;

import tiendaOnline.Entity.Categoria;

public interface CategoriaDAO extends GenericDao<Categoria> {
	
	public List<Categoria> getAll();
	
	public Categoria findProducto(long idProducto);
	
}
