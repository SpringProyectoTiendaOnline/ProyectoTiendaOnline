package tiendaOnline.Controller;

import java.io.IOException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tiendaOnline.Dto.ProductosDto;
import tiendaOnline.Entity.Clientes;
import tiendaOnline.Entity.ImagenProducto;
import tiendaOnline.Entity.LineaCompra;
import tiendaOnline.Entity.Preguntas;
import tiendaOnline.Entity.Productos;
import tiendaOnline.Entity.Respuestas;
import tiendaOnline.Entity.Valoracion;
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

	private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

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

	@GetMapping("/create-producto")
	public String productsForm(Model model) {
		Productos producto = new Productos();
		model.addAttribute("products", producto);
		return "producto/add-producto";
	}

	@PostMapping("/create-producto")
	public ModelAndView addProducto(@ModelAttribute @Valid Productos producto, BindingResult bindingResult,
			HttpSession session) throws IOException {
		ModelAndView mav = new ModelAndView();
		String mensaje = "";

		Productos find = productoServer.findById(producto.getIdProducto());

		// Comprobar si hay error
		if (bindingResult.hasFieldErrors()) {
			mav.addObject("products", producto);
			mav.setViewName("add-producto");
		} else {
			// Comprobar si existe el codigo de Producto
			if (find != null) {
				mensaje = "Existe el codigo";
				mav.setViewName("producto/add-producto");
			} else {
				// Guardar
				Productos productoSave = productoServer.save(producto);
				// Si ha guardado, guarda el imagen
				if (productoSave != null) {
				}
				List<Productos> listaProducto = productoServer.getAll();
				mav.addObject("listaProductos", listaProducto);
				mav.setViewName("/producto/list-producto");
			}

		}

		mav.addObject("msg", mensaje);

		return mav;
	}

	@GetMapping("/editar-producto/{idProducto}")
	public String update_producto(@PathVariable("idProducto") long id, Model model) {
		model.addAttribute("products", productoServer.findById(id));
		return "producto/update-producto";
	}

	@PostMapping("editar-producto/{idProducto}")
	public String update_producto_post(@PathVariable("idProducto") long id, @ModelAttribute @Valid Productos producto,
			BindingResult bindingResult, HttpServletRequest request) {

		if (bindingResult.hasFieldErrors()) {
			request.setAttribute("products", producto);
			return "redirect:/Producto/editar-producto/" + id;
		} else {
			producto.setIdProducto(id);
			productoServer.update(producto);
		}
		return "redirect:/Producto/list-producto";
	}

	@GetMapping("/list-producto")
	public ModelAndView listAllProductos(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("listaProductos", productoServer.getAll());
		mav.setViewName("producto/list-producto");
		return mav;
	}

	@GetMapping("/removeproducto/{idProducto}")
	public ModelAndView removeProducto(@PathVariable("idProducto") long idProdcuto) {
		ModelAndView mav = new ModelAndView();

		Productos producto = productoServer.findById(idProdcuto);

		List<LineaCompra> linea = lineaServer.getAll();

		if (producto != null) {
			for (int i = 0; i < linea.size(); i++) {
				if (linea.get(i).getProductos() != null) {
					if (linea.get(i).getProductos().getIdProducto() == producto.getIdProducto()) {
						linea.get(i).setProductos(null);
						LineaCompra lineaComp = lineaServer.update(linea.get(i));
						if (lineaComp != null) {
							System.out.println(lineaComp + " : Edidtar");
						}
					} else {
					}
				}

			}
		}

		if (producto != null) {
			productoServer.delete(producto);
		}

		mav.addObject("listaProductos", productoServer.getAll());
		mav.setViewName("producto/list-producto");
		return mav;

	}

	@GetMapping("/list-product-user/{idCliente}")
	public ModelAndView listAllProductosForClients(@PathVariable("idCliente") long idCliente) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("Cliente", clienteServer.findById(idCliente));
		mav.addObject("listaProductos", productoServer.getAll());
		mav.setViewName("producto/list-product-user");
		return mav;
	}

	@GetMapping("/perfil-producto/{idProducto}")
	public ModelAndView perfil_producto(@PathVariable("idProducto") long idProducto) {
		ModelAndView mav = new ModelAndView();

		List<Preguntas> lPreguntas = preguntasServer.findByProductos(productoServer.findById(idProducto));

		ImagenProducto imagen = new ImagenProducto();

		mav.addObject("listaPreguntas", lPreguntas);
		mav.addObject("Producto", productoServer.findById(idProducto));
		mav.addObject("ListaImagen", imagenServer.findByProducto(productoServer.findById(idProducto)));
		mav.setViewName("producto/perfil-producto");

		return mav;
	}

	// agregar la valoracion id.
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "/agregarvaloracion/{idProducto}/{puntuacion}")
	public @ResponseBody ResponseEntity agregarValoracion(@PathVariable("idProducto") long idProducto,
			@PathVariable("puntuacion") long puntuacion, HttpServletRequest request) {

		HttpSession session = request.getSession();
		long idCliente = (long) session.getAttribute("idUsuario");
		Clientes cliente = clienteServer.findById(idCliente);
		Productos producto = productoServer.findById(idProducto);
		List<Valoracion> lista = null;
		Valoracion valoracion = new Valoracion();

		try {
			lista = valoracionServer.findByProductoAndCliente(producto, cliente);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		if (lista.size() > 0) {
			producto = null;
		} else {
			valoracion.setProducto(producto);
			valoracion.setPuntuacion(puntuacion);
			valoracion.setCliente(cliente);
			valoracion = valoracionServer.save(valoracion);
		}
		if (valoracion == null && producto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(HttpStatus.OK);
	}


	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/enviarPregunta/{idProducto}", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity enviar_pregunta(@PathVariable("idProducto") long idProducto,
			@PathVariable("textoPregunta") String texto, HttpServletRequest request) {


		long idCliente = (long) request.getSession().getAttribute("idUsuario");

		Preguntas preguntas = new Preguntas(texto, clienteServer.findById(idCliente),
				productoServer.findById(idProducto));
		Preguntas preg = preguntasServer.save(preguntas);

		if (preg == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity(HttpStatus.OK);

		}

	}
	/*
	 * @PostMapping("/enviar-pregunta/{idProducto}") public ModelAndView
	 * enviar_pregunta(@PathVariable("idProducto") long idProducto,
	 * HttpServletRequest request) { ModelAndView mav = new ModelAndView();
	 * 
	 * HttpSession session = request.getSession(); long idCliente = (long)
	 * session.getAttribute("idUsuario"); String texto =
	 * request.getParameter("textoPregunta");
	 * 
	 * Preguntas preguntas = new Preguntas(texto, clienteServer.findById(idCliente),
	 * productoServer.findById(idProducto)); Preguntas preg =
	 * preguntasServer.save(preguntas); if (preg != null) {
	 * 
	 * }
	 * 
	 * List<Preguntas> lPreguntas =
	 * preguntasServer.findByProductos(productoServer.findById(idProducto));
	 * 
	 * mav.addObject("listaPreguntas", lPreguntas);
	 * 
	 * mav.addObject("Producto", productoServer.findById(idProducto));
	 * mav.setViewName("producto/perfil-producto");
	 * 
	 * return mav; }
	 */

	@GetMapping("/respuesta-producto/{idPregunta}")
	public ModelAndView respuesta(@PathVariable("idPregunta") long idPregunta) {
		ModelAndView mav = new ModelAndView();

		Preguntas preguntas = preguntasServer.findById(idPregunta);
		List<Respuestas> listRespuesta = respuestaServer.findByPreguntas(preguntas);

		mav.addObject("listaRespuestas", listRespuesta);
		mav.addObject("Pregunta", preguntas);
		mav.setViewName("respuesta-producto");

		return mav;

	}

	@PostMapping("/enviar-respuesta/{idPregunta}")
	public @ResponseBody ResponseEntity<Respuestas> recibir_respuesta(@PathVariable("idPregunta") long idPregunta,
			@PathVariable("idUsuario") long idCliente, @PathVariable("textoRespuesta") String textoRespuesta,
			@RequestBody Respuestas respuesta) {

		System.err.println(respuesta);

		Respuestas respuestas = new Respuestas(textoRespuesta, clienteServer.findById(idCliente),
				preguntasServer.findById(idPregunta));

		Respuestas resp = respuestaServer.save(respuestas);

		if (resp == null) {
			return new ResponseEntity<Respuestas>(resp, HttpStatus.NOT_FOUND);

		} else {
			return new ResponseEntity<Respuestas>(resp, HttpStatus.OK);

		}

	}

	// Buscar el producto por titulo
	@RequestMapping(method = RequestMethod.GET, value = "/searchProducto/{titulo}")
	public @ResponseBody List<ProductosDto> buscarProductos(@PathVariable("titulo") String nombreProducto) {
		List<ProductosDto> listaProducto = productoServer.findByNombreAndCodProducto(nombreProducto);
		return listaProducto;
	}


}
