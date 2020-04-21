package tiendaOnline.Server;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tiendaOnline.DAO.*;
import tiendaOnline.Dto.ProductosDto;
import tiendaOnline.Entity.*;

@Service
@Transactional
public class ProductoServerImpl implements ProductoServer {

	@Autowired
	private ProductosDAO productoDAO;

	@Override
	@Transactional
	public Productos save(Productos producto) {
		return productoDAO.create(producto);
	}

	@Override
	@Transactional
	public Productos findById(long id) {
		return productoDAO.find(id);

	}

	@Override
	@Transactional
	public void delete(Productos producto) {
		File file = new File(System.getProperty("user.home") + producto.getImagen());
		if (file.exists()) {
			file.delete();
		}
		productoDAO.delete(producto.getIdProducto());
	}

	@Override
	@Transactional
	public List<Productos> getAll() {
		return productoDAO.getAll();
	}

	@Override
	@Transactional
	public Productos update(Productos producto) {
		return productoDAO.update(producto);
	}

	@Override
	public List<Productos> findByCodProducto(long codProducto) {
		return productoDAO.findByCodProducto(codProducto);
	}

	@Override
	public List<Productos> findByNombre(String nombreProducto) {
		return productoDAO.findByNombre(nombreProducto);
	}

	@Override
	public List<ProductosDto> findByNombreAndCodProducto(String nombreCodProducto) {
		return productoDAO.findByNombreAndCodProducto(nombreCodProducto);
	}
}