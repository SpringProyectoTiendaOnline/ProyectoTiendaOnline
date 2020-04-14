package tiendaOnline.Server;

import java.util.HashSet;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tiendaOnline.DAO.ClienteDAO;
import tiendaOnline.Entity.Clientes;
import tiendaOnline.Entity.Rol;

@Transactional
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private ClienteDAO clienteDao;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Clientes cliente = clienteDao.findByEmail(email);
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for (Rol rol : cliente.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(rol.getNombreRol()));
			System.out.println(rol.getNombreRol());
		}

		return new User(cliente.getEmail(), cliente.getPassword(), grantedAuthorities);
	}
}
