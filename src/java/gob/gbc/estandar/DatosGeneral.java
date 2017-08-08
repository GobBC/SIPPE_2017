package gob.gbc.estandar;

import gob.gbc.util.ResourceLoader;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author gvillasenor
 */
public class DatosGeneral extends java.lang.Object {   
    private String strTitulo="";
    private String strTituloExtendido="";
    public DatosGeneral(String archivo) throws IOException {
        Properties propiedades = new Properties();
        propiedades.load(ResourceLoader.load(archivo));           
        strTitulo = propiedades.getProperty("TITULO");
        strTituloExtendido = propiedades.getProperty("TITULO_EXTENDIDO");                
    }

    /**
     * @return the strTitulo
     */
    public String getStrTitulo() {
        return strTitulo;
    }

    /**
     * @param strTitulo the strTitulo to set
     */
    public void setStrTitulo(String strTitulo) {
        this.strTitulo = strTitulo;
    }

    /**
     * @return the strTituloExtendido
     */
    public String getStrTituloExtendido() {
        return strTituloExtendido;
    }

    /**
     * @param strTituloExtendido the strTituloExtendido to set
     */
    public void setStrTituloExtendido(String strTituloExtendido) {
        this.strTituloExtendido = strTituloExtendido;
    }
}
