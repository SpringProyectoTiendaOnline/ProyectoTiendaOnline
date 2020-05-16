package tiendaOnline.DAO;

import java.util.ArrayList;


import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import tiendaOnline.Entity.Banco;
import tiendaOnline.Entity.Clientes;

@Repository
@Component("BancoDAO")
public class BancoDAOImpl extends GenericDaoImpl<Banco> implements BancoDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Banco> getAll() {
		List<Banco> lBanco = new ArrayList<Banco>();

		Query query = this.em.createQuery("From Banco");
		lBanco = query.getResultList();

		if (lBanco != null) {
			return lBanco;
		}

		return null;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Banco> findByCliente(Clientes cliente) {
		List<Banco> listaBanco = new ArrayList<Banco>();

		Query query = this.em.createQuery("From Banco b Where b.cliente.idCliente =:id");
		query.setParameter("id", cliente.getIdCliente());
		listaBanco = query.getResultList();

		if (!listaBanco.isEmpty()) {
			return listaBanco;
		}

		return null;
	}

	@Override
	public Banco findById(long id) {

		Query query = this.em.createQuery("From Banco b Where b.idBanco =:id");
		query.setParameter("id", id);
		Banco banco = (Banco) query.getSingleResult();
		if (banco != null) {
			return banco;
		}

		return null;
	}

}
