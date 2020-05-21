package tiendaOnline.Entity;

import java.io.Serializable;

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
	private static final long serialVersionUID = -6344565775520192666L;

	/**
	 * 
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idImagen")
	private Long idImagen;

	@Column(name = "urlImagen")
	private String urlImagen;

	@Lob
	@Column(name = "imagen")
	private byte[] image;

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

	public static long getSerialversionuid() {

		return serialVersionUID;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "ImagenProducto [idImagen=" + idImagen + ", urlImagen=" + urlImagen + ", producto=" + producto + "]";
	}

}
