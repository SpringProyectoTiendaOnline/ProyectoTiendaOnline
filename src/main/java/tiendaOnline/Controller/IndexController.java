package tiendaOnline.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	private int result = 0;

	@GetMapping({ "/", "index" })
	public ModelAndView indice(@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		ModelAndView mav = new ModelAndView();

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(8);

		Page<Productos> productoPage = productoServer.paginadaProducto(PageRequest.of(currentPage - 1, pageSize),
				result, 8);

		int totalPages = productoPage.getTotalPages();

		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			mav.addObject("pageNumbers", pageNumbers);
		}

		result = result + 8;
		mav.addObject("productoPage", productoPage);
		mav.addObject("listaCategoria", categoriaServer.getAll());
		mav.setViewName("indice");
		return mav;
	}

	/**
	 * Iniciar la sessión del usuario
	 * 
	 * @return
	 */
	@GetMapping("/login")
	public String login(Model theModel) {
		theModel.addAttribute("listaCategoria", categoriaServer.getAll());
		return "login";
	}

	/*
	 * @GetMapping("/logout") public ModelAndView handleLogout(HttpServletRequest
	 * request) { ModelAndView mav = new ModelAndView(); HttpSession session =
	 * request.getSession(true);
	 * 
	 * @SuppressWarnings("unchecked") HashMap<Productos, Integer> carrito =
	 * (HashMap<Productos, Integer>) session.getAttribute("sessionCarrito"); if
	 * (carrito != null) { carrito.clear(); } session.setAttribute("sessionCarrito",
	 * carrito); // Cerrar la session session.invalidate();
	 * mav.setViewName("login"); return mav; }
	 */

	@GetMapping("/access-denied")
	public String accessDenied() {
		return "/error/access-denied";
	}

}
