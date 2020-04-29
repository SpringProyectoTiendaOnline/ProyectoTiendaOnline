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
import javax.persistence.Table;

@Entity
@Table(name = "Respuestas", uniqueConstraints = @UniqueConstraint(columnNames = { "idRespuesta" }))
public class Respuestas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7052076385812026463L;
	/**
	 * 
	 */
	@Id
	@GeneratedValue
	@Column(name = "idRespuesta", nullable = false)
	private long idRespuesta;
	@Column(name = "texto")
	@NotNull(message = "Debes especificar el texto de respuesta")
	private String texto;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idCliente", nullable = true)
	private Clientes clientes;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idPregunta", nullable = true)
	private Preguntas preguntas;

	public Respuestas(@NotNull(message = "Debes especificar el texto de respuesta") String texto, Clientes clientes,
			Preguntas preguntas) {
		super();
		this.texto = texto;
		this.clientes = clientes;
		this.preguntas = preguntas;
	}

	public Respuestas() {

	}

	public long getIdRespuesta() {
		return idRespuesta;
	}

	public void setIdRespuesta(long idRespuesta) {
		this.idRespuesta = idRespuesta;
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

	public Preguntas getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(Preguntas preguntas) {
		this.preguntas = preguntas;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Respuestas [idRespuesta=" + idRespuesta + ", texto=" + texto + ", clientes=" + clientes + ", preguntas="
				+ preguntas + "]";
	}

}