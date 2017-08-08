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
public class ReprogramacionMeta {

    private int identificador;
    private List<MovOficioEstimacion> movEstimacionList;
    private MovimientoOficioMeta metaInfo;
    private double estimacion = 0.0;
    private double propuestaEstimacion = 0.0;
    private String valAutorizado = "P";

    public ReprogramacionMeta() {
        movEstimacionList = new ArrayList<MovOficioEstimacion>();
        metaInfo = new MovimientoOficioMeta();
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public List<MovOficioEstimacion> getMovEstimacionList() {
        return movEstimacionList;
    }

    public void setMovEstimacionList(List<MovOficioEstimacion> movEstimacionList) {
        this.movEstimacionList = movEstimacionList;
    }

    public MovimientoOficioMeta getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(MovimientoOficioMeta metaInfo) {
        this.metaInfo = metaInfo;
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
