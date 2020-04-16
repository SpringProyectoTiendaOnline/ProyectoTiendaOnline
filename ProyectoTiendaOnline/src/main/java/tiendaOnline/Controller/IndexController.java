package tiendaOnline.Controller;


import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

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

	@GetMapping("/access-denied")
	public String accessDenied() {
		return "/error/access-denied";
	}

}
