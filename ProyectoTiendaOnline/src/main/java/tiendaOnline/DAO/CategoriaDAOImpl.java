package tiendaOnline.DAO;

import java.util.List;
import java.util.Set;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import tiendaOnline.Entity.Categoria;
import tiendaOnline.Entity.Productos;
import tiendaOnline.Utilidades.Utilidades;

@Repository
@Component("CategoriaDAO")
public class CategoriaDAOImpl extends GenericDaoImpl<Categoria> implements CategoriaDAO {

    private static final Logger logger = LoggerFactory.getLogger("CategoriaDAO.class");

	@Override
	public List<Categoria> getAll() {
		@SuppressWarnings("unchecked")
		List<Categoria> list = this.em.createQuery("From Categoria").getResultList();
		if (list != null) {
			return list;
		}
		return null;
	}

	@Override
	public Categoria findProducto(long idProducto) {
		try {
			Categoria categoria = (Categoria) this.em
					.createQuery("From Categoria c join fetch c.producto p where p.idProducto = :id")
					.setParameter("id", idProducto).getSingleResult();

			if (categoria != null) {
				return categoria;
			}
		} catch (NoResultException n) {
			logger.error("Error", n);
			return null;

		}

		return null;
	}

	@Override
	public Categoria saveProductoCateg(long idCategoria, Productos producto) {

		Categoria categ = this.find(idCategoria);
		List<Productos> list = findCategProductos(idCategoria);
		Set<Productos> lProducto = Utilidades.convertListToSet(list);

		lProducto.add(producto);
		categ.setProducto(lProducto);
		System.out.println("Save Categoria : " + categ);

		this.em.merge(categ);

		return categ;

	}

	@Override
	public Categoria eliminarProductoCateg(long idCategoria, Productos producto) {
		Categoria categ = this.find(idCategoria);

		List<Productos> list = findCategProductos(idCategoria);
		Set<Productos> lProducto = Utilidades.convertListToSet(list);
		lProducto.remove(producto);

		categ.setProducto(lProducto);
		System.out.print("Remove :" + categ);

		return categ;
	}

	@Override
	public List<Productos> findCategProductos(long idCategoria) {
		@SuppressWarnings("unchecked")
		List<Productos> lProducto = this.em
				.createQuery("Select p From Productos p join fetch p.categoria c Where c.idCategoria = :id")
				.setParameter("id", idCategoria).getResultList();

		if (lProducto != null) {
			return lProducto;
		}
		return null;
	}

	@Override
	public List<Productos> findCategProductosPaginada(long idCategoria, int index, int limit) {
		@SuppressWarnings("unchecked")
		List<Productos> lProducto = this.em
				.createQuery("Select p From Productos p join fetch p.categoria c Where c.idCategoria = :id")
				.setParameter("id", idCategoria).setFirstResult(index).setMaxResults(limit).getResultList();

		if (lProducto != null) {
			return lProducto;
		}
		return null;
	}


}
