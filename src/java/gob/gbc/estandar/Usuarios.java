/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.estandar;

/**
 *
 * @author vhernandez
 */
public class Usuarios {

    private String sCveUsuario;
    private String sPassword;
    private String sActivo;    
    private String sNombre;
    private String sPater;
    private String sMater;
    private String sCorreo;
    private String iRol;
    private String sDescrRol;
    private String sDependencia;
    private String sDescrDependencia;
    private String sIndAdmin;
    private String sCapturaSolicitante;
    /**
     * @return the sCveUsuario
     */
    public String getsCveUsuario() {
        return sCveUsuario;
    }
    /*Limpia la instancia de valores basura*/

    public void limpiaUsuario() {
        setsActivo("");
        setsCorreo("");
        setsCveUsuario("");
        setsMater("");
        setsNombre("");
        setsPassword("");
        setsPater("");
        setiRol("");
    }

    /**
     * @param sCveUsuario the sCveUsuario to set
     */
    public void setsCveUsuario(String sCveUsuario) {
        this.sCveUsuario = sCveUsuario;
    }

    /**
     * @return the sPassword
     */
    public String getsPassword() {
        return sPassword;
    }

    /**
     * @param sPassword the sPassword to set
     */
    public void setsPassword(String sPassword) {
        this.sPassword = sPassword;
    }

    /**
     * @return the sActivo
     */
    public String getsActivo() {
        return sActivo;
    }

    /**
     * @param sActivo the sActivo to set
     */
    public void setsActivo(String sActivo) {
        this.sActivo = sActivo;
    }       

    /**
     * @return the sNombre
     */
    public String getsNombre() {
        return sNombre;
    }

    /**
     * @param sNombre the sNombre to set
     */
    public void setsNombre(String sNombre) {
        this.sNombre = sNombre;
    }

    /**
     * @return the sPater
     */
    public String getsPater() {
        return sPater;
    }

    /**
     * @param sPater the sPater to set
     */
    public void setsPater(String sPater) {
        this.sPater = sPater;
    }

    /**
     * @return the sMater
     */
    public String getsMater() {
        return sMater;
    }

    /**
     * @param sMater the sMater to set
     */
    public void setsMater(String sMater) {
        this.sMater = sMater;
    }

    /**
     * @return the sCorreo
     */
    public String getsCorreo() {
        return sCorreo;
    }

    /**
     * @param sCorreo the sCorreo to set
     */
    public void setsCorreo(String sCorreo) {
        this.sCorreo = sCorreo;
    }

    /**
     * @return the iRol
     */
    public String getiRol() {
        return iRol;
    }

    /**
     * @param iRol the iRol to set
     */
    public void setiRol(String iRol) {
        this.iRol = iRol;
    }

    /**
     * @return the sDescrRol
     */
    public String getsDescrRol() {
        return sDescrRol;
    }

    /**
     * @param sDescrRol the sDescrRol to set
     */
    public void setsDescrRol(String sDescrRol) {
        this.sDescrRol = sDescrRol;
    }

    /**
     * @return the sDependencia
     */
    public String getsDependencia() {
        return sDependencia;
    }

    /**
     * @param sDependencia the sDependencia to set
     */
    public void setsDependencia(String sDependencia) {
        this.sDependencia = sDependencia;
    }

    /**
     * @return the sDescrDependencia
     */
    public String getsDescrDependencia() {
        return sDescrDependencia;
    }

    /**
     * @param sDescrDependencia the sDescrDependencia to set
     */
    public void setsDescrDependencia(String sDescrDependencia) {
        this.sDescrDependencia = sDescrDependencia;
    }

    /**
     * @return the sIndAdmin
     */
    public String getsIndAdmin() {
        return sIndAdmin;
    }

    /**
     * @param sIndAdmin the sIndAdmin to set
     */
    public void setsIndAdmin(String sIndAdmin) {
        this.sIndAdmin = sIndAdmin;
    }

    /**
     * @return the sCapturaSolicitante
     */
    public String getsCapturaSolicitante() {
        return sCapturaSolicitante;
    }

    /**
     * @param sCapturaSolicitante the sCapturaSolicitante to set
     */
    public void setsCapturaSolicitante(String sCapturaSolicitante) {
        this.sCapturaSolicitante = sCapturaSolicitante;
    }
}
