package tiendaOnline.Server;

import java.util.List;

import tiendaOnline.Entity.Clientes;

public interface ClienteServer {

	public Clientes save(Clientes Cliente);

	public Clientes logIn(String email, String password);

	public boolean logOut(Clientes cliente);

	public Clientes findByEmail(String email);

	public Clientes findById(long id);

	public List<Clientes> getAll();

	public Clientes update(Clientes cliente);

	public void delete(Clientes cliente);
	

}
