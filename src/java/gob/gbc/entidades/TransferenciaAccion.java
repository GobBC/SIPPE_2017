/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.entidades;

import java.util.List;

/**
 *
 * @author jarguelles
 */
public class TransferenciaAccion {

    private int identificador;
    private MovimientoOficioAccion movOficioAccion;
    private List<MovOficioAccionEstimacion> movOficioAccionEstList;
    private double estimacion = 0.0;
    private double propuestaEstimacion = 0.0;
    private String valAutorizado = "P";

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public MovimientoOficioAccion getMovOficioAccion() {
        return movOficioAccion;
    }

    public void setMovOficioAccion(MovimientoOficioAccion movOficioAccion) {
        this.movOficioAccion = movOficioAccion;
    }

    public List<MovOficioAccionEstimacion> getMovOficioAccionEstList() {
        return movOficioAccionEstList;
    }

    public void setMovOficioAccionEstList(List<MovOficioAccionEstimacion> movOficioAccionEstList) {
        this.movOficioAccionEstList = movOficioAccionEstList;
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
