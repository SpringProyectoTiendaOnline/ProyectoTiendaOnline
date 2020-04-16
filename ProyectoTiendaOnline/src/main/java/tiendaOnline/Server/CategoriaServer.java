package tiendaOnline.Server;

import java.util.List;

import tiendaOnline.Entity.Categoria;

public interface CategoriaServer {

	public Categoria save(Categoria categoria);

	public Categoria edit(Categoria categoria);

	public void delete(Categoria categoria);

	public Categoria findById(long idCategoria);

	public List<Categoria> getAll();

	public Categoria findByProducto(long idProducto);

}
