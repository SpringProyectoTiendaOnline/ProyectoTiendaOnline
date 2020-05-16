package tiendaOnline.Controller;

import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;

import tiendaOnline.Entity.Banco;
import tiendaOnline.Entity.Clientes;
import tiendaOnline.Server.BancoServer;
import tiendaOnline.Server.ClienteServer;

@Controller
@RequestMapping("/Banco")
public class BancoController {
	
	@Autowired
	private ClienteServer ClienteServer;
	@Autowired
	private BancoServer bancoServer;

	@GetMapping("/create-banco/{idCliente}")
	public String banco_showFrom(@PathVariable("idCliente") long id, Model theModel) {
		Banco banco = new Banco();
		banco.setCliente(ClienteServer.findById(id));
		theModel.addAttribute("Cliente", ClienteServer.findById(id));
		theModel.addAttribute("Banco", banco);
		return "signup-banco";

	}

	@PostMapping("create-banco/{idCliente}") // id = idCliente
	public ModelAndView create_banco(@PathVariable("idCliente") long id, @ModelAttribute @Valid Banco banco,
			BindingResult result) {
		ModelAndView mav = new ModelAndView();
		Clientes cliente = ClienteServer.findById(id);
		mav.addObject("Cliente", cliente);

		if (!result.hasFieldErrors()) {
			if (banco != null) {
				banco.setCliente(cliente);
				System.err.println(banco.toString());
				Banco bancoGuard = bancoServer.save(banco);
				if (bancoGuard != null) {
					System.out.println("Banco : Guardado");
					mav.addObject("Cliente", cliente);
					List<Banco> listaBanco = bancoServer.findByCliente(cliente);
					mav.addObject("listaBanco", listaBanco);
					mav.setViewName("perfil-cliente");
				} else {
					System.out.println("Banco : No ha sido Guardado");
					mav.setViewName("signup-banco");

				}
			}
		} else {
			mav.addObject("Cliente", cliente);
			mav.setViewName("signup-banco");
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/editar-banco/{idCliente}/{idBanco}")
	public ModelAndView get_update_banco(@PathVariable("idBanco") long idBanco,
			@PathVariable("idCliente") long idCliente) {
		Banco banco = bancoServer.findById(idBanco);
		ModelAndView mav = new ModelAndView();
		mav.addObject("Cliente", ClienteServer.findById(idCliente));
		mav.addObject("Banco", banco);
		mav.setViewName("update-banco");
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "editar-banco/{idCliente}/{idBanco}")
	public ModelAndView post_update_banco(@PathVariable("idBanco") long id, @ModelAttribute @Valid Banco banco,
			BindingResult result) {
		ModelAndView mav = new ModelAndView();
		Banco bancoO = bancoServer.findById(id);
		if (result.hasFieldErrors()) {
			mav.addObject("Banco", bancoO);
			mav.setViewName("update-banco");
		} else {
			banco.setIdBanco(id);
			banco.setCliente(bancoO.getCliente());
			Banco bancoMod = bancoServer.update(banco);
			if (bancoMod != null) {
				List<Banco> listaBanco = bancoServer.findByCliente(bancoMod.getCliente());
				mav.addObject("listaBanco", listaBanco);
				mav.addObject("Cliente", bancoMod.getCliente());
				mav.setViewName("perfil-cliente");
			}
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete-banco/{idCliente}/{idBanco}")
	public ModelAndView delete_banco(@PathVariable("idBanco") long idBanco, @PathVariable("idCliente") long idCliente) {
		ModelAndView mav = new ModelAndView();
		Banco banco = bancoServer.findById(idBanco);
		Clientes cliente = banco.getCliente();
		if (banco != null) {
			bancoServer.delete(banco.getIdBanco());
			List<Banco> listaBanco = bancoServer.getAll();
			mav.addObject("listaBanco", listaBanco);
			mav.addObject("Cliente", cliente);
			mav.setViewName("perfil-cliente");
		}
		return mav;
	}



}
