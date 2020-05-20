package tiendaOnline.DAO;



import java.util.List;

import tiendaOnline.Entity.Clientes;

public interface ClienteDAO extends GenericDao<Clientes>{
	
	public Clientes logIn(String email, String password);

	public boolean logOut(Clientes cliente);

	public Clientes findByEmail(String email);

	public List<Clientes> getAll();
	
	public Clientes findById(long id);



}
