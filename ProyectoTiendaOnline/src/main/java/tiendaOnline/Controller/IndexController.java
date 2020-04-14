package tiendaOnline.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import tiendaOnline.Entity.Clientes;
import tiendaOnline.Entity.Productos;
import tiendaOnline.Server.*;

@Controller
public class IndexController {

	@Autowired
	private ClienteServer ClienteServer;
	@Autowired
	private BancoServer BancoServer;
	@Autowired
	private ProductoServer productoServer;

	@GetMapping("/indice")
	public ModelAndView indice() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("listaProductos", productoServer.getAll());
		mav.setViewName("index");
		return mav;
	}

	/**
	 * Iniciar la sessión del usuario
	 * 
	 * @return
	 */
	@GetMapping({ "/", "/login" })
	public String login() {
		return "login";
	}

	@GetMapping("/logout")
	public ModelAndView handleLogout(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession(true);
		@SuppressWarnings("unchecked")
		HashMap<Productos, Integer> carrito = (HashMap<Productos, Integer>) session.getAttribute("sessionCarrito");
		if (carrito != null) {
			carrito.clear();
		}
		session.setAttribute("sessionCarrito", carrito);
		// Cerrar la session
		session.invalidate();
		mav.setViewName("login");
		return mav;
	}

//	@RequestMapping(method = RequestMethod.POST, value = "/login") public
//	  ModelAndView handleLogin(HttpServletRequest request, Map<String, Object> map)
//	  { ModelAndView mav = new ModelAndView();
//	  
//	  String email = request.getParameter("email"); String password = request.getParameter("password");
//	  
//	  if (email.isEmpty() || password.isEmpty()) { map.put("msg",
//	  "Los campos no pueden ser nulo"); mav.setViewName("login"); 
//	  }
//	  
//	  Clientes cliente = ClienteServer.logIn(email, password);
//	  
//	  // Comprobar si existe el cliente. if (cliente != null) { // si el rol de
//	  cliente == 2; if (cliente.getRoles().iterator().next().getIdRol() == 2) {
//	  List<Clientes> list = ClienteServer.getAll(); mav.addObject("Cliente",
//	  cliente); mav.addObject("listaCliente", list);
//	  mav.setViewName("list-cliente"); } else { // sino cliente normal. List<Banco>
//	  listBanco = BancoServer.findByCliente(cliente); mav.addObject("listaBanco",
//	  listBanco); mav.addObject("Cliente", cliente);
//	  mav.addObject("listaProductos", productoServer.getAll());
//	  mav.setViewName("index"); } }else
//
//	{ // Captura el error map.put("msg",
//	  "Invalid Username o Password"); mav.setViewName("login"); }return mav;
//	}

	// ------------------------------------------ Registrar Cliente
	// ------------------------------------------//

	@GetMapping("/signup")
	public String showForm(Model theModel) {
		Clientes cliente = new Clientes();
		theModel.addAttribute("Cliente", cliente);
		return "signup";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/create-cliente")
	public ModelAndView create_cliente(@ModelAttribute Clientes cliente, BindingResult bindingResult) {
		ModelAndView mav = new ModelAndView();
		List<String> mensaje = new ArrayList<String>();

		if (bindingResult.hasFieldErrors()) {
			for (int i = 0; i < bindingResult.getAllErrors().size(); i++) {
				mensaje.add(bindingResult.getAllErrors().get(i).getDefaultMessage());
			}
			mav.addObject("Error", mensaje);
			mav.addObject("Cliente", cliente);
			mav.setViewName("signup");
		} else {
			Clientes clienteSave = ClienteServer.save(cliente);
			if (clienteSave != null) {
				mav.addObject("Cliente", clienteSave);
				mav.setViewName("perfil-cliente");
			}

		}

		return mav;

	}

	@GetMapping("/access-denied")
	public String accessDenied() {
		return "/error/access-denied";
	}

	/*
	 * public List<String> inputValid(Clientes cliente) { List<String> mensaje = new
	 * ArrayList<String>(); SimpleDateFormat formatter = new
	 * SimpleDateFormat("dd-mm-yyyy"); if (cliente.getEmail().length() == 0) {
	 * mensaje.add("El campo de cliente.getEmail() está vacío. "); } if
	 * (cliente.getPassword().length() == 0 || cliente.getPassword().isEmpty()) {
	 * mensaje.add("El campo de contraseña está vacío."); } if
	 * (cliente.getDireccion().length() == 0 || cliente.getDireccion().isEmpty()) {
	 * mensaje.add("El campo de dirección está vacío."); } if
	 * (cliente.getNombre().length() == 0 || cliente.getNombre().isEmpty()) {
	 * mensaje.add("El campo de cliente.getNombre() está vacío."); } if
	 * (cliente.getApellido().length() == 0 || cliente.getApellido().isEmpty()) {
	 * mensaje.add("El campo de cliente.getApellido() está vacío."); } if
	 * (cliente.getFnacimiento().length() == 0 ||
	 * cliente.getFnacimiento().isEmpty()) {
	 * mensaje.add("El campo de fecha de nacimiento está vacío."); } else { try {
	 * formatter.parse(cliente.getFnacimiento()); } catch (ParseException e) {
	 * mensaje.add("El campo de fecha de nacimiento no es válido."); }
	 * 
	 * }
	 * 
	 * return mensaje;
	 * 
	 * }
	 */

}
