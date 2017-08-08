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
 * @author ugarcia
 */
public class MovimientosAmpliacionReduccion extends Movimiento {

    private Caratula movCaratula;
    private List<AmpliacionReduccionMeta> ampReducMetaList;
    private List<AmpliacionReduccionAccion> ampReducAccionList;
    private List<AmpliacionReduccionAccionReq> ampReducAccionReqList;

    public MovimientosAmpliacionReduccion() {
        movCaratula = new Caratula();
        ampReducMetaList = new ArrayList<AmpliacionReduccionMeta>();
        ampReducAccionList = new ArrayList<AmpliacionReduccionAccion>();
        ampReducAccionReqList = new ArrayList<AmpliacionReduccionAccionReq>();
    }

    public List<AmpliacionReduccionMeta> getAmpReducMetaList() {
        return ampReducMetaList;
    }

    public void setAmpReducMetaList(List<AmpliacionReduccionMeta> ampReducMetaList) {
        this.ampReducMetaList = ampReducMetaList;
    }

    public List<AmpliacionReduccionAccion> getAmpReducAccionList() {
        return ampReducAccionList;
    }

    public void setAmpReducAccionList(List<AmpliacionReduccionAccion> ampReducAccionList) {
        this.ampReducAccionList = ampReducAccionList;
    }

    public List<AmpliacionReduccionAccionReq> getAmpReducAccionReqList() {
        return ampReducAccionReqList;
    }

    public void setAmpReducAccionReqList(List<AmpliacionReduccionAccionReq> ampReducAccionReqList) {
        this.ampReducAccionReqList = ampReducAccionReqList;
    }

    public Caratula getMovCaratula() {
        return movCaratula;
    }

    public void setMovCaratula(Caratula movCaratula) {
        this.movCaratula = movCaratula;
    }
}
