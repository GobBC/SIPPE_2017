/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.entidades;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jarguelles
 */
public class MovimientosTransferencia extends Movimiento {

    private Caratula movCaratula;
    private List<TransferenciaMeta> transferenciaMetaList;
    private List<TransferenciaAccion> transferenciaAccionList;
    private List<Transferencia> transferenciaList;

    public MovimientosTransferencia() {
        movCaratula = new Caratula();
        transferenciaMetaList = new ArrayList<TransferenciaMeta>();
        transferenciaAccionList = new ArrayList<TransferenciaAccion>();
        transferenciaList = new ArrayList<Transferencia>();
    }

    public Caratula getMovCaratula() {
        return movCaratula;
    }

    public void setMovCaratula(Caratula movCaratula) {
        this.movCaratula = movCaratula;
    }

    public List<TransferenciaMeta> getTransferenciaMetaList() {
        return transferenciaMetaList;
    }

    public void setTransferenciaMetaList(List<TransferenciaMeta> transferenciaMetaList) {
        this.transferenciaMetaList = transferenciaMetaList;
    }

    public List<TransferenciaAccion> getTransferenciaAccionList() {
        return transferenciaAccionList;
    }

    public void setTransferenciaAccionList(List<TransferenciaAccion> transferenciaAccionList) {
        this.transferenciaAccionList = transferenciaAccionList;
    }

    public List<Transferencia> getTransferenciaList() {
        return transferenciaList;
    }

    public void setTransferenciaList(List<Transferencia> transferenciaRecibenList) {
        this.transferenciaList = transferenciaRecibenList;
    }
}