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
public class TransferenciaMeta {

    private int identificador;
    private MovimientoOficioMeta movOficioMeta;
    private List<MovOficioEstimacion> movOficioEstimacionList;
    private double estimacion = 0.0;
    private double propuestaEstimacion = 0.0;
    private String valAutorizado = "P";

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public MovimientoOficioMeta getMovOficioMeta() {
        return movOficioMeta;
    }

    public void setMovOficioMeta(MovimientoOficioMeta movOficioMeta) {
        this.movOficioMeta = movOficioMeta;
    }

    public List<MovOficioEstimacion> getMovOficioEstimacion() {
        return movOficioEstimacionList;
    }

    public void setMovOficioEstimacion(List<MovOficioEstimacion> movOficioEstimacionList) {
        this.movOficioEstimacionList = movOficioEstimacionList;
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

    public List<MovOficioEstimacion> getMovOficioEstimacionList() {
        return movOficioEstimacionList;
    }

    public void setMovOficioEstimacionList(List<MovOficioEstimacion> movOficioEstimacionList) {
        this.movOficioEstimacionList = movOficioEstimacionList;
    }

    public String getValAutorizado() {
        return valAutorizado;
    }

    public void setValAutorizado(String valAutorizado) {
        this.valAutorizado = valAutorizado;
    }

}
