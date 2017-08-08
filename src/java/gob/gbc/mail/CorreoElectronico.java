/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.gbc.mail;

/**
 *
 * @author muribe
 */
import java.util.Properties;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;

public class CorreoElectronico {
    private String error = "";
    private String hostEC = "";
    private String usuarioEC = "";
    private String contrasenaEC = "";
    private String autorizacionEC = "";
    private String deEC = "";
    private String recipientes[];
    private String mensaje = "";
    private String asunto = "";
    private String tipoContenido = "";
    private String strServer = "";
    private String strUbicacion = "";
    private String strPuerto;

    public CorreoElectronico() {
        setError("");
        setHostEC("");
        setUsuarioEC("");
        setContrasenaEC("");
        setAutorizacionEC("");
        setDeEC("");
        setMensaje("");
        setAsunto("");
        setTipoContenido("text/html");
        setStrPuerto("");
    }
    
    public boolean datosEnvioCorreo() throws Exception {
            Properties propiedades = new Properties();
        try {
            propiedades.load(ResourceLoader.load("CorreoElectronico.properties"));
            setHostEC(propiedades.getProperty("hostEC"));
            setUsuarioEC(propiedades.getProperty("usuarioEC"));
            setContrasenaEC(propiedades.getProperty("contrasenaEC"));
            setAutorizacionEC(propiedades.getProperty("autorizacionEC"));
            setDeEC(propiedades.getProperty("deEC"));
            setStrPuerto(propiedades.getProperty("puertoEnvio"));
            
            return true;
        }catch(Exception e) {
            throw new Exception(e.toString());
        }        
    }

    public boolean enviaCorreo() throws Exception {
        setError("");

        try {
            if(datosEnvioCorreo()) {
                boolean debug = false;
                Properties propiedades = new Properties();
                propiedades.put("mail.smtp.user", getUsuarioEC());
                propiedades.put("mail.smtp.auth", getAutorizacionEC());
                propiedades.put("mail.smtp.host", getHostEC());                
                propiedades.put("mail.smtp.port", getStrPuerto());
                Session session;
                if(getAutorizacionEC().equals("true")) {
                    javax.mail.Authenticator autorizacion = 
                            new SMTPAuthenticator(getUsuarioEC(), getContrasenaEC());
                    session = Session.getInstance(propiedades, autorizacion);
                } else {
                    session = Session.getInstance(propiedades, null);
                }
                
                session.setDebug(debug);
                Message transMensaje = new MimeMessage(session);
                InternetAddress direccionDe = new InternetAddress(getDeEC());
                transMensaje.setFrom(direccionDe);
                InternetAddress direccionPara[] = new InternetAddress[getRecipientes().length];
                
                for(int i = 0; i < getRecipientes().length; i++) {
                    direccionPara[i] = new InternetAddress(getRecipientes()[i]);
                }

                transMensaje.setRecipients(javax.mail.Message.RecipientType.TO, direccionPara);
                transMensaje.setSubject(getAsunto());
                transMensaje.setContent(getMensaje(), getTipoContenido());
                Transport.send(transMensaje);
                
                return true;
            }
            return false;
        }catch(Exception e) {
            throw new Exception(e.toString());
        }
    }
    
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getHostEC() {
        return hostEC;
    }

    public void setHostEC(String hostEC) {
        this.hostEC = hostEC;
    }

    public String getUsuarioEC() {
        return usuarioEC;
    }

    public void setUsuarioEC(String usuarioEC) {
        this.usuarioEC = usuarioEC;
    }

    public String getContrasenaEC() {
        return contrasenaEC;
    }

    public void setContrasenaEC(String contrasenaEC) {
        this.contrasenaEC = contrasenaEC;
    }

    public String getAutorizacionEC() {
        return autorizacionEC;
    }

    public void setAutorizacionEC(String autorizacionEC) {
        this.autorizacionEC = autorizacionEC;
    }

    public String getDeEC() {
        return deEC;
    }

    public void setDeEC(String deEC) {
        this.deEC = deEC;
    }

    public String[] getRecipientes() {
        return recipientes;
    }

    public void setRecipientes(String[] recipientes) {
        this.recipientes = recipientes;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getTipoContenido() {
        return tipoContenido;
    }

    public void setTipoContenido(String tipoContenido) {
        this.tipoContenido = tipoContenido;
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

    public String getStrPuerto() {
        return strPuerto;
    }

    public void setStrPuerto(String strPuerto) {
        this.strPuerto = strPuerto;
    }
}
