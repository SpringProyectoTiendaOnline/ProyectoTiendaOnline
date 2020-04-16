package tiendaOnline.Server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tiendaOnline.DAO.CategoriaDAO;
import tiendaOnline.Entity.Categoria;

@Service
public class CategoriaServerImpl implements CategoriaServer {

	@Autowired
	public CategoriaDAO categoriaDao;

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

}
