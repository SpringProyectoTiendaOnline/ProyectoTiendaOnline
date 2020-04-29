package tiendaOnline.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "Valoraciones", uniqueConstraints = @UniqueConstraint(columnNames = { "idValoracion" }))
public class Valoracion implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 6337203157334932315L;
	/**
	 * 
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idValoracion", nullable = false, unique = true)
	private long idValoracion;
	@ManyToOne
	@JoinColumn(name = "idProducto")
	private Productos producto;
	
	@Column(name = "puntuacion")
	private long puntuacion;
	
	@ManyToOne
	@JoinColumn(name = "idCliente")
	private Clientes cliente;
	
	
	

	public Clientes getCliente() {
		return cliente;
	}

	public void setCliente(Clientes cliente) {
		this.cliente = cliente;
	}

	public Valoracion (long puntuacion, Productos producto) {
		this.producto= producto;
		this.puntuacion=puntuacion;
	}
	
	public Valoracion () {
		
	}
	public long getIdValoracion() {
		return idValoracion;
	}
	public void setIdValoracion(long idValoracion) {
		this.idValoracion = idValoracion;
	}
	public Productos getProducto() {
		return producto;
	}
	public void setProducto(Productos producto) {
		this.producto = producto;
	}
	public long getPuntuacion() {
		return puntuacion;
	}
	public void setPuntuacion(long puntuacion) {
		this.puntuacion = puntuacion;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

}