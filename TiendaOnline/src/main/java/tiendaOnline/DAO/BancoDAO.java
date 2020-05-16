package tiendaOnline.DAO;

import java.util.List;

import tiendaOnline.Entity.Banco;
import tiendaOnline.Entity.Clientes;

public interface BancoDAO extends GenericDao<Banco>{


	public List<Banco> getAll();

	public List<Banco> findByCliente(Clientes cliente);

	public Banco findById(long id);

}
