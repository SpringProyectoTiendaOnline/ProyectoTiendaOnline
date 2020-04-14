package tiendaOnline.Server;

import java.util.Date;
import java.util.List;

import tiendaOnline.Entity.Clientes;
import tiendaOnline.Entity.Compra;

public interface CompraServer {

	public Compra save(Compra compra);

	public Compra update(Compra compra);

	public void delete(long id);

	public List<Compra> getAll();

	public List<Compra> findByCliente(Clientes cliente);

	public List<Compra> findByFecha(Date fechaCompra);

	public Compra findById(long id);

}
