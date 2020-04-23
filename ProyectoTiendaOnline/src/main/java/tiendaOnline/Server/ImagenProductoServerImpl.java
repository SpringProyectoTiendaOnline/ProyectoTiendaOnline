package tiendaOnline.Server;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tiendaOnline.DAO.ImagenProductoDAO;
import tiendaOnline.Entity.ImagenProducto;
import tiendaOnline.Entity.Productos;

@Service
public class ImagenProductoServerImpl implements ImagenProductoServer {

	@Autowired
	private ImagenProductoDAO imagenProductoDao;

	@Override
	@Transactional
	public ImagenProducto save(ImagenProducto imagenProducto) {
		return imagenProductoDao.create(imagenProducto);
	}

	@Override
	@Transactional
	public ImagenProducto update(ImagenProducto imagenProducto) {
		return imagenProductoDao.update(imagenProducto);
	}

	@Override
	@Transactional
	public void delete(ImagenProducto imagenProducto) {
		imagenProductoDao.delete(imagenProducto);
	}

	@Override
	@Transactional
	public List<ImagenProducto> findByProducto(Productos producto) {
		return imagenProductoDao.findByProducto(producto);
	}

	@Override
	@Transactional
	public List<ImagenProducto> getAll() {
		return imagenProductoDao.getAll();
	}

	@Transactional
	@Override
	public ImagenProducto findByUrl(String url) {
		return imagenProductoDao.findByUrl(url);
	}

	@Override
	@Transactional
	public ImagenProducto findByImagen(byte[] imagen) {
		return imagenProductoDao.findByImagen(imagen);
	}

	@Override
	@Transactional
	public ImagenProducto findById(ImagenProducto imagenProducto) {
		return imagenProductoDao.find(imagenProducto);
	}

}
