package tiendaOnline.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Query;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import tiendaOnline.Dto.ProductosDto;
import tiendaOnline.Entity.Productos;

@Repository
@Component("ProductoDAO")
public class ProductosDAOImpl extends GenericDaoImpl<Productos> implements ProductosDAO {

	// Convertir de objeto a otro objeto para simplificar los datos
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Productos findById(long id) {
		try {
			Query query = this.em.createQuery("Select p From Productos p Where idProducto =:id");
			query.setParameter("id", id);
			Productos producto = (Productos) query.getSingleResult();
			if (producto != null) {
				return producto;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Productos> findByCodProducto(long codProducto) {
		try {
			Query query = this.em.createQuery("Select p From Productos p Where p.codProducto like %" + ":codigo" + "%");
			// Query query = sess().createQuery("from UserProfile where firstName LIKE
			// :name")
			// .setParameter("name", "%"+name+"%");

			query.setParameter("codigo", codProducto);
			@SuppressWarnings("unchecked")
			List<Productos> producto = query.getResultList();

			if (!producto.isEmpty()) {
				return producto;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Productos> getAll() {
		List<Productos> listaProducto = new ArrayList<Productos>();

		try {
			listaProducto = this.em.createQuery("Select p From Productos p").getResultList();
			if (listaProducto != null) {
				return listaProducto;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Productos> findByNombre(String nombre) {

		try {
			Query query = this.em.createQuery("Select p From Productos p Where p.titulo like '%" + ":nombre" + "%'");
			query.setParameter("nombre", nombre);
			@SuppressWarnings("unchecked")
			List<Productos> producto = query.getResultList();
			if (!producto.isEmpty()) {
				return producto;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<ProductosDto> findByNombreAndCodProducto(String nombreCodProducto) {

		Query query = this.em
				.createQuery("From Productos p where concat(p.codProducto,' ',p.titulo) like :nc");
		query.setParameter("nc", "%" + nombreCodProducto + "%");
		
		System.err.println(nombreCodProducto);
		List<Productos> lProducto = query.getResultList();

		if (lProducto != null) {
			return lProducto.stream().map(this::convertToProductoDto).collect(Collectors.toList());
		}

		return null;
	}

	private ProductosDto convertToProductoDto(Productos producto) {
		ProductosDto productoDto = modelMapper.map(producto, ProductosDto.class);
		return productoDto;
	}

}
