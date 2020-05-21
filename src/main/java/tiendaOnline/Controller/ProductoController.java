package tiendaOnline.Controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tiendaOnline.Dto.ProductosDto;
import tiendaOnline.Entity.Categoria;
import tiendaOnline.Entity.Clientes;
import tiendaOnline.Entity.ImagenProducto;
import tiendaOnline.Entity.LineaCompra;
import tiendaOnline.Entity.Preguntas;
import tiendaOnline.Entity.Productos;
import tiendaOnline.Entity.Respuestas;
import tiendaOnline.Entity.Valoracion;
import tiendaOnline.Server.CategoriaServer;
import tiendaOnline.Server.ClienteServer;
import tiendaOnline.Server.ImagenProductoServer;
import tiendaOnline.Server.LineaDeCompraServer;
import tiendaOnline.Server.PreguntaServer;
import tiendaOnline.Server.ProductoServer;
import tiendaOnline.Server.RespuestaServer;
import tiendaOnline.Server.ValoracionServer;

/**
 * @author six
 *
 */
@Controller
@RequestMapping(value = "/Producto")
public class ProductoController {

	@Autowired
	private ProductoServer productoServer;
	@Autowired
	private ClienteServer clienteServer;
	@Autowired
	private LineaDeCompraServer lineaServer;
	@Autowired
	private PreguntaServer preguntasServer;
	@Autowired
	private RespuestaServer respuestaServer;
	@Autowired
	private ImagenProductoServer imagenServer;
	@Autowired
	private ValoracionServer valoracionServer;
	@Autowired
	private CategoriaServer categoriaServer;

	private int result = 0;

	@RequestMapping(method = RequestMethod.GET, value = "/create-producto")
	public String productsForm(Model model) {
		Productos producto = new Productos();
		model.addAttribute("products", producto);
		return "producto/add-producto";
	}

	// Crear el producto desde el formulario
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "/create-producto")
	public @ResponseBody ResponseEntity addProducto(@RequestBody Productos producto, BindingResult bindingResult) {

		Productos p = productoServer.findByCodProducto(producto.getCodProducto());

		// si el codigo de producto existe.
		if (p != null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		producto.setValoracionMedia(0);
		productoServer.save(producto);
		return new ResponseEntity(HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/editar-producto/{idProducto}")
	public String update_producto(@PathVariable("idProducto") long id, Model model) {
		model.addAttribute("products", productoServer.findById(id));
		return "producto/update-producto";
	}

	// Editar el producto a través del formulario
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "/editar-producto/{idProducto}")
	public @ResponseBody ResponseEntity update_producto_post(@PathVariable("idProducto") long id,
			@RequestBody @Valid Productos producto, HttpServletRequest request) {

		Productos p = productoServer.findById(id);
		// si codproducto antiguo no es lo mismo que de nuevo.
		if (p.getCodProducto() != producto.getCodProducto()) {
			if (productoServer.findByCodProducto(producto.getCodProducto()) != null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);

			}
		}
		producto.setValoracionMedia(p.getValoracionMedia());
		productoServer.update(producto);
		return new ResponseEntity(HttpStatus.OK);

	}

	// Lista de producto
	@GetMapping("/list-producto")
	public ModelAndView listAllProductos(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("listaProductos", productoServer.getAll());
		mav.setViewName("producto/list-producto");
		return mav;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/removeproducto/{idProducto}")
	public @ResponseBody ResponseEntity<Object> removeProducto(@PathVariable("idProducto") long idProducto) {

		Productos producto = productoServer.findById(idProducto);

		List<LineaCompra> linea = lineaServer.findByProducto(producto);
		List<Preguntas> preguntas = preguntasServer.findByProductos(producto);
		Categoria categoria = categoriaServer.findByProducto(idProducto);
		List<ImagenProducto> imagen = imagenServer.findByProducto(producto);

		if (producto != null) {
			if (linea != null) {
				for (int i = 0; i < linea.size(); i++) {
					linea.get(i).setProductos(null);
					LineaCompra lineaComp = lineaServer.update(linea.get(i));
					System.err.println(lineaComp + " : Edidtar");

				}
			}

			if (preguntas != null) {
				for (int i = 0; i < preguntas.size(); i++) {
					preguntasServer.delete(preguntas.get(i));
					List<Respuestas> respuestas = respuestaServer.findByPreguntas(preguntas.get(i));
					for (int j = 0; j < respuestas.size(); j++) {
						respuestaServer.delete(respuestas.get(i));
					}
				}
			}

			if (categoria != null) {
				categoriaServer.eliminarProductoCateg(categoria.getIdCategoria(), idProducto);
			}

			if (imagen != null) {
				for (int i = 0; i < imagen.size(); i++) {
					imagenServer.delete(imagen.get(i));
				}

			}

			productoServer.delete(producto);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@GetMapping("/list-product-user/{idCliente}")
	public ModelAndView listAllProductosForClients(@PathVariable("idCliente") long idCliente,
			@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
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

		mav.addObject("Cliente", clienteServer.findById(idCliente));
		mav.addObject("listaCategoria", categoriaServer.getAll());
		mav.setViewName("producto/list-product-user");
		return mav;
	}

	// Perfil de producto
	@GetMapping("/perfil-producto/{idProducto}")
	public ModelAndView perfil_producto(@PathVariable("idProducto") long idProducto) {
		ModelAndView mav = new ModelAndView();

		List<Preguntas> lPreguntas = preguntasServer.findByProductos(productoServer.findById(idProducto));

		mav.addObject("listaPreguntas", lPreguntas);
		mav.addObject("listaCategoria", categoriaServer.getAll());
		mav.addObject("Producto", productoServer.findById(idProducto));
		mav.setViewName("producto/perfil-producto");

		return mav;
	}

	// agregar la valoracion id.
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "/agregarvaloracion/{idProducto}/{puntuacion}")
	public @ResponseBody ResponseEntity agregarValoracion(@PathVariable("idProducto") long idProducto,
			@PathVariable("puntuacion") long puntuacion, HttpServletRequest request) {

		// Obtener id de CLiente
		HttpSession session = request.getSession();
		long idCliente = (long) session.getAttribute("idUsuario");
		// Buscar cliente y producto
		Clientes cliente = clienteServer.findById(idCliente);
		Productos producto = productoServer.findById(idProducto);
		List<Valoracion> lista = null;

		try {
			// Comprobar si existe
			lista = valoracionServer.findByProductoAndCliente(producto, cliente);

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		// Si existe la lista de valoracion para este producto.
		if (lista.size() > 0) {
			cliente = null;
		} else {
			Valoracion valoracion = new Valoracion(puntuacion, producto, cliente);
			valoracion = valoracionServer.save(valoracion);
			producto.setValoracionMedia(valoracionServer.obtenerValoracionMediaPorProducto(producto));
			productoServer.update(producto);
		}
		if (producto == null || cliente == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(HttpStatus.OK);
	}

	// POST- Enviar la pregunta del producto
	@RequestMapping(value = "/enviarPregunta/{idProducto}", method = RequestMethod.POST)
	public @ResponseBody Preguntas enviar_pregunta(@PathVariable("idProducto") long idProducto,
			HttpServletRequest request, @RequestBody Preguntas pregun) {

		long idCliente = (long) request.getSession().getAttribute("idUsuario");

		Preguntas preguntas = new Preguntas(pregun.getTexto(), clienteServer.findById(idCliente),
				productoServer.findById(idProducto));

		Preguntas preg = preguntasServer.save(preguntas);

		if (preg == null) {
			return null;
		}

		return preg;
	}

	// GEt - Respuesta de producto
	@RequestMapping(value = "/respuestaProducto/{idPregunta}", method = RequestMethod.GET)
	public ModelAndView respuesta(@PathVariable("idPregunta") long idPregunta) {
		ModelAndView mav = new ModelAndView();

		Preguntas preguntas = preguntasServer.findById(idPregunta);
		List<Respuestas> listRespuesta = respuestaServer.findByPreguntas(preguntas);
		mav.addObject("listaRespuestas", listRespuesta);
		mav.addObject("Pregunta", preguntas);
		mav.setViewName("respuesta-producto");
		return mav;

	}

//Post - Respuesta de producto
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/enviar-respuesta/{idPregunta}", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity recibir_respuesta(@PathVariable("idPregunta") long idPregunta,
			HttpServletRequest request, @RequestBody Respuestas respuesta, HttpServletResponse response) {

		long idCliente = (long) request.getSession().getAttribute("idUsuario");

		Respuestas respuestas = new Respuestas(respuesta.getTexto(), clienteServer.findById(idCliente),
				preguntasServer.findById(idPregunta));

		Respuestas resp = respuestaServer.save(respuestas);

		if (resp == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);

		}
		return new ResponseEntity(HttpStatus.OK);

	}

	// Buscar el producto por titulo
	@RequestMapping(method = RequestMethod.GET, value = "/searchProducto/{titulo}")
	public @ResponseBody List<ProductosDto> buscarProductos(@PathVariable("titulo") String nombreProducto) {
		List<ProductosDto> listaProducto = productoServer.findByNombreAndCodProducto(nombreProducto);
		return listaProducto;
	}

}
