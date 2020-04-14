package tiendaOnline.DAO;

import java.util.List;

import tiendaOnline.Entity.Compra;
import tiendaOnline.Entity.LineaCompra;
import tiendaOnline.Entity.Productos;

public interface LineaDeCompraDAO extends GenericDao<LineaCompra>{

	
	public List<LineaCompra> getAll();

	public List<LineaCompra> findByProducto(Productos productos);
	
	public List<LineaCompra> findByCompra(Compra compra);
	
	public LineaCompra findById(long id);
}
