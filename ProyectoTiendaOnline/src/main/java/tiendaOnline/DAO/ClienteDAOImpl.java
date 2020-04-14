package tiendaOnline.DAO;

import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
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
		Query query = this.em.createQuery("From Clientes c Where c.idCliente =:id");
		query.setParameter("id", id);
		Clientes cliente = (Clientes) query.getSingleResult();
		if (cliente != null) {
			return cliente;
		}
		return null;
	}

//	
//	public Cliente save(Cliente Cliente) {
//		Session session = null;
//
//		try {
//			session = Utilidades.getSessionFactory().openSession();
//			session.beginTransaction();
//			if (Cliente != null) {
//				Cliente.setTipoCliente(1);
//				long id = (Long) session.save(Cliente);
//				System.out.println("Cliente : " + id);
//			}
//			session.getTransaction().commit();
//		} catch (HibernateException h) {
//			h.printStackTrace();
//		} finally {
//			if (session != null) {
//				session.close();
//			}
//		}
//		return Cliente;
//	}
//
//	public Cliente logIn(String email, String password) {
//		Session session = null;
//		Cliente cliente = new Cliente();
//
//		try {
//			session = Utilidades.getSessionFactory().openSession();
//			session.beginTransaction();
//
//			Query query = session.createQuery("From Cliente c Where c.email =:email AND c.password =:password");
//			query.setParameter("email", email);
//			query.setParameter("password", password);
//
//			cliente = (Cliente) query.getSingleResult();
//
//			if (cliente == null) {
//				return null;
//			} else {
//				return cliente;
//			}
//		} catch (Exception h) {
//			h.printStackTrace();
//		} finally {
//			if (session != null) {
//				session.close();
//			}
//		}
//		return cliente;
//	}
//
//	public boolean logOut(Cliente cliente) {
//		return false;
//	}
//
//	public Cliente findByEmail(String email) {
//		Session session = null;
//		Cliente cliente = new Cliente();
//		try {
//			session = Utilidades.getSessionFactory().openSession();
//			session.beginTransaction();
//
//			Query query = session.createQuery("From Cliente Where email =:email");
//			query.setParameter("email", email);
//
//			cliente = (Cliente) query.getSingleResult();
//			if (cliente != null) {
//				return cliente;
//			}
//		} catch (HibernateException h) {
//			h.printStackTrace();
//		} finally {
//			if (session != null) {
//				session.close();
//			}
//		}
//		return null;
//	}
//
//	@Override
//	public Cliente findById(long id) {
//		Session session = null;
//		Cliente cliente = new Cliente();
//		try {
//			session = Utilidades.getSessionFactory().openSession();
//			session.beginTransaction();
//
//			Query query = session.createQuery("From Cliente Where idCliente =:id");
//			query.setParameter("id", id);
//
//			cliente = (Cliente) query.getSingleResult();
//			if (cliente != null) {
//				return cliente;
//			}
//		} catch (HibernateException h) {
//			h.printStackTrace();
//		} finally {
//			if (session != null) {
//				session.close();
//			}
//		}
//		return null;
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Cliente> getAll() {
//		Session session = null;
//		List<Cliente> lcliente = new ArrayList<Cliente>();
//
//		try {
//			session = Utilidades.getSessionFactory().openSession();
//			session.beginTransaction();
//
//			Query query = session.createQuery("Select c From Cliente c");
//			lcliente = query.getResultList();
//
//			if (lcliente != null) {
//				return lcliente;
//			}else {
//				return null;
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (session != null) {
//				session.close();
//			}
//		}
//
//		return null;
//	}
//
//	@Override
//	public Cliente update(Cliente cliente) {
//		Session session = null;
//		try {
//			session = Utilidades.getSessionFactory().openSession();
//			session.beginTransaction();
//			session.update(cliente);
//			session.getTransaction().commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (session != null) {
//				session.close();
//			}
//		}
//		return cliente;
//	}
//
//	@Override
//	public boolean delete(Cliente cliente) {
//		Session session = null;
//		boolean eliminado = false;
//		try {
//			session = Utilidades.getSessionFactory().openSession();
//			session.beginTransaction();
//			session.delete(cliente);
//
//			session.getTransaction().commit();
//			eliminado = true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			eliminado = false;
//		} finally {
//			if (session != null) {
//				session.close();
//			}
//		}
//
//		return eliminado;
//	}

}
