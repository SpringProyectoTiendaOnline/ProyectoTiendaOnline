package tiendaOnline.Server;

import java.util.List;

import tiendaOnline.Entity.Preguntas;
import tiendaOnline.Entity.Respuestas;

public interface RespuestaServer {
	
	public Respuestas save(Respuestas respuesta);
	
	public List<Respuestas> findByPreguntas(Preguntas preguntas);
	
	public Respuestas find(Respuestas respuesta);
	
}
