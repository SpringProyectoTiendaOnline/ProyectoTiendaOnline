package tiendaOnline.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tiendaOnline.Dto.ProductosDto;
import tiendaOnline.Entity.Categoria;
import tiendaOnline.Entity.Productos;
import tiendaOnline.Server.CategoriaServer;
import tiendaOnline.Server.ImagenProductoServer;
import tiendaOnline.Server.ProductoServer;

@Controller
@RequestMapping(value = "/Categoria")
public class CategoriaController {

	@Autowired
	private CategoriaServer categoriaServer;

	@Autowired
	private ProductoServer productoServer;

	@Autowired
	private ImagenProductoServer imagenServer;

	// Listar las categorias
	@RequestMapping(method = RequestMethod.GET, value = "/lista-categoria")
	public ModelAndView listaCategoria(HttpServletRequest request, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		ModelAndView mav = new ModelAndView();
		long idCliente = (long) request.getSession().getAttribute("idUsuario");
		Categoria categoria = new Categoria();
		List<Categoria> listaCategoria = categoriaServer.getAll();

		mav.addObject("categoria", categoria);
		mav.addObject("listaCategoria", listaCategoria);
		mav.setViewName("categoria/list-categoria");

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/lista_producto_nombreCategoria/{idCategoria}/{nombreCategoria}")
	public ModelAndView listaProductoCategoria(@PathVariable("idCategoria") long idCategoria, Model theModel,
			@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
		ModelAndView mav = new ModelAndView();
		List<Productos> listaProducto = categoriaServer.findCategProductos(idCategoria);

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);

		Page<Productos> productosPage = categoriaServer
				.findPaginatedCategProductos(PageRequest.of(currentPage - 1, pageSize), idCategoria);
		mav.addObject("productoPage", productosPage);
		int totalPages = productosPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			mav.addObject("pageNumbers", pageNumbers);
		}
		mav.addObject("listaProductos", listaProducto);
		mav.addObject("imagenServer", imagenServer);
		mav.addObject("listaCategoria", categoriaServer.getAll());

		mav.setViewName("producto/list-product-user");

		return mav;

	}

	// Crear la categoria
	@RequestMapping(method = RequestMethod.POST, value = "/create-categoria")
	public ModelAndView crear_Categoria(@ModelAttribute("categoria") @Valid Categoria categoria, BindingResult result) {
		ModelAndView mav = new ModelAndView();
		List<Object> mensaje = new ArrayList<>();
		if (result.hasErrors()) {
			mensaje.addAll(result.getAllErrors());
			mav.addObject("Mensaje", result.getAllErrors());
		}

		if (categoriaServer.findByNombre(categoria.getNombreCategoria()) == null) {
			Categoria categSave = categoriaServer.save(categoria);
			if (categSave != null) {
				mav.addObject("categoria", new Categoria());
			}
		} else {
			mensaje.add("Existe la categoria con este nombre !");
			mav.addObject("Mensaje", mensaje);
		}

		List<Categoria> listaCategoria = categoriaServer.getAll();
		mav.addObject("listaCategoria", listaCategoria);
		mav.setViewName("categoria/list-categoria");

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
	public @ResponseBody ResponseEntity eliminarProducto(@PathVariable("idCategoria") Long idCategoria,
			@PathVariable("idProducto") long idProducto) {

		Categoria categoria = categoriaServer.eliminarProductoCateg(idCategoria, idProducto);

		if (categoria == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "/update-nombreCategoria/{idCategoria}")
	public @ResponseBody ResponseEntity update_nombreCategoria(@PathVariable("idCategoria") Long idCategoria,
			@RequestBody String nombreCategoria) {
		Categoria categoria = categoriaServer.findById(idCategoria);

		categoria.setNombreCategoria(nombreCategoria);

		Categoria upd = categoriaServer.edit(categoria);
		if (upd == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.DELETE, value = "/eliminarCategoria/{idCategoria}")
	public @ResponseBody ResponseEntity eliminarCategoria(@PathVariable("idCategoria") Long idCategoria) {

		categoriaServer.delete(categoriaServer.findById(idCategoria));

		return new ResponseEntity(HttpStatus.OK);
	}

}
