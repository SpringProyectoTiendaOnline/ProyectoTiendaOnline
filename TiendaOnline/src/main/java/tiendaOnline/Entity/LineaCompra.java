package tiendaOnline.Entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "LineaCompra", uniqueConstraints = @UniqueConstraint(columnNames = { "idLineaCompra" }))
public class LineaCompra implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2288073451832347647L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idLineaCompra", nullable = false, unique = true)
	private long idLineaCompra;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idCompra")
	private Compra compra;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idProducto", nullable = true)
	private Productos productos;
	@Column(name = "cantidad")
	private long cantidad;
	@Column(name = "precioTotal")
	private Float precioTotal;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "estado_LineaCompra", joinColumns = @JoinColumn(name = "idLineaCompra"), inverseJoinColumns = @JoinColumn(name = "idEstado"))
	private Set<EstadoPedido> estado = new HashSet<>();

	public LineaCompra(long idLineaCompra, Compra compra, Productos productos, long cantidad, Float precioTotal) {
		super();
		this.idLineaCompra = idLineaCompra;
		this.compra = compra;
		this.productos = productos;
		this.cantidad = cantidad;
		this.precioTotal = precioTotal;
	}

	public LineaCompra() {
		// TODO Auto-generated constructor stub
	}

	public long getIdLineaCompra() {
		return idLineaCompra;
	}

	public void setIdLineaCompra(long idLineaCompra) {
		this.idLineaCompra = idLineaCompra;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public Productos getProductos() {
		return productos;
	}

	public void setProductos(Productos productos) {
		this.productos = productos;
	}

	public long getCantidad() {
		return cantidad;
	}

	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}

	public Float getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(Float precioTotal) {
		this.precioTotal = precioTotal;
	}

	public Set<EstadoPedido> getEstado() {
		return estado;
	}

	public void setEstado(Set<EstadoPedido> estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "LineaCompra [idLineaCompra=" + idLineaCompra + ", compra=" + compra + ", productos=" + productos
				+ ", cantidad=" + cantidad + ", precioTotal=" + precioTotal + "]";
	}

}
