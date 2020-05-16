package tiendaOnline.DAO;

import java.util.List;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import tiendaOnline.Entity.Clientes;
import tiendaOnline.Entity.Preguntas;
import tiendaOnline.Entity.Respuestas;

@Repository
@Component("RespuestaDAO")
public class RespuestaDAOImpl extends GenericDaoImpl<Respuestas> implements RespuestaDAO {

	@Override
	public List<Respuestas> findAll() {
		@SuppressWarnings("unchecked")
		List<Respuestas> lista = this.em.createQuery("From Respuestas").getResultList();
		if (lista != null) {
			return lista;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Respuestas> findByPreguntas(Preguntas preguntas) {

		List<Respuestas> lista = this.em
				.createQuery("From Respuestas r join fetch r.preguntas p Where p.idPregunta = :id")
				.setParameter("id", preguntas.getIdPregunta()).getResultList();
		if (lista != null) {
			return lista;
		}

		return null;
	}

	@Override
	public List<Respuestas> findByCliente(Clientes cliente) {
		return null;
	}

}
