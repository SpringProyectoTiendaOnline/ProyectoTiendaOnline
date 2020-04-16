package tiendaOnline.DAO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import tiendaOnline.Entity.Categoria;
import tiendaOnline.Entity.Productos;

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
				System.out.println(categoria);
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
		
		Categoria categoria = this.find(idCategoria);
		Set<Productos> lProducto = new HashSet<Productos>();
		lProducto.add(producto);
		categoria.setProducto(lProducto);
		System.out.println("Save Categoria : " + categoria );

		this.em.merge(categoria);
		this.em.refresh(categoria);
		this.em.flush();
		this.em.clear();
		
		
		return categoria;
		
	}

}
