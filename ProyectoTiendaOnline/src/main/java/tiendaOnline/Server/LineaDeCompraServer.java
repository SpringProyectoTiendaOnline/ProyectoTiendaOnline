package tiendaOnline.Server;

import java.util.List;

import tiendaOnline.Entity.Compra;
import tiendaOnline.Entity.LineaCompra;
import tiendaOnline.Entity.Productos;

public interface LineaDeCompraServer {
	
	public LineaCompra save(LineaCompra linea);
	
	public LineaCompra update(LineaCompra linea);
	
	public void delete(long id);

	public List<LineaCompra> getAll();

	public List<LineaCompra> findByProducto(Productos productos);
	
	public List<LineaCompra> findByCompra(Compra compra);

	public LineaCompra findById(long id);

}
