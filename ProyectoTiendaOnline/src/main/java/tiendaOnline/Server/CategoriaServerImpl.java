package tiendaOnline.Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	public ProductosDAO productosDao;


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
		categoriaDao.delete(categoria.getIdCategoria());
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

	@Override
	@Transactional
	public Categoria eliminarProductoCateg(long idCategoria, long idProducto) {
		Productos producto = productosDao.find(idProducto);
		Categoria categoria = categoriaDao.find(idCategoria);
		
		for (Productos p: categoria.getProducto()) {
			if (p.getIdProducto() == idProducto) {
				return categoriaDao.eliminarProductoCateg(idCategoria, producto);
			}
		}
		
		return null;
	}

	@Override
	@Transactional
	public Categoria findByNombre(String nombreCategoria) {
		return categoriaDao.findByNombre(nombreCategoria);
	}

	@Override
	@Transactional
	public List<Productos> findCategProductos(long idCategoria) {
		return categoriaDao.findCategProductos(idCategoria);
	}
	
	@Override
	@Transactional
	public Page<Productos> findPaginatedCategProductos(Pageable pageable, long idCategoria) {
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;

		List<Productos> lista = new ArrayList<>();
		List<Productos> listaProducto = categoriaDao.findCategProductos(idCategoria);
		if (lista.size() < startItem) {
			lista = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, listaProducto.size());
			lista = listaProducto.subList(startItem, toIndex);
		}
		
		Page<Productos> productoPage = new PageImpl<Productos>(lista,PageRequest.of(currentPage, pageSize), listaProducto.size());

		return productoPage;
	}

	
	
}
