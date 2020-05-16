package tiendaOnline.Entity;

import java.io.Serializable;
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
@Table(name = "Rol")
public class Rol implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2086256903789241667L;

	/**
	 * 
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idRol")
	private int idRol;

	@Column(name = "nombreRol")
	private String nombreRol;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "Clientes_rol", joinColumns = @JoinColumn(name = "idRol"), inverseJoinColumns = @JoinColumn(name = "idCliente"))
	private Set<Clientes> clientes;

	public int getIdRol() {
		return idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	public String getNombreRol() {
		return nombreRol;
	}

	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}

	public Set<Clientes> getClientes() {
		return clientes;
	}

	public void setClientes(Set<Clientes> clientes) {
		this.clientes = clientes;
	}

}
