/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

/**
 *
 * @author joquintero
 */
public final class Configuracion {
    
    private String strArchivo;
    private Properties properties;
    
    public Configuracion() {
    }

    public Configuracion(String strArchivo) throws IOException {
        this.strArchivo = strArchivo;
        
        Properties properties = new Properties();
        properties.load(objResource.getClass().getClassLoader().getResourceAsStream(strArchivo));
        
        setProperties(properties);
    }

    public String getStrArchivo() {
        return strArchivo;
    }

    public void setStrArchivo(String strArchivo) {
        this.strArchivo = strArchivo;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
    
    
    
    
    
    
    public static Object objResource = new Configuracion();
    
    /**
    * Lee de un archivo de configuracion(porperties) todos los valores que se encuentren
    * en el archivo que se indica, devuelve un hashtable con todas las configuraciones 
    * establecidas.
    * 
    * 
    * @param archivo                       Nombre del archivo a leer
    * 
    * @return  <code>Hashtable</code>      Todos los valores encontrados.
    * 
    * @throws IOException
    * 
    * @throws Exception 
    */
    public static Hashtable obtenerValores(String strArchivo) throws IOException{
        Hashtable config = null;
        Enumeration llaves = null;
        Properties properties = new Properties();
        
        properties.load(objResource.getClass().getClassLoader().getResourceAsStream(strArchivo));

        llaves = properties.keys();
        if(llaves != null){
            config = new Hashtable();

            // Recorro llave por llave y obtengo su valor
            while (llaves.hasMoreElements()) {
                    String llave = (String) llaves.nextElement();
                    String valor = properties.getProperty(llave);
                    config.put(llave, valor);
            }
        }
        
        return config;
    }

    /**
     * 
     * @param strLlave
     * 
     * @return
     * 
     * @throws IOException
     * 
     * @throws Exception 
     */
    public String obtenerValor(String strLlave) throws IOException, Exception{
        String valor = null;
        
        if(getProperties() != null){
            valor = properties.getProperty(strLlave);
        }else{
            throw new Exception("El archivo de configuracion no ha sido cargado.");
        }
        
        return valor;
    }
    
    
    /**
     * 
     * @param strArchivo
     * 
     * @param strLlave
     * 
     * @return
     * 
     * @throws IOException 
     */
    public static String obtenerValor(String strArchivo,String strLlave) throws IOException{
        String valor = null;
        
        Properties properties = new Properties();
        
        properties.load(objResource.getClass().getClassLoader().getResourceAsStream(strArchivo));
        
        valor = properties.getProperty(strLlave);
        
        
        return valor;
    }
    
}
