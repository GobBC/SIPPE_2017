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
public class MovimientosRecalendarizacion extends Movimiento{   
    
    private List<RecalendarizacionMeta> movEstimacionList;
    private List<RecalendarizacionAccion> movAccionEstimacionList;
    private List<RecalendarizacionAccionReq> movOficiosAccionReq;
    
    public MovimientosRecalendarizacion() {
        super();
        movEstimacionList = new ArrayList<RecalendarizacionMeta>();
        movAccionEstimacionList = new ArrayList<RecalendarizacionAccion>();
        movOficiosAccionReq = new ArrayList<RecalendarizacionAccionReq>();
    }

    public List<RecalendarizacionMeta> getMovEstimacionList() {
        return movEstimacionList;
    }

    public void setMovEstimacionList(List<RecalendarizacionMeta> movEstimacionList) {
        this.movEstimacionList = movEstimacionList;
    }

    public List<RecalendarizacionAccion> getMovAccionEstimacionList() {
        return movAccionEstimacionList;
    }

    public void setMovAccionEstimacionList(List<RecalendarizacionAccion> movAccionEstimacionList) {
        this.movAccionEstimacionList = movAccionEstimacionList;
    }

    public List<RecalendarizacionAccionReq> getMovOficiosAccionReq() {
        return movOficiosAccionReq;
    }

    public void setMovOficiosAccionReq(List<RecalendarizacionAccionReq> movOficiosAccionReq) {
        this.movOficiosAccionReq = movOficiosAccionReq;
    }
    
}
