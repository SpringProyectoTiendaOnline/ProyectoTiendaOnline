package tiendaOnline.DAO;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import tiendaOnline.Entity.Compra;
import tiendaOnline.Entity.LineaCompra;
import tiendaOnline.Entity.Productos;

@Repository
@Component("LineaDeCompraDAO")
public class LineaDeCompraDAOImpl extends GenericDaoImpl<LineaCompra> implements LineaDeCompraDAO {

	@Override
	public List<LineaCompra> getAll() {
		try {
			@SuppressWarnings("unchecked")
			List<LineaCompra> lista = this.em.createQuery("Select l From LineaCompra l").getResultList();

			if (lista != null) {
				return lista;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<LineaCompra> findByProducto(Productos productos) {
		try {
			@SuppressWarnings("unchecked")
			List<LineaCompra> lista = this.em
					.createQuery("From LineaCompra c join fetch c.productos p Where p.idProducto = :id")
					.setParameter("id", productos.getIdProducto()).getResultList();

			if (lista != null) {
				return lista;
			}
		} catch (Exception e) {
			return null;
		}

		return null;
	}

	// "From Compra c join fetch c.clientes cl Where cl.idCliente = :id")
	@Override
	public List<LineaCompra> findByCompra(Compra compra) {
		@SuppressWarnings("unchecked")
		List<LineaCompra> lista = this.em.createQuery("From LineaCompra l join fetch l.compra c Where c.idCompra =:id")
				.setParameter("id", compra.getIdCompra()).getResultList();

		if (lista != null) {
			return lista;
		}
		return null;
	}

	@Override
	public LineaCompra findById(long id) {
		LineaCompra linea = (LineaCompra) this.em.createQuery("From LineaCompra l Where idLineaCompra =:id")
				.setParameter("id", id).getSingleResult();

		if (linea != null) {
			return linea;
		}

		return null;
	}

}
