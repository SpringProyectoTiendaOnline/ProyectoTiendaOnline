package tiendaOnline.Server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tiendaOnline.DAO.RespuestaDAO;
import tiendaOnline.Entity.Preguntas;
import tiendaOnline.Entity.Respuestas;

@Service
public class RespuestaServerImpl implements RespuestaServer {

	@Autowired
	private RespuestaDAO respuestaDAO;

	@Override
	@Transactional
	public Respuestas save(Respuestas respuesta) {
		return respuestaDAO.create(respuesta);
	}

	@Override
	@Transactional
	public List<Respuestas> findByPreguntas(Preguntas preguntas) {
		return respuestaDAO.findByPreguntas(preguntas);
	}

	@Override
	@Transactional
	public Respuestas find(Respuestas respuesta) {
		return respuestaDAO.find(respuesta);
	}

	@Override
	@Transactional
	public void delete(Respuestas respuesta) {
		 respuestaDAO.delete(respuesta);
	}

}
