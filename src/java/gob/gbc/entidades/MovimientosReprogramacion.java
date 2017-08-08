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
public class MovimientosReprogramacion extends Movimiento {

    private Caratula movCaratula;
    private List<ReprogramacionMeta> movOficioMetaList;
    private List<ReprogramacionAccion> movOficioAccionList;

    public MovimientosReprogramacion() {
        super();
        movCaratula = new Caratula();
        movOficioMetaList = new ArrayList<ReprogramacionMeta>();
        movOficioAccionList = new ArrayList<ReprogramacionAccion>();
    }

    public Caratula getMovCaratula() {
        return movCaratula;
    }

    public void setMovCaratula(Caratula movCaratula) {
        this.movCaratula = movCaratula;
    }

    public List<ReprogramacionMeta> getMovOficioMetaList() {
        return movOficioMetaList;
    }

    public void setMovOficioMetaList(List<ReprogramacionMeta> movOficioMetaList) {
        this.movOficioMetaList = movOficioMetaList;
    }

    public List<ReprogramacionAccion> getMovOficioAccionList() {
        return movOficioAccionList;
    }

    public void setMovOficioAccionList(List<ReprogramacionAccion> movOficioAccionList) {
        this.movOficioAccionList = movOficioAccionList;
    }
}
