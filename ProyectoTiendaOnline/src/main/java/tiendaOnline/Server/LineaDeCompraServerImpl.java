package tiendaOnline.Server;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tiendaOnline.DAO.LineaDeCompraDAO;
import tiendaOnline.Entity.Compra;
import tiendaOnline.Entity.LineaCompra;
import tiendaOnline.Entity.Productos;

@Service
@Transactional
public class LineaDeCompraServerImpl implements LineaDeCompraServer {

	@Autowired
	private LineaDeCompraDAO lineaCompra;

	@Override
	@Transactional
	public LineaCompra save(LineaCompra linea) {
		return lineaCompra.create(linea);
	}

	@Override
	@Transactional
	public LineaCompra update(LineaCompra linea) {
		return lineaCompra.update(linea);
	}

	@Override
	@Transactional
	public void delete(long id) {
		lineaCompra.delete(id);
	}

	@Override
	@Transactional
	public List<LineaCompra> getAll() {
		return lineaCompra.getAll();
	}

	@Override
	@Transactional
	public List<LineaCompra> findByProducto(Productos productos) {
		return lineaCompra.findByProducto(productos);
	}

	@Override
	@Transactional
	public List<LineaCompra> findByCompra(Compra compra) {
		return lineaCompra.findByCompra(compra);
	}

	@Override
	@Transactional
	public LineaCompra findById(long id) {
		return lineaCompra.findById(id);
	}

}
