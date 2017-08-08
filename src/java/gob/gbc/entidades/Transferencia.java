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
public class Transferencia extends Movimiento implements Cloneable{

    private int folio;
    private int consec;
    private String ramo;
    private String depto;
    private String finalidad;
    private String funcion;
    private String subfuncion;
    private String prgConac;
    private String programa;
    private String tipoProy;
    private int proyecto;
    private int meta;
    private int accion;
    private String partida;
    private String tipoGasto;
    private String fuente;
    private String fondo;
    private String recurso;
    private String municipio;
    private String tipoOficio;
    private int delegacion;
    private String relLaboral;
    private double importe;
    private double quincePor;
    private double disponible;
    private double disponibleAnual;
    private double acumulado;
    private String estatus;
    private String transitorio;
    private String considerar;
    private List<TransferenciaAccionReq> transferenciaAccionReqList;


    public Transferencia() {
        transferenciaAccionReqList = new ArrayList<TransferenciaAccionReq>();
    }

    public List<TransferenciaAccionReq> getTransferenciaAccionReqList() {
        return transferenciaAccionReqList;
    }

    public void setTransferenciaAccionReqList(List<TransferenciaAccionReq> transferenciaAccionReqList) {
        this.transferenciaAccionReqList = transferenciaAccionReqList;
    }

    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public int getConsec() {
        return consec;
    }

    public void setConsec(int consec) {
        this.consec = consec;
    }

    public String getRamo() {
        return ramo;
    }

    public void setRamo(String ramo) {
        this.ramo = ramo;
    }

    public String getDepto() {
        return depto;
    }

    public void setDepto(String depto) {
        this.depto = depto;
    }

    public String getFinalidad() {
        return finalidad;
    }

    public void setFinalidad(String finalidad) {
        this.finalidad = finalidad;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public String getSubfuncion() {
        return subfuncion;
    }

    public void setSubfuncion(String subfuncion) {
        this.subfuncion = subfuncion;
    }

    public String getPrgConac() {
        return prgConac;
    }

    public void setPrgConac(String prgConac) {
        this.prgConac = prgConac;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getTipoProy() {
        return tipoProy;
    }

    public void setTipoProy(String tipoProy) {
        this.tipoProy = tipoProy;
    }

    public int getProyecto() {
        return proyecto;
    }

    public void setProyecto(int proyecto) {
        this.proyecto = proyecto;
    }

    public int getMeta() {
        return meta;
    }

    public void setMeta(int meta) {
        this.meta = meta;
    }

    public int getAccion() {
        return accion;
    }

    public void setAccion(int accion) {
        this.accion = accion;
    }

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public String getTipoGasto() {
        return tipoGasto;
    }

    public void setTipoGasto(String tipoGasto) {
        this.tipoGasto = tipoGasto;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public String getFondo() {
        return fondo;
    }

    public void setFondo(String fondo) {
        this.fondo = fondo;
    }

    public String getRecurso() {
        return recurso;
    }

    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public int getDelegacion() {
        return delegacion;
    }

    public void setDelegacion(int delegacion) {
        this.delegacion = delegacion;
    }

    public String getRelLaboral() {
        return relLaboral;
    }

    public void setRelLaboral(String relLaboral) {
        this.relLaboral = relLaboral;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getQuincePor() {
        return quincePor;
    }

    public void setQuincePor(double quincePor) {
        this.quincePor = quincePor;
    }

    public double getDisponible() {
        return disponible;
    }

    public void setDisponible(double disponible) {
        this.disponible = disponible;
    }

    public double getAcumulado() {
        return acumulado;
    }

    public void setAcumulado(double acumulado) {
        this.acumulado = acumulado;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
    
    public String getTipoOficio() {
        return tipoOficio;
    }

    public void setTipoOficio(String tipoOficio) {
        this.tipoOficio = tipoOficio;
    } 

    public String getTransitorio() {
        return transitorio;
    }

    public void setTransitorio(String transitorio) {
        this.transitorio = transitorio;
    }

    public String getConsiderar() {
        return considerar;
    }

    public void setConsiderar(String considerar) {
        this.considerar = considerar;
    }

    public double getDisponibleAnual() {
        return disponibleAnual;
    }

    public void setDisponibleAnual(double disponibleAnual) {
        this.disponibleAnual = disponibleAnual;
    }
    
    
    
    @Override
    public Transferencia clone() {
        try {
            return (Transferencia)super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new Transferencia();
            // This should never happen
        }
    }
    
}
