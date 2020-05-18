package tiendaOnline.Server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tiendaOnline.DAO.BancoDAO;
import tiendaOnline.Entity.Banco;
import tiendaOnline.Entity.Clientes;

@Service
@Transactional
public class BancoServerImpl implements BancoServer {

	@Autowired
	private BancoDAO bancoDAO;

	@Override
	@Transactional
	public Banco save(Banco banco) {
		return bancoDAO.create(banco);
	}

	@Override
	@Transactional
	public void delete(long id) {
		bancoDAO.delete(id);
	}

	@Override
	@Transactional
	public Banco update(Banco banco) {
		return bancoDAO.update(banco);
	}

	@Override
	@Transactional
	public List<Banco> getAll() {
		return bancoDAO.getAll();
	}

	@Override
	@Transactional
	public List<Banco> findByCliente(Clientes cliente) {
		return bancoDAO.findByCliente(cliente);
	}

	@Override
	@Transactional
	public Banco findById(long id) {
		return bancoDAO.findById(id);
	}

	@Override
	@Transactional
	public Banco findByNumTarjeta(Banco banco) {
		return bancoDAO.findByNumTarjeta(banco);
	}

}
