/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.util;

/**
 *
 * @author joquintero
 */
public final class HTML {
    public static final int MENSAJE_ERROR = -1;
    
    public static final int MENSAJE_INFORMACION = 0;
    
    public static final int MENSAJE_OK = 1;
    
    public static String getMensajeResultado(int tipoMensaje,String strTitulo,String strMensaje){
        String strResultado = "";
        
        String strClass = "";
        
        switch(tipoMensaje){
            case MENSAJE_ERROR:
                strClass = "mensaje-res-error";
                break;
            case MENSAJE_INFORMACION:
                strClass = "mensaje-res-info";
                break;
            case MENSAJE_OK:
                strClass = "mensaje-res-ok";
                break;
        }
        
        strResultado += "<div class='mensaje-res "+strClass+"'>"
                + "         <div class='mensaje-texto'>";
        
        strResultado += "<p>"+strTitulo+"</p>";
        
        strResultado += "<p>"+strMensaje+"</p></div>"
                + "         </div><br/>";
    
        return strResultado;
    
    }
}
