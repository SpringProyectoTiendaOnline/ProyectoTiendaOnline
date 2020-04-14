package tiendaOnline.Server;


import java.util.List;


import tiendaOnline.Entity.Preguntas;
import tiendaOnline.Entity.Productos;


public interface PreguntaServer{

	public List<Preguntas> findAll();
	
	public Preguntas save(Preguntas preguntas);
	
	public Preguntas update(Preguntas preguntas);
	
	public void delete(Preguntas preguntas);
	
	public Preguntas findById(long idPregunta);
	
	public List<Preguntas> findByProductos(Productos productos);
	
	
	
}
