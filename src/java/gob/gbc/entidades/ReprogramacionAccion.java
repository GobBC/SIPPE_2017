/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.entidades;

import gob.gbc.entidades.MovOficioAccionEstimacion;
import gob.gbc.entidades.MovimientoOficioAccion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ugarcia
 */
public class ReprogramacionAccion {

    private int identificador;
    private List<MovOficioAccionEstimacion> movAcionEstimacionList;
    private MovimientoOficioAccion movOficioAccion;
    private double estimacion = 0.0;
    private double propuestaEstimacion = 0.0;
    private int metaAux = 0;
    private String valAutorizado = "P";

    public ReprogramacionAccion() {
        identificador = 0;
        movAcionEstimacionList = new ArrayList<MovOficioAccionEstimacion>();
        movOficioAccion = new MovimientoOficioAccion();
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public List<MovOficioAccionEstimacion> getMovAcionEstimacionList() {
        return movAcionEstimacionList;
    }

    public void setMovAcionEstimacionList(List<MovOficioAccionEstimacion> movAcionEstimacionList) {
        this.movAcionEstimacionList = movAcionEstimacionList;
    }

    public MovimientoOficioAccion getMovOficioAccion() {
        return movOficioAccion;
    }

    public void setMovOficioAccion(MovimientoOficioAccion movOficioAccion) {
        this.movOficioAccion = movOficioAccion;
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

    public int getMetaAux() {
        return metaAux;
    }

    public void setMetaAux(int metaAux) {
        this.metaAux = metaAux;
    }

    public String getValAutorizado() {
        return valAutorizado;
    }

    public void setValAutorizado(String valAutorizado) {
        this.valAutorizado = valAutorizado;
    }
    
    
    
}
