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
public class RecalendarizacionMeta {

    private int identificado;
    private List<MovOficioEstimacion> movEstimacionList;
    private double estimacion = 0.0;
    private double propuestaEstimacion = 0.0;
    private String valAutorizado = "P";

    public RecalendarizacionMeta() {
        movEstimacionList = new ArrayList<MovOficioEstimacion>();
    }

    public int getIdentificado() {
        return identificado;
    }

    public void setIdentificado(int identificado) {
        this.identificado = identificado;
    }

    public List<MovOficioEstimacion> getMovEstimacionList() {
        return movEstimacionList;
    }

    public void setMovEstimacionList(List<MovOficioEstimacion> movEstimacionList) {
        this.movEstimacionList = movEstimacionList;
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

    public String getValAutorizado() {
        return valAutorizado;
    }

    public void setValAutorizado(String valAutorizado) {
        this.valAutorizado = valAutorizado;
    }
    
}
