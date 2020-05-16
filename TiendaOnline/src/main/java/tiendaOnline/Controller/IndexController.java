package tiendaOnline.Controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import tiendaOnline.Entity.Productos;
import tiendaOnline.Server.*;

@Controller
public class IndexController {

	@Autowired
	private ProductoServer productoServer;

	@Autowired
	private ImagenProductoServer imagenServer;

	@Autowired
	private CategoriaServer categoriaServer;

	@GetMapping({ "/", "index" })
	public ModelAndView indice() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("listaCategoria", categoriaServer.getAll());
		mav.addObject("listaProductos", productoServer.getAll());
		mav.addObject("imagenServer", imagenServer);
		mav.setViewName("indice");
		return mav;
	}

	/**
	 * Iniciar la sessión del usuario
	 * 
	 * @return
	 */
	@GetMapping(value = {"/login"})
	public String staticResource(Model theModel) {
		theModel.addAttribute("listaCategoria", categoriaServer.getAll());
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
