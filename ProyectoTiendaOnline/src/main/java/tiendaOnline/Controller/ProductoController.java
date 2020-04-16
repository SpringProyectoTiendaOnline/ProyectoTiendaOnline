package tiendaOnline.Controller;

import java.util.ArrayList;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tiendaOnline.Dto.ProductosDto;
import tiendaOnline.Entity.Clientes;
import tiendaOnline.Entity.LineaCompra;
import tiendaOnline.Entity.Preguntas;
import tiendaOnline.Entity.Productos;
import tiendaOnline.Entity.Respuestas;
import tiendaOnline.Server.ClienteServer;
import tiendaOnline.Server.LineaDeCompraServer;
import tiendaOnline.Server.PreguntaServer;
import tiendaOnline.Server.ProductoServer;
import tiendaOnline.Server.RespuestaServer;
import tiendaOnline.Utilidades.Utilidades;

/**
 * @author six
 *
 */
@Controller
@RequestMapping("/Producto")
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

	@GetMapping("/create-producto/{idCliente}")
	public String productsForm(Model model, @PathVariable("idCliente") long idCliente) {
		Productos producto = new Productos();
		model.addAttribute("products", producto);
		model.addAttribute("Cliente", clienteServer.findById(idCliente));
		return "add-producto";
	}

	@PostMapping("/create-producto/{idCliente}")
	public ModelAndView addProducto(@ModelAttribute @Valid Productos producto,
			@PathVariable("idCliente") long idCliente, BindingResult bindingResult) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("Cliente", clienteServer.findById(idCliente));
		List<Productos> list = productoServer.getAll();
		boolean existe = false;
		String mensaje = "";
		for (int i = 0; i < list.size() && !existe; i++) {
			if (list.get(i).getCodProducto() == producto.getCodProducto()) {
				existe = true;
			}
		}

		if (bindingResult.hasFieldErrors()) {
			mav.addObject("products", producto);
			mav.setViewName("add-producto");
		} else {
			if (!existe) {
				Productos productoSave = productoServer.save(producto);
				if (productoSave != null) {
					List<Productos> listaProducto = productoServer.getAll();
					mav.addObject("listaProductos", listaProducto);
					mav.setViewName("list-producto");
				}
			} else {
				mensaje = "Existe el codigo";
				mav.addObject("products", producto);
				mav.setViewName("add-producto");
			}

			mav.addObject("msg", mensaje);

		}
		return mav;
	}

	@GetMapping("/editar-producto/{idCliente}/{id}")
	public String update_producto(@PathVariable("idCliente") long idCliente, @PathVariable("id") long id, Model model) {
		model.addAttribute("Cliente", clienteServer.findById(idCliente));
		model.addAttribute("products", productoServer.findById(id));
		return "update-producto";
	}

	@PostMapping("editar-producto/{idCliente}/{id}")
	public ModelAndView update_producto_post(@PathVariable("idCliente") long idCliente, @PathVariable("id") long id,
			@ModelAttribute @Valid Productos producto, BindingResult bindingResult) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("Cliente", clienteServer.findById(idCliente));
		producto.setIdProducto(id);
		if (bindingResult.hasFieldErrors()) {
			mav.addObject("products", producto);
			mav.setViewName("update-producto");
		} else {
			Productos productoSave = productoServer.update(producto);
			if (productoSave != null) {
				List<Productos> listaProducto = productoServer.getAll();
				mav.addObject("listaProductos", listaProducto);
				mav.setViewName("list-producto");
			}
		}
		return mav;
	}

	@GetMapping("/list-producto/{idCliente}")
	public ModelAndView listAllProductos(HttpServletRequest request, @PathVariable long idCliente) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession(true);
		session.setAttribute("idCliente", idCliente);
		mav.addObject("Cliente", clienteServer.findById(idCliente));
		mav.addObject("listaProductos", productoServer.getAll());
		mav.setViewName("list-producto");
		return mav;
	}

	@GetMapping("/removeproducto/{idCliente}/{idProducto}")
	public ModelAndView removeProducto(@PathVariable("idCliente") long idCliente, @PathVariable("idProducto") long id) {
		ModelAndView mav = new ModelAndView();

		Productos producto = productoServer.findById(id);

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

		mav.addObject("Cliente", clienteServer.findById(idCliente));
		mav.addObject("listaProductos", productoServer.getAll());
		mav.setViewName("list-producto");
		return mav;

	}

	@GetMapping("/list-product-user/{idCliente}")
	public ModelAndView listAllProductosForClients(@PathVariable("idCliente") long idCliente) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("Cliente", clienteServer.findById(idCliente));
		mav.addObject("listaProductos", productoServer.getAll());
		mav.setViewName("list-product-user");
		return mav;
	}

	@GetMapping("/perfil-producto/{idProducto}")
	public ModelAndView perfil_producto(@PathVariable("idProducto") long idProducto) {
		ModelAndView mav = new ModelAndView();

		List<Preguntas> lPreguntas = preguntasServer.findByProductos(productoServer.findById(idProducto));

		mav.addObject("listaPreguntas", lPreguntas);

		mav.addObject("Producto", productoServer.findById(idProducto));
		mav.setViewName("perfil-producto");

		return mav;

	}

	@PostMapping("/enviar-pregunta/{idProducto}")
	public ModelAndView enviar_pregunta(@PathVariable("idProducto") long idProducto, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();

		HttpSession session = request.getSession();
		long idCliente = (long) session.getAttribute("idUsuario");
		String texto = request.getParameter("textoPregunta");

		Preguntas preguntas = new Preguntas(texto, clienteServer.findById(idCliente),
				productoServer.findById(idProducto));
		Preguntas preg = preguntasServer.save(preguntas);
		if (preg != null) {
			
		}
			

		List<Preguntas> lPreguntas = preguntasServer.findByProductos(productoServer.findById(idProducto));

		mav.addObject("listaPreguntas", lPreguntas);

		mav.addObject("Producto", productoServer.findById(idProducto));
		mav.setViewName("perfil-producto");

		return mav;
	}

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
	public ModelAndView recibir_respuesta(@PathVariable("idPregunta") long idPregunta, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();

		HttpSession session = request.getSession();
		long idCliente = (long) session.getAttribute("idUsuario");
		String texto = request.getParameter("textoRespuesta");

		Respuestas respuestas = new Respuestas(texto, clienteServer.findById(idCliente),
				preguntasServer.findById(idPregunta));

		respuestaServer.save(respuestas);
		Preguntas preguntas = preguntasServer.findById(idPregunta);

		List<Respuestas> listRespuesta = respuestaServer.findByPreguntas(preguntas);

		mav.addObject("listaRespuestas", listRespuesta);

		mav.addObject("Pregunta", preguntas);
		mav.setViewName("respuesta-producto");

		return mav;

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/searchProducto/{nombreProducto}")
	public @ResponseBody List<ProductosDto> buscarProductos(@PathVariable("nombreProducto") String nombreProducto){
		List<ProductosDto> listaProducto = productoServer.findByNombreAndCodProducto(nombreProducto);
		return listaProducto;
	}
	

	/*@GetMapping("/searchProducto/{idCliente}")
	public ModelAndView buscarProducto(@PathVariable("idCliente") long idCliente, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String valor = (String) request.getParameter("valorProducto");
		List<Productos> listaProducto = productoServer.getAll();
		List<Productos> listaProductoEncontrado = new ArrayList<>();

		Clientes cliente = clienteServer.findById(idCliente);

		if (!valor.isEmpty()) {
			if (Utilidades.isNumeric(valor)) {
				for (int i = 0; i < listaProducto.size(); i++) {
					String codProducto = String.valueOf(listaProducto.get(i).getCodProducto());
					if (codProducto.contains(valor)) {
						listaProductoEncontrado.add(listaProducto.get(i));
					}
				}
				mav.addObject("listaProductos", listaProductoEncontrado);
			} else {
				for (int i = 0; i < listaProducto.size(); i++) {
					String titulo = listaProducto.get(i).getTitulo();
					titulo = titulo.toLowerCase();
					valor = valor.toLowerCase();
					if (titulo.contains(valor)) {
						listaProductoEncontrado.add(listaProducto.get(i));
					}
				}
				mav.addObject("listaProductos", listaProductoEncontrado);
			}
		} else {
			mav.addObject("listaProductos", productoServer.getAll());
		}
		mav.addObject("Cliente", cliente);
		mav.setViewName("list-product-user");

		return mav;

	}*/

}
