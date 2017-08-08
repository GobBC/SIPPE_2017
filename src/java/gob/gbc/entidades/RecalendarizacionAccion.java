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
public class RecalendarizacionAccion {

    private int identificado;
    private List<MovOficioAccionEstimacion> movAccionEstimacionList;
    private double estimacion = 0.0;
    private double propuestaEstimacion = 0.0;
    private String valAutorizacion = "P";

    public RecalendarizacionAccion() {
        movAccionEstimacionList = new ArrayList<MovOficioAccionEstimacion>();
    }

    public int getIdentificado() {
        return identificado;
    }

    public void setIdentificado(int identificado) {
        this.identificado = identificado;
    }

    public List<MovOficioAccionEstimacion> getMovEstimacionList() {
        return movAccionEstimacionList;
    }

    public void setMovEstimacionList(List<MovOficioAccionEstimacion> movAccionEstimacionList) {
        this.movAccionEstimacionList = movAccionEstimacionList;
    }

    public double getEstimacion() {
        return estimacion;
    }

    public void setEstimacion(double estimacion) {
        this.estimacion = estimacion;
    }

    public double getPropuestaEstimacion() {
        return propuestaEstimacion;
    }

    public void setPropuestaEstimacion(double propuestaEstimacion) {
        this.propuestaEstimacion = propuestaEstimacion;
    }

    public List<MovOficioAccionEstimacion> getMovAccionEstimacionList() {
        return movAccionEstimacionList;
    }

    public void setMovAccionEstimacionList(List<MovOficioAccionEstimacion> movAccionEstimacionList) {
        this.movAccionEstimacionList = movAccionEstimacionList;
    }

    public String getValAutorizacion() {
        return valAutorizacion;
    }

    public void setValAutorizacion(String Autorizacion) {
        this.valAutorizacion = Autorizacion;
    }
    
}
