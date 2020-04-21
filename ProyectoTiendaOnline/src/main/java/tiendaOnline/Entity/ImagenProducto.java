package tiendaOnline.Entity;

import java.util.Arrays;

public class ImagenProducto {
	
	private Long idImagen;
	
	private byte[] code;
	
	private String url;
	
	private Productos producto;
	
	
	public ImagenProducto() {
		
	}


	@Override
	public String toString() {
		return "ImagenProducto [idImagen=" + idImagen + ", url=" + url + ", code=" + Arrays.toString(code)
				+ ", producto=" + producto + "]";
	}
	
	
	

}
