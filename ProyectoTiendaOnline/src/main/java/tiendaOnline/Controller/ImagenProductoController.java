package tiendaOnline.Controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import tiendaOnline.Entity.ImagenProducto;
import tiendaOnline.Entity.Productos;
import tiendaOnline.Server.ImagenProductoServer;
import tiendaOnline.Server.ProductoServer;
import tiendaOnline.Utilidades.Utilidades;

@Controller
@RequestMapping(value="/ImagenProducto")
public class ImagenProductoController {
	
	@Autowired
	private ImagenProductoServer imagenServer;
	
	@Autowired
	private ProductoServer productoServer;

	
	@RequestMapping(method = RequestMethod.POST, value = "/create-imagenProducto/{idProducto}")
	public String crearImagen(@RequestParam("imagenFile") MultipartFile imagenFile, @PathVariable("idProducto") long idProducto, HttpServletRequest request) {
		Productos producto = productoServer.findById(idProducto);
		try {
			ImagenProducto imagen = Utilidades.convertImage(imagenFile);
			if (imagen != null) {
				imagen.setProducto(producto);
				imagenServer.save(imagen);
				
				request.setAttribute("imagenProducto", imagen); 
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/Producto/perfil-producto/"+idProducto;
	}
	

}
