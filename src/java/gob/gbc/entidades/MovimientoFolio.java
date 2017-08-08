/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.entidades;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jarguelles
 */
public class MovimientoFolio {

    private int movimiento;
    private int tabla;
    private int folio;
    private long id_justificacion;
    private String ramo;
    private String prg;
    private String proyecto_abr;
    private int meta;
    private int accion;
    private String depto;
    private String partida;
    private String ffr_abr;
    private BigDecimal importe;
    private boolean Asignado;
    private int diferenciador;
    private String tipoProy;
    private int proyecto;
    private String fuente;
    private String fondo;
    private String recurso;

    public MovimientoFolio() {
    }

    public int getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(int movimiento) {
        this.movimiento = movimiento;
    }

    public int getTabla() {
        return tabla;
    }

    public void setTabla(int tabla) {
        this.tabla = tabla;
    }

    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public long getId_justificacion() {
        return id_justificacion;
    }

    public void setId_justificacion(long id_justificacion) {
        this.id_justificacion = id_justificacion;
    }

    public String getRamo() {
        return ramo;
    }

    public void setRamo(String ramo) {
        this.ramo = ramo;
    }

    public String getPrg() {
        return prg;
    }

    public void setPrg(String prg) {
        this.prg = prg;
    }

    public String getProyecto_abr() {
        return proyecto_abr;
    }

    public void setProyecto_abr(String proyecto_abr) {
        this.proyecto_abr = proyecto_abr;
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

    public String getDepto() {
        return depto;
    }

    public void setDepto(String depto) {
        this.depto = depto;
    }

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public String getFfr_abr() {
        return ffr_abr;
    }

    public void setFfr_abr(String ffr_abr) {
        this.ffr_abr = ffr_abr;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public boolean isAsignado() {
        return Asignado;
    }

    public void setAsignado(boolean Asignado) {
        this.Asignado = Asignado;
    }

    public int getDiferenciador() {
        return diferenciador;
    }

    public void setDiferenciador(int diferenciador) {
        this.diferenciador = diferenciador;
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



}
