package tiendaOnline.Server;

import java.util.List;

import tiendaOnline.Entity.Banco;
import tiendaOnline.Entity.Clientes;


public interface BancoServer {

	public Banco save(Banco banco);

	public void delete(long id);

	public Banco update(Banco banco);
	
	public List<Banco> getAll();
	
	public List<Banco> findByCliente(Clientes cliente);
	
	public Banco findById(long id);
	
	public Banco findByNumTarjeta(Banco banco);


}
