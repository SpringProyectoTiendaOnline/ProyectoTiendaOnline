package tiendaOnline.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tiendaOnline.Dto.ProductosDto;
import tiendaOnline.Entity.Categoria;
import tiendaOnline.Entity.Clientes;
import tiendaOnline.Entity.Productos;
import tiendaOnline.Server.CategoriaServer;
import tiendaOnline.Server.ClienteServer;
import tiendaOnline.Server.ProductoServer;

@Controller
@RequestMapping(value = "/Categoria")
public class CategoriaController {

	@Autowired
	private CategoriaServer categoriaServer;

	@Autowired
	private ProductoServer productoServer;

	@Autowired
	private ClienteServer clienteServer;

//para la busqueda paginada
	private int result = 0;

	// Listar las categorias
	@RequestMapping(method = RequestMethod.GET, value = "/lista-categoria")
	public ModelAndView listaCategoria() {
		ModelAndView mav = new ModelAndView();
		Categoria categoria = new Categoria();
		List<Categoria> listaCategoria = categoriaServer.getAll();
		mav.addObject("categoria", categoria);
		mav.addObject("listaCategoria", listaCategoria);
		mav.setViewName("categoria/list-categoria");
		return mav;

	}

	// muestra la lista de categorias al usuario
	@RequestMapping(method = RequestMethod.GET, value = "/lista-categoriaUser/{idCliente}")
	public ModelAndView listaCategoriaUser(@PathVariable("idCliente") long idCliente) {
		ModelAndView mav = new ModelAndView();
		Categoria categoria = new Categoria();
		mav.addObject("Cliente", clienteServer.findById(idCliente));
		List<Categoria> listaCategoria = categoriaServer.getAll();
		mav.addObject("categoria", categoria);
		mav.addObject("listaCategoria", listaCategoria);
		mav.setViewName("categoria/list-categoriaUser");
		result = 0;
		return mav;

	}

	// muestra de cuatro en cuatro los productos por categoria
	@GetMapping("/list-4product-user/{idCategoria}")
	public ModelAndView list4ProductosForClients(@PathVariable("idCategoria") long idCategoria,
			HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();
		mav.addObject("categoria", categoriaServer.findById(idCategoria));

		mav.addObject("listaProductos", categoriaServer.findCategProductosPaginada(idCategoria, result, 4));
		mav.setViewName("categoria/list-4product-user");
		result = result + 4;
		return mav;
	}

	// Crear la categoria
	@RequestMapping(method = RequestMethod.POST, value = "/create-categoria")
	public ModelAndView crear_Categoria(@ModelAttribute("categoria") @Valid Categoria categoria, BindingResult result) {
		ModelAndView mav = new ModelAndView();
		if (result.hasErrors()) {
			List<Categoria> listaCategoria = categoriaServer.getAll();
			mav.addObject("listaCategoria", listaCategoria);
			mav.setViewName("categoria/list-categoria");
			mav.addObject("Mensaje", result.getAllErrors());
		} else {
			Categoria categSave = categoriaServer.save(categoria);
			if (categSave != null) {
				List<Categoria> listaCategoria = categoriaServer.getAll();
				mav.addObject("listaCategoria", listaCategoria);
				mav.setViewName("categoria/list-categoria");
			}
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{idCategoria}")
	public ModelAndView perfilCategoria(@PathVariable("idCategoria") long idCategoria) {
		ModelAndView mav = new ModelAndView();
		Categoria categoria = categoriaServer.findById(idCategoria);

		mav.addObject("categoria", categoria);
		mav.setViewName("categoria/perfil-categoria");
		return mav;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "/insertarProducto/{idCategoria}")
	public @ResponseBody ResponseEntity insertarProducto(@PathVariable("idCategoria") long idCategoria,
			@RequestBody ProductosDto productoDto) {
		// si no existe el producto
		Categoria categoria = categoriaServer.saveProductoCateg(idCategoria,
				productoServer.findById(productoDto.getIdProducto()));
		// si categoria == null, error.
		if (categoria == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(HttpStatus.OK);

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.DELETE, value = "/eliminarProducto/{idCategoria}/{idProducto}")
	public @ResponseBody ResponseEntity eliminarProducto(@PathVariable("idCategoria") long idCategoria,
			@PathVariable("idProducto") long idProducto) {

		Categoria categoria = categoriaServer.eliminarProductoCateg(idCategoria, idProducto);

		if (categoria == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(HttpStatus.OK);
	}

}
