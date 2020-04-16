package tiendaOnline.Server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tiendaOnline.DAO.CategoriaDAO;
import tiendaOnline.DAO.ProductosDAO;
import tiendaOnline.Entity.Categoria;
import tiendaOnline.Entity.Productos;

@Service
@Transactional
public class CategoriaServerImpl implements CategoriaServer {

	@Autowired
	public CategoriaDAO categoriaDao;

	@Autowired
	public ProductosDAO productoDao;

	@Override
	@Transactional
	public Categoria save(Categoria categoria) {
		return categoriaDao.create(categoria);
	}

	@Override
	@Transactional
	public Categoria edit(Categoria categoria) {
		return categoriaDao.update(categoria);
	}

	@Transactional
	@Override
	public void delete(Categoria categoria) {
		categoriaDao.delete(categoria);
	}

	@Override
	@Transactional
	public Categoria findById(long idCategoria) {
		return categoriaDao.find(idCategoria);
	}

	@Transactional
	@Override
	public List<Categoria> getAll() {
		return categoriaDao.getAll();
	}

	@Transactional
	@Override
	public Categoria findByProducto(long idProducto) {
		return categoriaDao.findProducto(idProducto);
	}

	@Override
	@Transactional
	public Categoria saveProductoCateg(long idCategoria, Productos producto) {
		boolean existe = false;
		Categoria categoria = categoriaDao.find(idCategoria);

		for (Productos p : categoria.getProducto()) {
			if (p.getIdProducto() == producto.getIdProducto()) {
				existe = true;
			}
		}
		
		if (!existe) {
			Categoria cat = categoriaDao.saveProductoCateg(idCategoria, producto);
			return cat;
		}

		return null;
	}

}
