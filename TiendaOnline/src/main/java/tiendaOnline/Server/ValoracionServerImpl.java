package tiendaOnline.Server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tiendaOnline.DAO.ValoracionDAO;
import tiendaOnline.Entity.Clientes;
import tiendaOnline.Entity.Productos;
import tiendaOnline.Entity.Valoracion;

@Service
public class ValoracionServerImpl implements ValoracionServer {
	
	@Autowired
	private ValoracionDAO valoracionDAO;

	@Override
	@Transactional
	public Valoracion save(Valoracion valoracion) {
		return valoracionDAO.create(valoracion);
	}

	@Override
	@Transactional
	public List<Valoracion> findByProducto(Productos producto) {
		return valoracionDAO.findByProducto(producto);
	}

	@Override
	@Transactional
	public Valoracion find(Valoracion valoracion) {
		return valoracionDAO.find(valoracion);
	}

	@Override
	@Transactional
	public Valoracion findById(long id) {
		return valoracionDAO.findById(id);
	}

	@Override
	@Transactional
	public List<Valoracion> findByProductoAndCliente(Productos producto, Clientes cliente) {
		return valoracionDAO.findByClienteAndProducto(producto, cliente);
	}
	
	@Override
	@Transactional
	public double obtenerValoracionMediaPorProducto(Productos producto) {
		return valoracionDAO.obtenerValoracionMediaPorProducto(producto);
	}

}