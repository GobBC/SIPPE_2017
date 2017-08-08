/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.gbc.util;

/**
 *
 * @author ugarcia
 */
public enum EnumProcesoEspecial {
    NORMATIVO_META(1),
    NORMATIVO_AVANCE(2),
    PROCESO_PARAMETROS(3);
    
    private int proceso ;
    
    private EnumProcesoEspecial(int proceso){
        this.proceso = proceso;
    }

    public int getProceso() {
        return proceso;
    }

    public void setProceso(int proceso) {
        this.proceso = proceso;
    }
}
