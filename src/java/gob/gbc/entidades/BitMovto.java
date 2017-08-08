/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.entidades;

import java.sql.Date;

/**
 *
 * @author rharo
 */
public class BitMovto {
    private int oficio;
    private String appLogin;
    private Date fechaAut;
    private String autorizo;
    private String impFirma;
    private String tipoOficio;
    private String terminal;
    private String sysClave;
    private int tipoFlujo;
    private String tipoUsr;
    private String comentarioAut;
    private String fecha;
    private int numComentario;

    public int getOficio() {
        return oficio;
    }

    public void setOficio(int oficio) {
        this.oficio = oficio;
    }

    public String getAppLogin() {
        return appLogin;
    }

    public void setAppLogin(String appLogin) {
        this.appLogin = appLogin;
    }

    public Date getFechaAut() {
        return fechaAut;
    }

    public void setFechaAut(Date fechaAut) {
        this.fechaAut = fechaAut;
    }

    public String getAutorizo() {
        return autorizo;
    }

    public void setAutorizo(String autorizo) {
        this.autorizo = autorizo;
    }

    public String getImpFirma() {
        return impFirma;
    }

    public void setImpFirma(String impFirma) {
        this.impFirma = impFirma;
    }

    public String getTipoOficio() {
        return tipoOficio;
    }

    public void setTipoOficio(String tipoOficio) {
        this.tipoOficio = tipoOficio;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getSysClave() {
        return sysClave;
    }

    public void setSysClave(String sysClave) {
        this.sysClave = sysClave;
    }

    public int getTipoFlujo() {
        return tipoFlujo;
    }

    public void setTipoFlujo(int tipoFlujo) {
        this.tipoFlujo = tipoFlujo;
    }

    public String getTipoUsr() {
        return tipoUsr;
    }

    public void setTipoUsr(String tipoUsr) {
        this.tipoUsr = tipoUsr;
    }

    public String getComentarioAut() {
        return comentarioAut;
    }

    public void setComentarioAut(String comentarioAut) {
        this.comentarioAut = comentarioAut;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getNumComentario() {
        return numComentario;
    }

    public void setNumComentario(int numComentario) {
        this.numComentario = numComentario;
    }
    
    
}
