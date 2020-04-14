package tiendaOnline.Controller;
import java.util.Date;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import tiendaOnline.DAO.EstadoRepository;
import tiendaOnline.Entity.Banco;
import tiendaOnline.Entity.Clientes;
import tiendaOnline.Entity.Compra;
import tiendaOnline.Entity.EstadoPedido;
import tiendaOnline.Entity.LineaCompra;
import tiendaOnline.Entity.Productos;
import tiendaOnline.Server.BancoServer;
import tiendaOnline.Server.ClienteServer;
import tiendaOnline.Server.CompraServer;
import tiendaOnline.Server.LineaDeCompraServer;
import tiendaOnline.Server.ProductoServer;

@Controller
@RequestMapping("/Compra")
public class CompraController {

	@Autowired
	private ProductoServer productoServer;
	@Autowired
	private ClienteServer clienteServer;
	@Autowired
	private CompraServer compraServer;
	@Autowired
	private LineaDeCompraServer lineaServer;
	@Autowired
	private BancoServer bancoServer;
	@Autowired
	private EstadoRepository estado;

	@GetMapping("/show-carrito/{idCliente}")
	public ModelAndView showCarrito(@PathVariable("idCliente") long idCliente, Model model) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("Cliente", clienteServer.findById(idCliente));
		mav.setViewName("carrito");
		return mav;
	}

	@GetMapping("/comprarProducto/{idCliente}/{idProducto}")
	public ModelAndView comprarProducto(HttpServletRequest request, @PathVariable("idCliente") long idCliente,
			@PathVariable("idProducto") long idProducto) {
		ModelAndView mav = new ModelAndView();
		HttpSession sesion = request.getSession(true);

		Productos producto = productoServer.findById(idProducto);
		Clientes cliente = clienteServer.findById(idCliente);
		@SuppressWarnings("unchecked")
		HashMap<Productos, Long> carrito = (HashMap<Productos, Long>) sesion.getAttribute("sessionCarrito");
		Map<String, Object> mensaje = new HashMap<>();
		String msg = "msg";
		boolean existe = false;

		if (producto != null) {
			if (producto.getStock() == 0) {
				mensaje.put("msg", "Ya no queda el producto !");
			} else {
				if (carrito == null) {
					carrito = new HashMap<Productos, Long>();
					carrito.put(producto, (long) 1);

				} else {
					// Imprimimos el Map con un Iterador
					Iterator<Productos> it = carrito.keySet().iterator();
					while (it.hasNext() && !existe) {
						Productos key = it.next();
						if (key.getIdProducto() == producto.getIdProducto()) {
							producto = key;
							existe = true;
						} else {
							existe = false;
						}
					}

					if (existe) {
						if (producto.getStock() > carrito.get(producto)) {
							carrito.put(producto, (long) carrito.get(producto) + 1);
							System.err.println("Carrito " + carrito.keySet().iterator().next().getCodProducto()
									+ " --->" + carrito.get(producto));
						} else {
							mensaje.put("msg", "El producto sólo queda : " + producto.getStock());
						}
					} else {
						carrito.put(producto, (long) 1);
						System.err.println("Carrito " + carrito.keySet().iterator().next().getCodProducto() + " --->"
								+ carrito.get(producto));

					}
				}
			}
			sesion.setAttribute("sessionCarrito", carrito);

		}
		mav.addObject("msg", mensaje);
		mav.addObject("Cliente", cliente);
		mav.addObject("listaProductos", productoServer.getAll());
		mav.setViewName("list-product-user");

		return mav;
	}

	//Eliminar el producto desde el carrito
	@SuppressWarnings("unchecked")
	@GetMapping("/eliminar-producto-carrito/{idCliente}/{idProducto}")
	public ModelAndView eliminar_producto_carrito(HttpServletRequest request, @PathVariable("idCliente") long idCliente,
			@PathVariable("idProducto") long idProducto) {
		HttpSession session = request.getSession(true);

		ModelAndView mav = new ModelAndView();
		HashMap<Productos, Long> carrito = (HashMap<Productos, Long>) session.getAttribute("sessionCarrito");

		Productos producto = productoServer.findById(idProducto);
		Clientes cliente = clienteServer.findById(idCliente);

		Iterator<Entry<Productos, Long>> it = carrito.entrySet().iterator();
		
		if (carrito.keySet().size() == 0) {
			System.out.println("0");
		}else {
			while (it.hasNext()) {
				Entry<Productos, Long> key = it.next();
				System.out.println(key);
				if (key.getKey().getIdProducto() == producto.getIdProducto()) {
					it.remove();
				}
			}
		}
	

		session.setAttribute("sessionCarrito", carrito);
		mav.addObject("Cliente", cliente);
		mav.addObject("listaProductos", productoServer.getAll());
		mav.setViewName("carrito");
		return mav;

	}

	@SuppressWarnings("unchecked")
	@PostMapping("/limpiar-pedido/{idCliente}")
	public ModelAndView limpiar_pedido(HttpServletRequest request, @PathVariable("idCliente") long idCliente) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession(true);
		HashMap<Productos, Long> carrito = (HashMap<Productos, Long>) session.getAttribute("sessionCarrito");
		Clientes cliente = clienteServer.findById(idCliente);

		carrito.clear();
		session.setAttribute("sessionCarrito", carrito);
		mav.addObject("Cliente", cliente);
		mav.addObject("listaProductos", productoServer.getAll());
		mav.setViewName("carrito");

		return mav;

	}

	@SuppressWarnings("unchecked")
	@PostMapping("/realizar-pedido/{idCliente}")
	public ModelAndView realizar_pedido(HttpServletRequest request, @PathVariable("idCliente") long idCliente) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession(true);
		HashMap<Productos, Long> carrito = (HashMap<Productos, Long>) session.getAttribute("sessionCarrito");
		Clientes cliente = clienteServer.findById(idCliente);
		Compra compra = new Compra();
		LineaCompra linea = new LineaCompra();
		List<Banco> listaBanco = bancoServer.findByCliente(cliente);
		Date nowDate = new Date();
		String mensajeError = "";
		if (listaBanco != null) {
			if (carrito != null) {
				compra.setFechaCompra(nowDate);
				compra.setClientes(cliente);

				Compra compraSave = compraServer.save(compra);

				if (compraSave != null) {
					Iterator<Productos> it = carrito.keySet().iterator();
					while (it.hasNext()) {
						Productos key = it.next();

						System.err.println(key);
						Productos producto = key;
						producto.setStock(key.getStock() - carrito.get(key));

						productoServer.update(producto);

						if (producto != null) {
							System.err.println("Producto Mod" + producto);

						}
						linea.setProductos(key);
						linea.setCantidad(carrito.get(key));
						linea.setCompra(compra);
						linea.setPrecioTotal(
								carrito.get(key) * key.getPrecio() - (key.getPrecio() * key.getDescuento()));
						Set<EstadoPedido> list = new HashSet<EstadoPedido>();
						EstadoPedido estadoPedido = estado.getOne(1);
						list.add(estadoPedido);
						linea.setEstado(list);

						lineaServer.update(linea);

					}

				}
				carrito.clear();
			}
		} else {
			mensajeError = "No tienes datos de Bancaria!  Ir a perfil introducirselo";
			mav.addObject("msg", mensajeError);
		}

		session.setAttribute("sessionCarrito", carrito);
		mav.addObject("Cliente", cliente);
		mav.addObject("listaProductos", productoServer.getAll());
		mav.setViewName("carrito");

		return mav;

	}

}
