package tiendaOnline.Entity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

//session.invalite()

@Entity
@Table(name = "Compra", uniqueConstraints = @UniqueConstraint(columnNames = { "idCompra" }))
public class Compra implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3112985360137648044L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCompra", nullable = false, unique = true)
	private long idCompra;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idCliente", nullable = true)
	private Clientes clientes;

	@Column(name = "fechaCompra")
	private Date fechaCompra;

	public Compra(long idCompra, Clientes clientes, Date fechaCompra) {
		super();
		this.idCompra = idCompra;
		this.clientes = clientes;
		this.fechaCompra = fechaCompra;
	}

	public Compra() {

	}

	public long getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(long idCompra) {
		this.idCompra = idCompra;
	}

	public Clientes getClientes() {
		return clientes;
	}

	public void setClientes(Clientes clientes) {
		this.clientes = clientes;
	}

	public Date getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	@Override
	public String toString() {
		return "Compra [idCompra=" + idCompra + ", clientes=" + clientes + ", fechaCompra=" + fechaCompra + "]";
	}

}
