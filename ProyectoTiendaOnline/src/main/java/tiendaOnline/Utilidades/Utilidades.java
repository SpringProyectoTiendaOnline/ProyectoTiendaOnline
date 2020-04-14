package tiendaOnline.Utilidades;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Responsable de crear un objeto sesi髇 (gestiona la conexión a BD de forma transparente
 * @author Laura
 *
 */
public class Utilidades {
	
	public static boolean isNumeric(String cadena){
		try {
			Long.parseLong(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	
	// Generic function to convert list to set 
    public static <T> Set<T> convertListToSet(List<T> list) 
    { 
        // create an empty set 
        Set<T> set = new HashSet<>(); 
  
        // Add each element of list into the set 
        for (T t : list) 
            set.add(t); 
  
        // return the set 
        return set; 
    } 

}
