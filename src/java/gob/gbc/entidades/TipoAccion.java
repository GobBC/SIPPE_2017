/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.gbc.entidades;

/**
 *
 * @author ugarcia
 */
public class TipoAccion {
    private int tipoAccionId;
    private String tipoAccion;
    private String detalleReq;

    public int getTipoAccionId() {
        return tipoAccionId;
    }

    public void setTipoAccionId(int tipoAccionId) {
        this.tipoAccionId = tipoAccionId;
    }

    public String getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(String tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    public String getDetalleReq() {
        return detalleReq;
    }

    public void setDetalleReq(String detalleReq) {
        this.detalleReq = detalleReq;
    }
    
    
}
