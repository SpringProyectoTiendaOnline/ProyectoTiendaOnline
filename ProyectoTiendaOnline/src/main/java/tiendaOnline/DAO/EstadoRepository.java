package tiendaOnline.DAO;

import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import tiendaOnline.Entity.EstadoPedido;


@Repository
@Component("estadoRepository")
public interface EstadoRepository extends JpaRepository<EstadoPedido, Integer>{
	
}

