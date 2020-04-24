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
@RequestMapping(value = "/ImagenProducto")
public class ImagenProductoController {

	@Autowired
	private ImagenProductoServer imagenServer;

	@Autowired
	private ProductoServer productoServer;

	@RequestMapping(method = RequestMethod.POST, value = "/action-imagenProducto/{idProducto}")
	public String crearImagen(@RequestParam("imagenFile") MultipartFile imagenFile,
			@PathVariable("idProducto") long idProducto, @RequestParam(value = "showIdImagenProducto") Long idImagen,
			@RequestParam(value = "action", required = true) String action, HttpServletRequest request) {

		Productos producto = productoServer.findById(idProducto);
		String msgError = "", msg = "";

		try {
			if (action.equalsIgnoreCase("save")) {

				if (imagenFile != null) {
					ImagenProducto imagen = Utilidades.convertImage(imagenFile);

					imagen.setProducto(producto);
					imagenServer.save(imagen);
					msg = "La imagen ha sido añadido correctamente";

					request.setAttribute("imagenProducto", imagen);
				} else {
					msgError = "No ha sido añadido la imagen, porque no has imsertado";
				}
			}

			if (action.equalsIgnoreCase("delete")) {
				if (idImagen != null) {
					ImagenProducto imagenProducto = imagenServer.findById(idImagen);
					imagenProducto.setProducto(null);
					imagenProducto = imagenServer.update(imagenProducto);
					imagenServer.delete(imagenProducto);
					msg = "La imagen ha sido eliminado correctamente.";
				}else {
					msg = "La imagen no ha sido eliminado correctamente.";
				}
			}

			if (action.equalsIgnoreCase("update")) {

				if (imagenFile != null && idImagen > 0) {
					ImagenProducto imagen = Utilidades.convertImage(imagenFile);

					imagen.setIdImagen(idImagen);
					imagen.setProducto(producto);
					imagenServer.update(imagen);
					msg = "La Imagen ha sido modificado correctamente.";

					request.setAttribute("imagenProducto", imagen);
				} else {
					if (idImagen <= 0) {
						msgError = "La imagen no ha sido modificado, No has seleccionado el imagen";
					} else {
						msgError = "La imagen no ha sido modificado, No has insertado la imagen que quiere cambiar";
					}
				}	
			}
			
			request.setAttribute("msgError", msgError);
			request.setAttribute("msg", msg);

		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return "redirect:/Producto/perfil-producto/" + idProducto;
	}

}
