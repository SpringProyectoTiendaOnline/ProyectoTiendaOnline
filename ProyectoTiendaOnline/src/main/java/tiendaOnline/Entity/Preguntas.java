package tiendaOnline.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Table;

@Entity
@Table(name = "Preguntas", uniqueConstraints = @UniqueConstraint(columnNames = { "idPregunta" }))
public class Preguntas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1628363364763521008L;
	/**
	 * 
	 */
	@Id
	@GeneratedValue
	@Column(name = "idPregunta", nullable = false)
	private long idPregunta;
	@Column(name = "texto")
	@NotNull(message = "Debes especificar el texto")
	private String texto;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idCliente", nullable = true)
	private Clientes clientes;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idProducto", nullable = true)
	private Productos productos;

	public Preguntas(@NotNull(message = "Debes especificar el texto") String texto, Clientes clientes,
			Productos productos) {
		super();
		this.texto = texto;
		this.clientes = clientes;
		this.productos = productos;
	}
	
	public Preguntas() {
		
	}

	public long getIdPregunta() {
		return idPregunta;
	}

	public void setIdPregunta(long idPregunta) {
		this.idPregunta = idPregunta;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Clientes getClientes() {
		return clientes;
	}

	public void setClientes(Clientes clientes) {
		this.clientes = clientes;
	}

	public Productos getProductos() {
		return productos;
	}

	public void setProductos(Productos productos) {
		this.productos = productos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Preguntas [idPregunta=" + idPregunta + ", texto=" + texto + ", clientes=" + clientes + ", productos="
				+ productos + "]";
	}

	
}