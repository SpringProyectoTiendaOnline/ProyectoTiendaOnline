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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import tiendaOnline.DAO.RolRepository;
import tiendaOnline.Entity.Banco;
import tiendaOnline.Entity.Clientes;
import tiendaOnline.Entity.Compra;
import tiendaOnline.Entity.LineaCompra;
import tiendaOnline.Server.BancoServer;
import tiendaOnline.Server.CategoriaServer;
import tiendaOnline.Server.ClienteServer;
import tiendaOnline.Server.CompraServer;
import tiendaOnline.Server.LineaDeCompraServer;

@Controller
@RequestMapping(value = "/Cliente")
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
	@Autowired
	private CategoriaServer categoriaServer;

	// Registrar Cliente
	@GetMapping("/signup")
	public String showForm(Model theModel) {
		Clientes cliente = new Clientes();
		theModel.addAttribute("listaCategoria", categoriaServer.getAll());
		theModel.addAttribute("Cliente", cliente);
		return "signup";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/create-cliente")
	public ModelAndView create_cliente(@ModelAttribute Clientes cliente, BindingResult bindingResult) {
		ModelAndView mav = new ModelAndView();
		List<String> mensaje = new ArrayList<String>();

		if (bindingResult.hasErrors()) {
			for (int i = 0; i < bindingResult.getAllErrors().size(); i++) {
				mensaje.add(bindingResult.getAllErrors().get(i).getDefaultMessage());
			}
			mav.addObject("Error", mensaje);
			mav.addObject("Cliente", cliente);
			mav.addObject("listaCategoria", categoriaServer.getAll());
			mav.setViewName("signup");
			return mav;
		}

		Clientes clienteSave = ClienteServer.save(cliente);
		if (clienteSave != null) {
			mav.addObject("Cliente", clienteSave);
			mav.setViewName("/login");
		}
		return mav;
	}

	// Perfil de cliente
	@RequestMapping(method = RequestMethod.GET, value = "/perfil-cliente/{idCliente}")
	public ModelAndView profile(HttpServletRequest request, @PathVariable("idCliente") long idCliente) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession(true);
		if (session != null && session.getAttribute("idUsuario") != null) {
			mav.addObject("Cliente", ClienteServer.findById(idCliente));
			mav.addObject("listaBanco", bancoServer.findByCliente(ClienteServer.findById(idCliente)));
			mav.addObject("listaCategoria", categoriaServer.getAll());
			mav.setViewName("perfil-cliente");
		} else {
			mav.setViewName("indice");
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
		model.addAttribute("listaCategoria", categoriaServer.getAll());
		return "update-cliente";

	}

	@RequestMapping(method = RequestMethod.POST, value = "/editarCliente/{idCliente}")
	public ModelAndView post_update_cliente(@PathVariable("idCliente") long id, @ModelAttribute @Valid Clientes cliente,
			BindingResult result, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();

		if (result.hasFieldErrors()) {
			mav.addObject("Cliente", cliente);
			mav.addObject("listaCategoria", categoriaServer.getAll());
			mav.setViewName("update-cliente");
			return mav;
		}

		long idCliente = (long) request.getSession().getAttribute("idUsuario");
		cliente.setIdCliente(id);
		Clientes clienteMod = ClienteServer.update(cliente);
		if (idCliente == 1) {
			List<Clientes> lCliente = ClienteServer.getAll();
			mav.addObject("Cliente", ClienteServer.findById(idCliente));
			mav.addObject("listaCliente", lCliente);
			mav.setViewName("list-cliente");
		} else {
			if (clienteMod != null) {
				List<Banco> listBanco = bancoServer.findByCliente(clienteMod);
				mav.addObject("listaBanco", listBanco);
				mav.addObject("Cliente", clienteMod);
				mav.addObject("listaCategoria", categoriaServer.getAll());
				mav.setViewName("perfil-cliente");
			}

		}

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete-cliente/{idCliente}")
	public ModelAndView delete_cliente(@PathVariable("idCliente") long idCliente) {
		ModelAndView mav = new ModelAndView();

		Clientes cliente = ClienteServer.findById(idCliente);

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
					List<LineaCompra> linea = lineaServer.findByCompra(compra.get(i));
					if (linea != null) {
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
			mav.addObject("listaCategoria", categoriaServer.getAll());
			mav.addObject("listaCliente", listaCliente);
			mav.setViewName("list-cliente");

		}
		return mav;
	}

}
