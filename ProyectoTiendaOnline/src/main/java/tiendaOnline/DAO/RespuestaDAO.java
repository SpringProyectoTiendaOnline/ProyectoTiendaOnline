package tiendaOnline.DAO;

import java.util.List;

import tiendaOnline.Entity.Clientes;
import tiendaOnline.Entity.Preguntas;
import tiendaOnline.Entity.Respuestas;

public interface RespuestaDAO extends GenericDao<Respuestas>{
	
	public List<Respuestas> findAll();  

	public List<Respuestas> findByPreguntas(Preguntas preguntas);
	
	public List<Respuestas> findByCliente(Clientes cliente);
	
	

}
