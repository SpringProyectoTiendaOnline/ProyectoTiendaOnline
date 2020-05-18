package tiendaOnline.Controller;

import java.util.List;

import javax.validation.Valid;

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

	@PostMapping("/create-banco/{idCliente}") // id = idCliente
	public @ResponseBody ResponseEntity create_banco(@PathVariable("idCliente") long id, @RequestBody Banco banco,
			BindingResult result) {
		banco.setCliente(ClienteServer.findById(id));
		Banco b = bancoServer.findByNumTarjeta(banco);

		if (b != null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		bancoServer.save(banco);
		return new ResponseEntity<>(HttpStatus.OK);
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

	@RequestMapping(method = RequestMethod.POST, value = "/editar-banco/{idCliente}/{idBanco}")
	public @ResponseBody ResponseEntity post_update_banco(@PathVariable("idBanco") long idBanco,@PathVariable("idCliente") long idCliente,
			@RequestBody @Valid Banco banco, BindingResult result) {

		Banco b = bancoServer.findById(idBanco);
		Clientes c = ClienteServer.findById(idCliente);
		// Si el numero de tarjeta antiguo != numero de tarjeta nuevo.
		if (b.getNumTarjeta() != banco.getNumTarjeta()) {
			// si nueva tarjeta existe para mismo cliente.
			if (bancoServer.findByNumTarjeta(banco) != null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		System.err.println(banco.toString());
		banco.setCliente(c);
		bancoServer.update(banco);
		return new ResponseEntity<>(HttpStatus.OK);
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
