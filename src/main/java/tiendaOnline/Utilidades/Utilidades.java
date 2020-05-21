package tiendaOnline.Utilidades;

import java.io.File;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

import tiendaOnline.Entity.ImagenProducto;

/**
 * Responsable de crear un objeto sesi髇 (gestiona la conexión a BD de forma
 * transparente
 * 
 * @author Laura
 *
 */
public class Utilidades {

	// Guardar los fichero subido en este lugar.
	private static final String rutaImage = System.getProperty("user.home") + "/images/";

	public static boolean isNumeric(String cadena) {
		try {
			Long.parseLong(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	// Generic function to convert list to set
	public static <T> Set<T> convertListToSet(List<T> list) {
		// create an empty set
		Set<T> set = new HashSet<>();

		// Add each element of list into the set
		for (T t : list)
			set.add(t);

		// return the set
		return set;
	}

	public static ImagenProducto convertImage(MultipartFile imagenFile) throws IOException {

		ImagenProducto imagenProducto = new ImagenProducto();

		// Nombre original del imagen
		String ofn = imagenFile.getOriginalFilename();

		// Ejermplo: imagen.png
		// --> png
		String suffix = ofn.substring(ofn.lastIndexOf("."));

		// el nombre de fichero en formato = uuid
		// 43dsfd.png
		String filename = UUID.randomUUID().toString() + suffix;
		// Obtener la ruta del imagen -->
		String filePath = rutaImage + filename;

		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		
		try {
			imagenFile.transferTo(file);

		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		String urlImagen = "/images/" + filename;
		imagenProducto.setUrlImagen(urlImagen);

		return imagenProducto;

	}

	public static boolean validarImagen(String suffix) {
		if (suffix.equalsIgnoreCase("png") && suffix.equalsIgnoreCase("jpg") && suffix.equalsIgnoreCase("jpeg")
				&& suffix.equalsIgnoreCase("git")) {
			return true;
		} else {
			return false;
		}
	}

}
