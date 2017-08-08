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
public class TipoCompromiso {
    
    private int tipoCompromisoId;
    private String tipoCompromisoDes;
    private String valorCompromiso;
    private String descrLarga;

    public int getTipoCompromisoId() {
        return tipoCompromisoId;
    }

    public void setTipoCompromisoId(int tipoCompromisoId) {
        this.tipoCompromisoId = tipoCompromisoId;
    }

    public String getTipoCompromisoDes() {
        return tipoCompromisoDes;
    }

    public void setTipoCompromisoDes(String tipoCompromisoDes) {
        this.tipoCompromisoDes = tipoCompromisoDes;
    }

    public String getValorCompromiso() {
        return valorCompromiso;
    }

    public void setValorCompromiso(String valorCompromiso) {
        this.valorCompromiso = valorCompromiso;
    }

    public String getDescrLarga() {
        return descrLarga;
    }

    public void setDescrLarga(String descrLarga) {
        this.descrLarga = descrLarga;
    }
    
    
    
}
