package tiendaOnline.Server;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tiendaOnline.DAO.CompraDAO;
import tiendaOnline.Entity.Clientes;
import tiendaOnline.Entity.Compra;

@Service
@Transactional
public class CompraServerImpl implements CompraServer{
	
	@Autowired
	private CompraDAO compraDao;

	@Override
	@Transactional
	public Compra save(Compra compra) {
		return compraDao.create(compra);
	}

	@Override
	@Transactional
	public Compra update(Compra compra) {
		return compraDao.update(compra);
	}

	@Override
	@Transactional
	public void delete(long id) {
		compraDao.delete(id);
	}

	@Override
	@Transactional
	public List<Compra> getAll() {
		return compraDao.getAll();
	}

	@Override
	@Transactional
	public List<Compra> findByCliente(Clientes cliente) {
		return compraDao.findByCliente(cliente);
	}

	@Override
	@Transactional
	public List<Compra> findByFecha(Date fechaCompra) {
		return compraDao.findByFecha(fechaCompra);
	}

	@Override
	@Transactional
	public Compra findById(long id) {
		return compraDao.findById(id);
	}

}
