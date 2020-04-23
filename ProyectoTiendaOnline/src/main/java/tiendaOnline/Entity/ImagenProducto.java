package tiendaOnline.Entity;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ImagenProducto", uniqueConstraints = @UniqueConstraint(columnNames = { "idImagen" }))
public class ImagenProducto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idImagen")
	private Long idImagen;

	@Lob
	@Column(name = "imagen")
	private byte[] imagen;

	@Column(name = "urlImagen")
	private String urlImagen;

	@ManyToOne
	@JoinColumn(name = "idProducto", nullable = true)
	private Productos producto;

	public ImagenProducto() {

	}

	public Long getIdImagen() {
		return idImagen;
	}

	public void setIdImagen(Long idImagen) {
		this.idImagen = idImagen;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public String getUrlImagen() {
		return urlImagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}

	public Productos getProducto() {
		return producto;
	}

	public void setProducto(Productos producto) {
		this.producto = producto;
	}

	@Override
	public String toString() {
		return "ImagenProducto [idImagen=" + idImagen + ", imagen=" + Arrays.toString(imagen) + ", urlImagen="
				+ urlImagen + ", producto=" + producto + "]";
	}

	

}
