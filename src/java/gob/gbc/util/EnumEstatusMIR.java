/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.util;

/**
 *
 * @author azatarain
 */
public enum EnumEstatusMIR {
    CANCELADA(0),
    BORRADOR(1),
    ENVIADA(2),
    RECHAZADA(3),
    VALIDADA(4),
    ENVIADA_POS(5),
    RECHAZADA_POS(6),
    VALIDADA_POS(7);
    
    private int proceso ;
    
    private EnumEstatusMIR(int proceso){
        this.proceso = proceso;
    }

    public int getProceso() {
        return proceso;
    }

    public void setProceso(int proceso) {
        this.proceso = proceso;
    }

}
