package tiendaOnline.DAO;


import java.util.List;
import tiendaOnline.Entity.Preguntas;
import tiendaOnline.Entity.Productos;


public interface PreguntaDAO extends GenericDao<Preguntas>{

	public List<Preguntas> findAll();
	
	public List<Preguntas> findByProducto(Productos producto);
	
	
	public Preguntas findById(long idPregunta);

	
}
