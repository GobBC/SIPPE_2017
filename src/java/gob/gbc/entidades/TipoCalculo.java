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
public class TipoCalculo {
    private int tipoCalculoId;
    private String tipoCalculo;
    private String abrev;

    public String getTipoCalculo() {
        return tipoCalculo;
    }

    public void setTipoCalculo(String tipoCalculo) {
        this.tipoCalculo = tipoCalculo;
    }

    public String getAbrev() {
        return abrev;
    }

    public void setAbrev(String abrev) {
        this.abrev = abrev;
    }

    public int getTipoCalculoId() {
        return tipoCalculoId;
    }

    public void setTipoCalculoId(int tipoCalculoId) {
        this.tipoCalculoId = tipoCalculoId;
    }    
    
}
