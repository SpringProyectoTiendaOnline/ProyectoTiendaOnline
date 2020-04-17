package tiendaOnline.DAO;


import java.util.List;
import java.util.Set;
import javax.persistence.NoResultException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import tiendaOnline.Entity.Categoria;
import tiendaOnline.Entity.Productos;
import tiendaOnline.Utilidades.Utilidades;

@Repository
@Component("CategoriaDAO")
public class CategoriaDAOImpl extends GenericDaoImpl<Categoria> implements CategoriaDAO {

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
			n.printStackTrace();
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

}
