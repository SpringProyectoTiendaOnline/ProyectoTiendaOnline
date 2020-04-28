package tiendaOnline.DAO;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import tiendaOnline.Entity.Clientes;
import tiendaOnline.Entity.Productos;
import tiendaOnline.Entity.Valoracion;

@Repository
@Component("ValoracionDAO")
public class ValoracionDAOImpl extends GenericDaoImpl<Valoracion> implements ValoracionDAO {
	
	
	@Autowired
	ProductosDAO productoDao;
	

	@Override
	public Valoracion findById(long id) {
		try {
			Query query = this.em.createQuery("Select v From Valoraciones v Where idValoracion =:id");
			query.setParameter("id", id);
			Valoracion valoracion = (Valoracion) query.getSingleResult();
			if (valoracion != null) {
				return valoracion;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Valoracion> findByProducto(Productos producto) {
		@SuppressWarnings("unchecked")
		List<Valoracion> lista = this.em
				.createQuery("From Valoraciones v join fetch v.producto p Where p.idProducto = :id")
				.setParameter("id", producto.getIdProducto()).getResultList();
		if (lista != null) {
			return lista;
		}

		return null;
	}
	

	@Override
	public List<Valoracion> findByPuntuacion(long puntuacion) {
		@SuppressWarnings("unchecked")
		List<Valoracion> lista = this.em
				.createQuery("From Valoraciones v Where puntuacion = :puntuacion")
				.setParameter("puntuacion", puntuacion).getResultList();
		if (lista != null) {
			return lista;
		}

		return null;
	}

	@Override
	public List<Valoracion> getAll() {
		@SuppressWarnings("unchecked")
		List<Valoracion> lista = this.em.createQuery("From Valoraciones").getResultList();
		if (lista != null) {
			return lista;
		}
		return null;
	}

	@Override
	public List<Valoracion> findByClienteAndProducto(Productos producto, Clientes cliente) {
		@SuppressWarnings("unchecked")
		List<Valoracion> lista = this.em
				.createQuery("From Valoracion v Where idProducto = :id and idCliente = :idCli")
				.setParameter("id", producto.getIdProducto()).setParameter("idCli", cliente.getIdCliente()).getResultList();
		if (lista != null) {
			return lista;
		}

		return null;
	}

	
	
	
	

}