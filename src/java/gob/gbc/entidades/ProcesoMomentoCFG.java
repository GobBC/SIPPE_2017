/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.entidades;

/**
 *
 * @author rharo
 */
public class ProcesoMomentoCFG {
    private int proceso;
    private int consec;
    private int tipoMomento;
    private int momento;
    private int momentoRelacionado;
    private int momentoPoliza;

    public int getProceso() {
        return proceso;
    }

    public void setProceso(int proceso) {
        this.proceso = proceso;
    }

    public int getConsec() {
        return consec;
    }

    public void setConsec(int consec) {
        this.consec = consec;
    }

    public int getTipoMomento() {
        return tipoMomento;
    }

    public void setTipoMomento(int tipoMomento) {
        this.tipoMomento = tipoMomento;
    }

    public int getMomento() {
        return momento;
    }

    public void setMomento(int momento) {
        this.momento = momento;
    }

    public int getMomentoRelacionado() {
        return momentoRelacionado;
    }

    public void setMomentoRelacionado(int momentoRelacionado) {
        this.momentoRelacionado = momentoRelacionado;
    }

    public int getMomentoPoliza() {
        return momentoPoliza;
    }

    public void setMomentoPoliza(int momentoPoliza) {
        this.momentoPoliza = momentoPoliza;
    }
    
    
}
