package tiendaOnline.DAO;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import tiendaOnline.Entity.Categoria;

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
		Categoria categoria = (Categoria) this.em
				.createQuery("From Categoria c join fetch producto p where p.idProducto = :id")
				.setParameter("id", idProducto).getSingleResult();

		if (categoria != null) {
			return categoria;
		}
		return null;
	}

}
