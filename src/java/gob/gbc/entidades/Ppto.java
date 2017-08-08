/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.entidades;

import java.util.ArrayList;

/**
 *
 * @author jarguelles
 */
public class Ppto {

    private int year;
    private String ramo;
    private String depto;
    private String finalidad;
    private String funcion;
    private String subfuncion;
    private String prgConac;
    private String prg;
    private String tipoProy;
    private int proyecto;
    private String proyectoId;
    private int meta;
    private String metaId;
    private int accion;
    private String accionId;
    private String partida;
    private String tipoGasto;
    private String fuente;
    private String fondo;
    private String recurso;
    private String municipio;
    private int delegacion;
    private String delegacionId;
    private String relLaboral;
    private int mes;
    private double asignado;
    private int actualizado;
    private int compromiso;
    private int ejercido;
    private int original;
    private int mcAprobado;
    private int mcModificado;
    private int mcPptoXEjercer;
    private int mcComprometido;
    private int mcDevengado;
    private int mcEjercido;
    private int mcPagado;
    private int mcAprobCgo;
    private int mcAprobAbn;
    private int mcModCgo;
    private int mcModAbn;
    private int mcPXEjerCgo;
    private int mcPXEjerAbn;
    private int mcCompCgo;
    private int mcCompAbn;
    private int mcDevAbn;
    private int mcDevCgo;
    private int mcEjerCgo;
    private int mcEjerAbn;
    private int mcPagCgo;
    private int mcPagAbn;
    //No es parte de la tabla
    private String origen;
    private String descrPlantilla;
    private ArrayList dMeses;
    public Ppto() {
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

    public String getPrg() {
        return prg;
    }

    public void setPrg(String prg) {
        this.prg = prg;
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

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public double getAsignado() {
        return asignado;
    }

    public void setAsignado(double asignado) {
        this.asignado = asignado;
    }

    public int getActualizado() {
        return actualizado;
    }

    public void setActualizado(int actualizado) {
        this.actualizado = actualizado;
    }

    public int getCompromiso() {
        return compromiso;
    }

    public void setCompromiso(int copromiso) {
        this.compromiso = compromiso;
    }

    public int getEjercido() {
        return ejercido;
    }

    public void setEjercido(int ejercido) {
        this.ejercido = ejercido;
    }

    public int getOriginal() {
        return original;
    }

    public void setOriginal(int original) {
        this.original = original;
    }

    public int getMcAprobado() {
        return mcAprobado;
    }

    public void setMcAprobado(int mcAprobado) {
        this.mcAprobado = mcAprobado;
    }

    public int getMcModificado() {
        return mcModificado;
    }

    public void setMcModificado(int mcModificado) {
        this.mcModificado = mcModificado;
    }

    public int getMcPptoXEjercer() {
        return mcPptoXEjercer;
    }

    public void setMcPptoXEjercer(int mcPptoXEjercer) {
        this.mcPptoXEjercer = mcPptoXEjercer;
    }

    public int getMcComprometido() {
        return mcComprometido;
    }

    public void setMcComprometido(int mcComprometido) {
        this.mcComprometido = mcComprometido;
    }

    public int getMcDevengado() {
        return mcDevengado;
    }

    public void setMcDevengado(int mcDevengado) {
        this.mcDevengado = mcDevengado;
    }

    public int getMcEjercido() {
        return mcEjercido;
    }

    public void setMcEjercido(int mcEjercido) {
        this.mcEjercido = mcEjercido;
    }

    public int getMcPagado() {
        return mcPagado;
    }

    public void setMcPagado(int mcPagado) {
        this.mcPagado = mcPagado;
    }

    public int getMcAprobCgo() {
        return mcAprobCgo;
    }

    public void setMcAprobCgo(int mcAprobCgo) {
        this.mcAprobCgo = mcAprobCgo;
    }

    public int getMcAprobAbn() {
        return mcAprobAbn;
    }

    public void setMcAprobAbn(int mcAprobAbn) {
        this.mcAprobAbn = mcAprobAbn;
    }

    public int getMcModCgo() {
        return mcModCgo;
    }

    public void setMcModCgo(int mcModCgo) {
        this.mcModCgo = mcModCgo;
    }

    public int getMcModAbn() {
        return mcModAbn;
    }

    public void setMcModAbn(int mcModAbn) {
        this.mcModAbn = mcModAbn;
    }

    public int getMcPXEjerCgo() {
        return mcPXEjerCgo;
    }

    public void setMcPXEjerCgo(int mcPXEjerCgo) {
        this.mcPXEjerCgo = mcPXEjerCgo;
    }

    public int getMcPXEjerAbn() {
        return mcPXEjerAbn;
    }

    public void setMcPXEjerAbn(int mcPXEjerAbn) {
        this.mcPXEjerAbn = mcPXEjerAbn;
    }

    public int getMcCompCgo() {
        return mcCompCgo;
    }

    public void setMcCompCgo(int mcCompCgo) {
        this.mcCompCgo = mcCompCgo;
    }

    public int getMcCompAbn() {
        return mcCompAbn;
    }

    public void setMcCompAbn(int mcCompAbn) {
        this.mcCompAbn = mcCompAbn;
    }

    public int getMcDevAbn() {
        return mcDevAbn;
    }

    public void setMcDevAbn(int mcDevAbn) {
        this.mcDevAbn = mcDevAbn;
    }

    public int getMcDevCgo() {
        return mcDevCgo;
    }

    public void setMcDevCgo(int mcDevCgo) {
        this.mcDevCgo = mcDevCgo;
    }

    public int getMcEjerCgo() {
        return mcEjerCgo;
    }

    public void setMcEjerCgo(int mcEjerCgo) {
        this.mcEjerCgo = mcEjerCgo;
    }

    public int getMcEjerAbn() {
        return mcEjerAbn;
    }

    public void setMcEjerAbn(int mcEjerAbn) {
        this.mcEjerAbn = mcEjerAbn;
    }

    public int getMcPagCgo() {
        return mcPagCgo;
    }

    public void setMcPagCgo(int mcPagCgo) {
        this.mcPagCgo = mcPagCgo;
    }

    public int getMcPagAbn() {
        return mcPagAbn;
    }

    public void setMcPagAbn(int mcPagAbn) {
        this.mcPagAbn = mcPagAbn;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(String proyectoId) {
        this.proyectoId = proyectoId;
    }

    public String getMetaId() {
        return metaId;
    }

    public void setMetaId(String metaId) {
        this.metaId = metaId;
    }

    public String getAccionId() {
        return accionId;
    }

    public void setAccionId(String accionId) {
        this.accionId = accionId;
    }

    public String getDelegacionId() {
        return delegacionId;
    }

    public void setDelegacionId(String delegacionId) {
        this.delegacionId = delegacionId;
    }

    public String getDescrPlantilla() {
        return descrPlantilla;
    }

    public void setDescrPlantilla(String descrPlantilla) {
        this.descrPlantilla = descrPlantilla;
    }

    /**
     * @return the dMeses
     */
    public ArrayList getdMeses() {
        return dMeses;
    }

    /**
     * @param dMeses the dMeses to set
     */
    public void setdMeses(ArrayList dMeses) {
        this.dMeses = dMeses;
    }
    
}
