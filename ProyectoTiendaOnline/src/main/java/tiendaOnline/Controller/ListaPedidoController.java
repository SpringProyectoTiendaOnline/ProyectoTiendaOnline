package tiendaOnline.Controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import tiendaOnline.DAO.EstadoRepository;
import tiendaOnline.Entity.Clientes;
import tiendaOnline.Entity.Compra;
import tiendaOnline.Entity.EstadoPedido;
import tiendaOnline.Entity.LineaCompra;
import tiendaOnline.Server.BancoServer;
import tiendaOnline.Server.ClienteServer;
import tiendaOnline.Server.CompraServer;
import tiendaOnline.Server.LineaDeCompraServer;
import tiendaOnline.Server.ProductoServer;

@Controller
@RequestMapping("/ListaPedido")
public class ListaPedidoController {

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
	private EstadoRepository estadoServer;

	@GetMapping("/list-pedido-user/{idCliente}")
	public ModelAndView showCarrito(@PathVariable("idCliente") long idCliente, Model model) {
		ModelAndView mav = new ModelAndView();
		Clientes cliente = clienteServer.findById(idCliente);
		List<Compra> compra = compraServer.findByCliente(cliente);

		mav.addObject("LineaDeCompraServer", lineaServer);
		mav.addObject("Compra", compra);
		mav.addObject("Cliente", cliente);
		mav.setViewName("lista-pedido");
		return mav;
	}

	@RequestMapping(value = "/devolucion-pedido/{idCliente}/{idLineaCompra}", method = RequestMethod.GET)
	public ModelAndView devolucion_from(@PathVariable("idCliente") long idCliente,
			@PathVariable("idLineaCompra") long idLineaPedido) {
		ModelAndView mav = new ModelAndView();
		Clientes cliente = clienteServer.findById(idCliente);
		LineaCompra lineaCompra = lineaServer.findById(idLineaPedido);

		mav.addObject("Cliente", cliente);
		mav.addObject("LineaCompra", lineaCompra);
		mav.setViewName("devolver-producto");

		return mav;
	}

	@RequestMapping(value = "/realizar-devolucion/{idCliente}/{idLineaCompra}", method = RequestMethod.POST)
	public ModelAndView realizar_devolucion(@PathVariable("idCliente") long idCliente,
			@PathVariable("idLineaCompra") long idLineaPedido, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();
		Set<EstadoPedido> listEstado = new HashSet<EstadoPedido>();

		Clientes cliente = clienteServer.findById(idCliente);
		LineaCompra lineaCompra = lineaServer.findById(idLineaPedido);
		Compra compra = lineaCompra.getCompra();
		int cantidad = Integer.parseInt(request.getParameter("cantidadDevolucion"));

		if (lineaCompra != null) {
			if (cantidad == lineaCompra.getCantidad()) {
				EstadoPedido estadoPedido = estadoServer.getOne(2);
				listEstado.add(estadoPedido);
				lineaCompra.setEstado(listEstado);
				lineaServer.update(lineaCompra);
			} else {
				if (cantidad < lineaCompra.getCantidad()) {
					EstadoPedido estadoPedido = estadoServer.getOne(2);
					lineaCompra.setPrecioTotal(
							(lineaCompra.getCantidad() - cantidad) * lineaCompra.getProductos().getPrecio());
					lineaCompra.setCantidad(lineaCompra.getCantidad() - cantidad);

					lineaServer.update(lineaCompra);

					LineaCompra linea = new LineaCompra();
					linea.setCantidad(cantidad);
					linea.setCompra(lineaCompra.getCompra());
					linea.setProductos(lineaCompra.getProductos());
					linea.setPrecioTotal(cantidad * lineaCompra.getProductos().getPrecio());
					listEstado.add(estadoPedido);
					linea.setEstado(listEstado);
					lineaServer.save(linea);

				}
			}

		}

		mav.addObject("Cliente", cliente);
		mav.addObject("LineaCompra", lineaCompra);
		mav.addObject("Compra", compra);
		mav.addObject("LineaDeCompraServer", lineaServer);
		mav.setViewName("lista-pedido");
		return mav;

	}
}
