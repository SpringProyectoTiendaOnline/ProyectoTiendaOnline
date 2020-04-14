package tiendaOnline.DAO;

import java.util.Date;
import java.util.List;

import tiendaOnline.Entity.Clientes;
import tiendaOnline.Entity.Compra;

public interface CompraDAO extends GenericDao<Compra>{

	public List<Compra> getAll();

	public List<Compra> findByCliente(Clientes cliente);

	public List<Compra> findByFecha(Date fechaCompra);

	public Compra findById(long id);

}
