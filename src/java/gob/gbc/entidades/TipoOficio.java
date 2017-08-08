/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.entidades;

/**
 *
 * @author rharo
 */
public class TipoOficio extends Movimiento {
    private String tipoOficio;
    private String statusTipoOficio;
    private String obsRechazoTipoOficio;

    public String getTipoOficio() {
        return tipoOficio;
    }

    public void setTipoOficio(String tipoOficio) {
        this.tipoOficio = tipoOficio;
    }

    public String getStatusTipoOficio() {
        return statusTipoOficio;
    }

    public void setStatusTipoOficio(String statusTipoOficio) {
        this.statusTipoOficio = statusTipoOficio;
    }

    public String getObsRechazoTipoOficio() {
        return obsRechazoTipoOficio;
    }

    public void setObsRechazoTipoOficio(String obsRechazoTipoOficio) {
        this.obsRechazoTipoOficio = obsRechazoTipoOficio;
    }
    
    
}
