/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.entidades;

/**
 *
 * @author vhernandez
 */
public class TipoSesion {
    private int iSesion;
    private String sDescripcion;

    /**
     * @return the iSesion
     */
    public int getiSesion() {
        return iSesion;
    }

    /**
     * @param iSesion the iSesion to set
     */
    public void setiSesion(int iSesion) {
        this.iSesion = iSesion;
    }

    /**
     * @return the sDescripcion
     */
    public String getsDescripcion() {
        return sDescripcion;
    }

    /**
     * @param sDescripcion the sDescripcion to set
     */
    public void setsDescripcion(String sDescripcion) {
        this.sDescripcion = sDescripcion;
    }
}
