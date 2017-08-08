
package gob.gbc.util;

import java.io.InputStream;

/**
 * Clase para la obtencion de recursos en tiempo de ejecucion.
 * 
 * @author  Jose Luis Albor Garcia
 * @version 1.0
 */
 
public class ResourceLoader
{

    private ResourceLoader(){
    }

	/**
	 * Metodo que busca un archivo en las rutas de acceso de la clase <code>ClassLoader</code> y 
	 * obtiene un InputStream del mismo.
	 * 
	 * @param s Cadena con el nombre del archivo a buscar.
	 * @return Stream del archivo encontrado.
	 */
    public static InputStream load(String s)
    {
        InputStream inputstream = ClassLoader.getSystemResourceAsStream(s);
        if(inputstream == null)
            inputstream = objResource.getClass().getClassLoader().getResourceAsStream(s);
        return inputstream;
    }

    private static Object objResource = new ResourceLoader();

}
