/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.gbc.util;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author ugarcia
 */
public class Propiedades {
    private String version;
    
    public Propiedades(String archivo) throws IOException {
        Properties prop = new Properties();
        prop.load(ResourceLoader.load(archivo));
        
        version = prop.getProperty("VERSION");
        
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    
}
