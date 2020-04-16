package tiendaOnline.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import tiendaOnline.Entity.Categoria;
import tiendaOnline.Entity.Productos;
import tiendaOnline.Server.CategoriaServer;

@Controller
@RequestMapping("/Categoria")
public class CategoriaController {

	@Autowired
	private CategoriaServer categoriaServer;

	@RequestMapping(method = RequestMethod.GET, value = "/lista-categoria")
	public ModelAndView listaCategoria() {
		ModelAndView mav = new ModelAndView();
		Categoria categoria = new Categoria();
		List<Categoria> listaCategoria = categoriaServer.getAll();
		mav.addObject("categoria", categoria);
		mav.addObject("listaCategoria", listaCategoria);
		mav.setViewName("categoria/list-categoria");
		return mav;

	}

	@RequestMapping(method = RequestMethod.POST, value = "/create-categoria")
	public ModelAndView crear_Categoria(@ModelAttribute("categoria") @Valid Categoria categoria, BindingResult result) {
		ModelAndView mav = new ModelAndView();
		if (result.hasErrors()) {
			List<Categoria> listaCategoria = categoriaServer.getAll();
			mav.addObject("listaCategoria", listaCategoria);
			mav.setViewName("categoria/list-categoria");
			mav.addObject("Mensaje", result.getAllErrors());
		} else {
			Categoria categSave = categoriaServer.save(categoria);
			if (categSave != null) {
				List<Categoria> listaCategoria = categoriaServer.getAll();
				mav.addObject("listaCategoria", listaCategoria);
				mav.setViewName("categoria/list-categoria");
			}
		}

		return mav;

	}

}
