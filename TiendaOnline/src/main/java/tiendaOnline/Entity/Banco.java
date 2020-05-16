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
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Banco", uniqueConstraints = @UniqueConstraint(columnNames = { "idBanco" }))
public class Banco implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -805111769637747874L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idBanco", nullable = false, unique = true)
	private long idBanco;
	@Column(name = "nombre")
	@NotNull(message = "Debes especificar el nombre de Bancaria")
	private String nombre;
	@Column(name = "numTarjeta")
	@NotNull(message = "Debes especificar el número de la tarjeta")
	private long numTarjeta;
	@Column(name = "titular")
	@NotNull(message = "Debes especificar el titular de bancaria")
	private String titular;
	@Column(name = "codSeguridad")
	@NotNull(message = "Debes especificar el número de seguridad")
	private int codSeguridad;
	@Column(name = "dirFactura")
	@NotNull(message = "Debes especificar la dirección de Factura")
	private String dirFactura;
	@ManyToOne()
	@JoinColumn(name = "idCliente", nullable = false)
	private Clientes cliente;

	public Banco(String nombre, long numTarjeta, String titular, int codSeguridad, String dirFactura, Clientes cliente) {
		this.nombre = nombre;
		this.numTarjeta = numTarjeta;
		this.titular = titular;
		this.codSeguridad = codSeguridad;
		this.dirFactura = dirFactura;
		this.cliente = cliente;
	}

	public Banco() {

	}

	public long getIdBanco() {
		return idBanco;
	}

	public void setIdBanco(long idBanco) {
		this.idBanco = idBanco;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getNumTarjeta() {
		return numTarjeta;
	}

	public void setNumTarjeta(long numTarjeta) {
		this.numTarjeta = numTarjeta;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public int getCodSeguridad() {
		return codSeguridad;
	}

	public void setCodSeguridad(int codSeguridad) {
		this.codSeguridad = codSeguridad;
	}

	public String getDirFactura() {
		return dirFactura;
	}

	public void setDirFactura(String dirFactura) {
		this.dirFactura = dirFactura;
	}

	public Clientes getCliente() {
		return cliente;
	}

	public void setCliente(Clientes cliente) {
		this.cliente = cliente;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Banco [idBanco=" + idBanco + ", nombre=" + nombre + ", numTarjeta=" + numTarjeta + ", titular="
				+ titular + ", codSeguridad=" + codSeguridad + ", dirFactura=" + dirFactura + ", cliente=" + cliente
				+ "]";
	}

	
}
