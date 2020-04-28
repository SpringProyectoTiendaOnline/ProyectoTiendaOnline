package tiendaOnline.Entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import javax.persistence.GenerationType;

@Entity
@Table(name = "Cliente", uniqueConstraints = @UniqueConstraint(columnNames = { "idCliente" }))
public class Clientes implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCliente", nullable = false, unique = true)
	private long idCliente;
	
	
	@NotNull @Size(min=2, max=15, message="La longitud de apellido debe ser entre 2 - 15")
	@Column(name = "nombre", nullable = false)
	@NotEmpty(message = "Debes especificar el nombre")
	private String nombre;
	
	@NotNull @Size(min=2, max=15, message="La longitud de apellido debe ser entre 2 - 15")
	@Column(name = "apellido", nullable = false)
	@NotEmpty(message = "Debes especificar el apellido")
	private String apellido;
	
	@Column(name = "email", nullable = false, unique = true)
	@NotEmpty(message = "Debes especificar el email")
	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~  -]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@" + "(?:[a-z0-9](?:[a-z0-9-]*"
			+ "[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "La dirección debe tener la forma email@email.com")
	@Email(message = "Email should be valid")
	private String email;
	
	@Column(name = "fnacimiento", nullable = false)
	@NotEmpty(message = "Debes especificar la fecha de nacimiento")
	private String fnacimiento;
	
	@Column(name = "password", nullable = false)
	@NotNull @Size(min=6, max=80, message="La longitud de apellido debe ser entre 6 - 80")
	@NotEmpty(message = "Debes especificar la contraseña")
	private String password;
	
	@NotNull @Size(min=8, max=100, message="La longitud de direccion debe ser entre 10 - 100")
	@Column(name = "direccion", nullable = false)
	@NotEmpty(message = "Debes especificar la dirección")
	private String direccion;
	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "Clientes_rol", 
	joinColumns = @JoinColumn(name = "idCliente"),
	inverseJoinColumns = @JoinColumn(name = "idRol"))
	private Set<Rol> roles = new HashSet<>();
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Valoracion> valoraciones = new HashSet<>();

	public Clientes() {

	}

	public Clientes(String nombre, String apellido, String email, String fnacimiento, String password,
			String direccion) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.fnacimiento = fnacimiento;
		this.password = password;
		this.direccion = direccion;
	}

	public long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFnacimiento() {
		return fnacimiento;
	}

	public void setFnacimiento(String fnacimiento) {
		this.fnacimiento = fnacimiento;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}


	@Override
	public String toString() {
		return "Clientes [idCliente=" + idCliente + ", nombre=" + nombre
				+ ", apellido=" + apellido + ", email=" + email + ", fnacimiento=" + fnacimiento + ", password="
				+ password + ", direccion=" + direccion + ", roles=" + roles + "]";
	}

	
	
}
