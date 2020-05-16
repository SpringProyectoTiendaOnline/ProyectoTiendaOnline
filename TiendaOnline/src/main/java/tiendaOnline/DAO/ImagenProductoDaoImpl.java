package tiendaOnline.DAO;

import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import tiendaOnline.Entity.ImagenProducto;
import tiendaOnline.Entity.Productos;

@Repository
@Component("ImagenProductoDAO")
public class ImagenProductoDaoImpl extends GenericDaoImpl<ImagenProducto> implements ImagenProductoDAO {

	private Logger logger = LoggerFactory.getLogger(ImagenProductoDaoImpl.class);

	@Override
	public List<ImagenProducto> findByProducto(Productos producto) {
		@SuppressWarnings("unchecked")
		List<ImagenProducto> lImagen = this.em
				.createQuery("From ImagenProducto i join fetch i.producto p Where p.idProducto = :id")
				.setParameter("id", producto.getIdProducto()).getResultList();
		if (lImagen != null) {
			return lImagen;
		}

		return null;
	}

	@Override
	public List<ImagenProducto> getAll() {
		@SuppressWarnings("unchecked")
		List<ImagenProducto> lImagen = this.em.createQuery("From ImagenProducto").getResultList();
		if (lImagen != null) {
			return lImagen;
		}
		return null;
	}

	@Override
	public ImagenProducto findByUrl(String url) {
		try {
			ImagenProducto imagen = (ImagenProducto) this.em.createQuery("From ImagenProducto Where urlImagen =:url")
					.setParameter("url", url).getSingleResult();
			if (imagen != null) {
				return imagen;
			}
		} catch (NoResultException no) {
			no.printStackTrace();
			logger.error("Error : " + no);
		}

		return null;
	}

	@Override
	public ImagenProducto findByImagen(byte[] imagen) {
		try {
			ImagenProducto ima = (ImagenProducto) this.em.createQuery("From ImagenProducto Where imagen =:imagen")
					.setParameter("imagen", imagen).getSingleResult();
			if (imagen != null) {
				return ima;
			}
		} catch (NoResultException no) {
			no.printStackTrace();
			logger.error("Error : " + no);
		}

		return null;
	}

}
