package tiendaOnline.DAO;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import tiendaOnline.Entity.Clientes;
import tiendaOnline.Entity.Compra;

@Repository
@Component("CompraDAO")
public class CompraDaoImpl extends GenericDaoImpl<Compra> implements CompraDAO {

	@Override
	public List<Compra> getAll() {
		@SuppressWarnings("unchecked")
		List<Compra> listaCompra = this.em.createQuery("From Compra").getResultList();
		if (listaCompra != null) {
			return listaCompra;
		}
		return null;
	}

	@Override
	public List<Compra> findByCliente(Clientes cliente) {
		try {
			@SuppressWarnings("unchecked")
			List<Compra> listaCompra = this.em.createQuery("From Compra c join fetch c.clientes cl Where  cl.idCliente = :id")
					.setParameter("id", cliente.getIdCliente()).getResultList();
			

			if (listaCompra != null) {
				System.err.println(listaCompra.toString());
				return listaCompra;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	@Override
	public List<Compra> findByFecha(Date fechaCompra) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Compra findById(long id) {
		Compra compra = (Compra) this.em.createQuery("From Compra c Where c.idCompra =:id").setParameter("id", id)
				.getSingleResult();

		if (compra != null) {
			return compra;
		}

		return null;
	}

}
