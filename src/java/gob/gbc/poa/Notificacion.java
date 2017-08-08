/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.gbc.poa;

/**
 *
 * @author muribe
 */
import gob.gbc.mail.CorreoElectronico;
import gob.gbc.util.Bitacora;

public class Notificacion {
    private String strDestinatarios = "";
    private String strMensaje = "";
    private String strAsunto = "";
    private String strUbicacion = "";
    private String strServer = "";
    private Bitacora bitacora;

    /** Creates a new instance of ResultSQL */
    public Notificacion() throws Exception  {
        bitacora = new Bitacora();
    }

    public String getStrAsunto() {
        return strAsunto;
    }

    public void setStrAsunto(String strAsunto) {
        this.strAsunto = strAsunto;
    }

    public String getStrDestinatarios() {
        return strDestinatarios;
    }

    public void setStrDestinatarios(String strDestinatarios) {
        this.strDestinatarios = strDestinatarios;
    }

    public String getStrMensaje() {
        return strMensaje;
    }

    public void setStrMensaje(String strMensaje) {
        this.strMensaje = strMensaje;
    }

    public String getStrServer() {
        return strServer;
    }

    public void setStrServer(String strServer) {
        this.strServer = strServer;
    }

    public String getStrUbicacion() {
        return strUbicacion;
    }

    public void setStrUbicacion(String strUbicacion) {
        this.strUbicacion = strUbicacion;
    }
    
    public boolean enviaCorreo(){
        String destinatario [] = {""};
        boolean blResultado = false;

        if(strDestinatarios.indexOf(",")>-1)
            destinatario = strDestinatarios.split(",");
        else
            destinatario[0] = strDestinatarios;

        try {
            CorreoElectronico servicioCorreo = new CorreoElectronico();
            servicioCorreo.setRecipientes(destinatario);
            servicioCorreo.setAsunto(strAsunto);
            servicioCorreo.setMensaje(strMensaje);
            blResultado = servicioCorreo.enviaCorreo();
        } catch(Exception e){
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(e, new Throwable());
            bitacora.grabaBitacora();
            blResultado = false;
        }
        return blResultado;
    }

}
