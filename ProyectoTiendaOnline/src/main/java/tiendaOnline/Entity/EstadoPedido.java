package tiendaOnline.Entity;

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
import javax.persistence.Table;

@Entity
@Table(name = "estadoPedido")
public class EstadoPedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idEstado")
	private int idEstado;

	@Column(name = "estado")
	private String estado;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "estado_LineaCompra", joinColumns = @JoinColumn(name = "idEstado"), inverseJoinColumns = @JoinColumn(name = "idLineaCompra"))
	private Set<LineaCompra> lineaCompra;

	public int getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Set<LineaCompra> getLineaCompra() {
		return lineaCompra;
	}

	public void setLineaCompra(Set<LineaCompra> lineaCompra) {
		this.lineaCompra = lineaCompra;
	}

	@Override
	public String toString() {
		return "EstadoPedido [idEstado=" + idEstado + ", estado=" + estado + ", lineaCompra=" + lineaCompra + "]";
	}
	
	
	
	

}
