package tiendaOnline.DAO;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import tiendaOnline.Entity.Clientes;
import tiendaOnline.Entity.Preguntas;
import tiendaOnline.Entity.Productos;

@Repository
@Component("PreguntaDAO")
public class PreguntasDAOImpl extends GenericDaoImpl<Preguntas> implements PreguntaDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Preguntas> findAll() {
		List<Preguntas> lista = this.em.createQuery("Select p From Preguntas p").getResultList();
		if (lista != null) {
			return lista;
		}
		return null;
	}

	@Override
	public List<Preguntas> findByProducto(Productos producto) {
		@SuppressWarnings("unchecked")
		List<Preguntas> preguntas = this.em.createQuery("Select p From Preguntas p join fetch p.productos pro Where pro.idProducto = :id")
				.setParameter("id", producto.getIdProducto()).getResultList();
		if (preguntas != null) {
			return preguntas;
		}
		return null;
	}


	@Override
	public Preguntas findById(long idPregunta) {
		Preguntas pregunta = (Preguntas) this.em.createQuery("Select p From Preguntas p Where p.idPregunta = :id")
				.setParameter("id", idPregunta).getSingleResult();
		if (pregunta != null) {
			return pregunta;
		}
		return null;
	}

}
