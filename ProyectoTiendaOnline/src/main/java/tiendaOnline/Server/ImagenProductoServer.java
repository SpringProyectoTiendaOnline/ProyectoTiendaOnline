package tiendaOnline.Server;

import java.util.List;

import tiendaOnline.Entity.ImagenProducto;
import tiendaOnline.Entity.Productos;

public interface ImagenProductoServer {

	public ImagenProducto save(ImagenProducto imagenProducto);

	public ImagenProducto update(ImagenProducto imagenProducto);

	public void delete(ImagenProducto imagenProducto);
	
	public ImagenProducto findById(long idImagen);

	public List<ImagenProducto> findByProducto(Productos producto);

	public List<ImagenProducto> getAll();

	public ImagenProducto findByUrl(String url);

	public ImagenProducto findByImagen(byte[] imagen);

}
