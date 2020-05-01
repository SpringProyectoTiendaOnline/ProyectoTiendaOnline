package tiendaOnline.DAO;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import tiendaOnline.Entity.Clientes;

@Repository
@Component("ClienteDAO")
public class ClienteDAOImpl extends GenericDaoImpl<Clientes> implements ClienteDAO {

	@Override
	public Clientes logIn(String email, String password) {
		try {
			Query query = this.em.createQuery("From Clientes c Where c.email =:email AND c.password =:password");
			query.setParameter("email", email);
			query.setParameter("password", password);
			Clientes cliente = (Clientes) query.getSingleResult();
			if (cliente != null) {
				return cliente;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean logOut(Clientes cliente) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Clientes findByEmail(String email) {
		Query query = this.em.createQuery("From Clientes c Where c.email =:email");
		query.setParameter("email", email);
		Clientes cliente = (Clientes) query.getSingleResult();
		if (cliente != null) {
			return cliente;
		}
		return null;
	}

	@Override
	public List<Clientes> getAll() {
		Query query = this.em.createQuery("From Clientes");
		@SuppressWarnings("unchecked")
		List<Clientes> listaCliente = query.getResultList();
		if (listaCliente != null) {
			return listaCliente;
		}
		return null;
	}

	@Override
	public Clientes findById(long id) {
		try {
			Query query = this.em.createQuery("From Clientes c Where c.idCliente =:id");
			query.setParameter("id", id);
			Clientes cliente = (Clientes) query.getSingleResult();
			if (cliente != null) {
				return cliente;
			}
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		return null;
	}

}
