package tiendaOnline.Server;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tiendaOnline.DAO.ClienteDAO;
import tiendaOnline.DAO.RolRepository;
import tiendaOnline.Entity.Clientes;
import tiendaOnline.Entity.Rol;

@Transactional
@Service
public class ClienteServerImpl implements ClienteServer {

	@Autowired
	private ClienteDAO ClienteDao;
	@Autowired
	private RolRepository rolRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public Clientes save(Clientes Cliente) {
		Set<Rol> roles = new HashSet<Rol>();
		Rol rol = rolRepository.getOne(1);
		roles.add(rol);
		Cliente.setRoles(roles);
		Cliente.setPassword(passwordEncoder.encode(Cliente.getPassword()));
		return ClienteDao.create(Cliente);
	}

	@Transactional
	public Clientes logIn(String email, String password) {
		return ClienteDao.logIn(email, password);
	}

	@Transactional
	public boolean logOut(Clientes cliente) {
		return ClienteDao.logOut(cliente);
	}

	@Transactional
	public Clientes findByEmail(String email) {
		return ClienteDao.findByEmail(email);
	}

	@Override
	public Clientes findById(long id) {
		return ClienteDao.findById(id);
	}

	@Override
	public List<Clientes> getAll() {
		return ClienteDao.getAll();
	}

	@Override
	@Transactional
	public Clientes update(Clientes cliente) {
		return ClienteDao.update(cliente);
	}

	@Override
	public void delete(Clientes cliente) {
		ClienteDao.delete(cliente.getIdCliente());
	}

}
