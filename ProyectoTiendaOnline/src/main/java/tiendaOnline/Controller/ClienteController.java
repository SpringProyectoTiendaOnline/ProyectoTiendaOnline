package tiendaOnline.Controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import tiendaOnline.DAO.RolRepository;
import tiendaOnline.Entity.Banco;
import tiendaOnline.Entity.Clientes;
import tiendaOnline.Entity.Compra;
import tiendaOnline.Entity.LineaCompra;
import tiendaOnline.Server.BancoServer;
import tiendaOnline.Server.ClienteServer;
import tiendaOnline.Server.CompraServer;
import tiendaOnline.Server.LineaDeCompraServer;

@Controller
@RequestMapping("/Cliente")
public class ClienteController {

	@Autowired
	private ClienteServer ClienteServer;
	@Autowired
	private BancoServer bancoServer;
	@Autowired
	private LineaDeCompraServer lineaServer;
	@Autowired
	private CompraServer compraServer;
	@Autowired
	private RolRepository rol;

	// Perfil de cliente
	@RequestMapping(method = RequestMethod.GET, value = "/perfil-cliente/{idCliente}")
	public ModelAndView profile(HttpServletRequest request, @PathVariable("idCliente") long idCliente) {
		ModelAndView mav = new ModelAndView();

		HttpSession session = request.getSession();

		if (session != null && session.getAttribute("idUsuario") != null) {
			System.out.println(session.getAttribute("idUsuario"));
			mav.addObject("Cliente", ClienteServer.findById(idCliente));
			mav.addObject("listaBanco", bancoServer.findByCliente(ClienteServer.findById(idCliente)));
			mav.setViewName("perfil-cliente");
		} else {
			mav.setViewName("index");
		}

		return mav;
	}

	@GetMapping("/list/{idCliente}")
	public ModelAndView listUsers(@PathVariable("idCliente") long idCliente, Model theModel) {
		ModelAndView mav = new ModelAndView();
		List<Clientes> lCliente = ClienteServer.getAll();
		theModel.addAttribute("listaCliente", lCliente);
		mav.addObject("Cliente", ClienteServer.findById(idCliente));
		mav.setViewName("list-cliente");
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/editar-cliente/{idCliente}")
	public String get_update_cliente(@PathVariable("idCliente") long id, Model model) {
		Clientes cliente = ClienteServer.findById(id);
		model.addAttribute("Cliente", cliente);
		return "update-cliente";

	}

	@RequestMapping(method = RequestMethod.POST, value = "editar-cliente/{idCliente}")
	public ModelAndView post_update_cliente(@PathVariable("idCliente") long id, @ModelAttribute @Valid Clientes cliente,
			BindingResult result, Model themodel) {
		ModelAndView mav = new ModelAndView();

		Clientes clienteOrig = ClienteServer.findById(id);
		if (result.hasFieldErrors()) {
			mav.addObject("Cliente", cliente);
			mav.setViewName("update-cliente");
		} else {
			cliente.setRoles(clienteOrig.getRoles());
			cliente.setIdCliente(id);
			Clientes clienteMod = ClienteServer.update(cliente);
			if (clienteMod != null) {
				List<Banco> listBanco = bancoServer.findByCliente(clienteMod);
				mav.addObject("listaBanco", listBanco);
				mav.addObject("Cliente", clienteMod);
				mav.setViewName("perfil-cliente");
			}

		}

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete-cliente/{id}/{idCliente}")
	public ModelAndView delete_cliente(@PathVariable("id") long id, @PathVariable("idCliente") long idCliente) {
		ModelAndView mav = new ModelAndView();
		Clientes cliente = ClienteServer.findById(idCliente);
		Clientes clienteAdmin = ClienteServer.findById(id);

		if (cliente != null) {
			List<Banco> listaBanco = bancoServer.findByCliente(cliente);
			List<Compra> compra = compraServer.findByCliente(cliente);

			if (listaBanco != null) {
				for (int i = 0; i < listaBanco.size(); i++) {
					bancoServer.delete(listaBanco.get(i).getIdBanco());
				}
			}

			if (compra != null) {
				for (int i = 0; i < compra.size(); i++) {
					System.err.println(compra);
					List<LineaCompra> linea = lineaServer.findByCompra(compra.get(i));
					if (linea != null) {
						System.err.println(linea.toString());
						for (int j = 0; j < linea.size(); j++) {
							linea.get(i).setProductos(null);
							LineaCompra comp = lineaServer.update(linea.get(i));
							if (comp != null) {
								lineaServer.delete(linea.get(j).getIdLineaCompra());
							}
						}
						compra.get(i).setClientes(null);
						Compra compMod = compraServer.update(compra.get(i));
						if (compMod != null) {
							compraServer.delete(compra.get(i).getIdCompra());
						}
					}
				}

			}

			if (cliente != null) {
				rol.delete(cliente.getRoles().iterator().next());
				ClienteServer.delete(cliente);
			}

			List<Clientes> listaCliente = ClienteServer.getAll();
			mav.addObject("Cliente", clienteAdmin);

			mav.addObject("listaCliente", listaCliente);
			mav.setViewName("list-cliente");

		}
		return mav;
	}

}
