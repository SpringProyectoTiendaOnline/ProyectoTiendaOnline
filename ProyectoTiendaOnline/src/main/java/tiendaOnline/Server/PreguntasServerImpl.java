package tiendaOnline.Server;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tiendaOnline.DAO.PreguntaDAO;
import tiendaOnline.Entity.Preguntas;
import tiendaOnline.Entity.Productos;
@Service
@Transactional
public class PreguntasServerImpl implements PreguntaServer {

	@Autowired
	private  PreguntaDAO preguntasDAO;
	
	@Override
	@Transactional
	public List<Preguntas> findAll() {
		return preguntasDAO.findAll();
	}

	@Override
	@Transactional
	public Preguntas save(Preguntas preguntas) {
		return preguntasDAO.create(preguntas);
	}

	@Override
	@Transactional
	public Preguntas update(Preguntas preguntas) {
		return preguntasDAO.update(preguntas);
	}

	@Override
	@Transactional
	public void delete(Preguntas preguntas) {
		 preguntasDAO.delete(preguntas);
	}

	@Override
	@Transactional
	public Preguntas findById(long idPregunta) {
		return preguntasDAO.findById(idPregunta);
	}

	@Override
	@Transactional
	public List<Preguntas> findByProductos(Productos productos) {
		return preguntasDAO.findByProducto(productos);
	}

}
