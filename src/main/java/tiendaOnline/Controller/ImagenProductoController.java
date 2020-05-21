package tiendaOnline.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import tiendaOnline.Entity.ImagenProducto;
import tiendaOnline.Entity.Productos;
import tiendaOnline.Server.ImagenProductoServer;
import tiendaOnline.Server.ProductoServer;

@Controller
@RequestMapping(value = "/ImagenProducto")
public class ImagenProductoController {

	@Autowired
	private ImagenProductoServer imagenServer;

	@Autowired
	private ProductoServer productoServer;

	@RequestMapping(method = RequestMethod.POST, value = "/action-imagenProducto/{idProducto}")
	public String accionImagen(@RequestParam("imagenFile") MultipartFile imagenFile,
			@PathVariable("idProducto") long idProducto, @RequestParam(value = "showIdImagenProducto") Long idImagen,
			@RequestParam(value = "action", required = true) String action, HttpServletRequest request) {

		try {
			Productos producto = productoServer.findById(idProducto);
			String msgError = "", msg = "";
			if (action.equalsIgnoreCase("save")) {
				if (imagenFile != null) {
					// ImagenProducto imagen = Utilidades.convertImage(imagenFile);
					ImagenProducto imagen = new ImagenProducto();

					byte[] image = imagenFile.getBytes();

					// Nombre original del imagen
					String ofn = imagenFile.getOriginalFilename();

					// Ejermplo: imagen.png
					// --> png
					String suffix = ofn.substring(ofn.lastIndexOf("."));

					// el nombre de fichero en formato = uuid
					// 43dsfd.png
					String filename = "/images/" + UUID.randomUUID().toString() + suffix;

					imagen.setImage(image);
					imagen.setUrlImagen(filename);
					imagen.setProducto(producto);

					ImagenProducto im = imagenServer.save(imagen);
					msg = "La imagen ha sido añadido correctamente";

				} else {
					msgError = "No ha sido añadido la imagen, porque no has insertado";
				}
			}

			if (action.equalsIgnoreCase("delete")) {
				if (idImagen != null) {
					ImagenProducto imagenProducto = imagenServer.findById(idImagen);
					imagenProducto.setProducto(null);
					imagenProducto = imagenServer.update(imagenProducto);
					imagenServer.delete(imagenProducto);
					msg = "La imagen ha sido eliminado correctamente.";
				} else {
					msg = "La imagen no ha sido eliminado correctamente.";
				}
			}

			if (action.equalsIgnoreCase("update")) {

				if (imagenFile != null && idImagen > 0) {
					ImagenProducto imagen = new ImagenProducto();

					byte[] image = imagenFile.getBytes();

					// Nombre original del imagen
					String ofn = imagenFile.getOriginalFilename();

					// Ejermplo: imagen.png
					// --> png
					String suffix = ofn.substring(ofn.lastIndexOf("."));

					// el nombre de fichero en formato = uuid
					// 43dsfd.png
					String filename = "/images/" + UUID.randomUUID().toString() + suffix;

					imagen.setImage(image);
					imagen.setUrlImagen(filename);
					imagen.setProducto(producto);
					imagen.setIdImagen(idImagen);

					ImagenProducto up = imagenServer.update(imagen);
					msg = "La Imagen ha sido modificado correctamente.";

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

	// action upload images

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/imagen/{idProducto}/{idImagen}")
	public @ResponseBody ResponseEntity getImageAsResponseEntity(@PathVariable("idProducto") String idProducto,
			@PathVariable("idImagen") String idImagen) {
		try {
			Productos p = productoServer.findById((Long.parseLong(idProducto)));
			ImagenProducto imagen = imagenServer.findImagenProducto((Long.parseLong(idImagen)), p.getIdProducto());
			System.err.println(idProducto + " " + idImagen);

			if (imagen != null) {
				byte[] media = imagen.getImage();

				HttpHeaders headers = new HttpHeaders();
				headers.setCacheControl(CacheControl.noCache().getHeaderValue());
				ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);

				return responseEntity;
			}
			return new ResponseEntity(HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}

}
