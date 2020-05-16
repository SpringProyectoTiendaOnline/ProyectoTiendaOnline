package tiendaOnline.Entity;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import javax.persistence.Table;

@Entity
@Table(name = "Productos", uniqueConstraints = @UniqueConstraint(columnNames = { "idProducto" }))
public class Productos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6667711637025232024L;
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idProducto", nullable = false)
	private long idProducto;
	@Column(name = "titulo")
	@NotNull(message = "Debes especificar el título del producto")
	private String titulo;
	@Column(name = "descripcion")
	@NotNull(message = "Debes especificar la descripción del producto")
	private String descripcion;
	
	@Column(name = "codProducto", unique = true)
	private long codProducto;
	
	@Column(name = "precio")
	@NotNull(message = "Debes especificar el precio del producto")
	private float precio;
	@Column(name = "descuento")
	@NotNull(message = "Debes especificar el descuento del producto")
	private float descuento;
	@Column(name = "stock")
	@NotNull(message = "Debes especificar cantidad de stock de producto")
	private long stock;
	
	@Column(name = "valoracionMedia")
	private double valoracionMedia;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ImagenProducto> imagen = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "Producto_Categoria", joinColumns = @JoinColumn(name = "idProducto"), inverseJoinColumns = @JoinColumn(name = "id_Categoria"))
	private Set<Categoria> categoria = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Valoracion> valoraciones = new HashSet<>();

	public Productos() {

	}

	public Productos(long idProducto, @NotNull(message = "Debes especificar el título del producto") String titulo,
			@NotNull(message = "Debes especificar la descripción del producto") String descripcion, long codProducto,
			@NotNull(message = "Debes especificar el precio del producto") float precio,
			@NotNull(message = "Debes especificar el descuento del producto") float descuento,
			@NotNull(message = "Debes especificar cantidad de stock de producto") long stock) {
		super();
		this.idProducto = idProducto;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.codProducto = codProducto;
		this.precio = precio;
		this.descuento = descuento;
		this.stock = stock;
	}

	public long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public long getCodProducto() {
		return codProducto;
	}

	public void setCodProducto(long codProducto) {
		this.codProducto = codProducto;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public float getDescuento() {
		return descuento;
	}

	public void setDescuento(float descuento) {
		this.descuento = descuento;
	}

	public long getStock() {
		return stock;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}

	public Set<Categoria> getCategoria() {
		return categoria;
	}

	public void setCategoria(Set<Categoria> categoria) {
		this.categoria = categoria;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Set<ImagenProducto> getImagen() {
		return imagen;
	}

	public void setImagen(Set<ImagenProducto> imagen) {
		this.imagen = imagen;
	}

	public Set<Valoracion> getValoraciones() {
		return valoraciones;
	}

	public void setValoraciones(Set<Valoracion> valoraciones) {
		this.valoraciones = valoraciones;
	}
	
	
	public double getValoracionMedia() {
		return valoracionMedia;
	}

	public void setValoracionMedia(double valoracionMedia) {
		this.valoracionMedia = valoracionMedia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Productos other = (Productos) obj;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;

		if (idProducto == 0) {
			if (other.idProducto != 0)
				return false;
		} else if (idProducto != other.idProducto)
			return false;

		if (codProducto == 0) {
			if (other.codProducto != 0)
				return false;
		} else if (codProducto != other.codProducto)
			return false;

		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "Productos [idProducto=" + idProducto + ", titulo=" + titulo + ", descripcion=" + descripcion
				+ ", codProducto=" + codProducto + ", precio=" + precio + ", descuento=" + descuento + ", stock="
				+ stock + ", valoracionMedia=" + valoracionMedia +"]";
	}

	

}