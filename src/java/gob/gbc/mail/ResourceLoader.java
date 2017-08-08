/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.gbc.mail;

import java.io.InputStream;

/**
 *
 * @author gvillasenor
 */
public class ResourceLoader {
    private static Object objResource = new ResourceLoader();

    private ResourceLoader() {
    }

    public static InputStream load(String s) {
        InputStream inputstream = ClassLoader.getSystemResourceAsStream(s);
        if(inputstream == null) {
            inputstream = objResource.getClass().getClassLoader().getResourceAsStream(s);            
        }
        
        return inputstream;
    }
}
