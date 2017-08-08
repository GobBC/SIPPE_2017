/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.entidades;

/**
 *
 * @author rharo
 */
public class FlujoFirmas {
    protected int tipoFlujo;
    protected String tipoUsr;
    protected int sysClave;
    protected String appLogin;
    protected int orden;

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

    public int getSysClave() {
        return sysClave;
    }

    public void setSysClave(int sysClave) {
        this.sysClave = sysClave;
    }

    public String getAppLogin() {
        return appLogin;
    }

    public void setAppLogin(String appLogin) {
        this.appLogin = appLogin;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
    
    
}
