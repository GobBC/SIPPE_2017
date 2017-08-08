/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.gbc.entidades;

import java.util.Date;

/**
 *
 * @author ugarcia
 */
public class Indicador {
    private String year;
    private String ramo;
    private String programa;
    private String indicadorId;
    private String indicadores;
    private int dimension;
    private int componente;
    private int actividad;
    private String medios;
    private String supuestos;
    private String indicadorSEI;
    private Date fecha;
    
    /*Para Grid de Fin-Propósito*/
    private String tipoRegistro;
    private String finProposito;

    //Para permisos de edicion inline
    private boolean normativo = false;
    private int etapa = 0;
    private int estatus = 0;
    private boolean politicaPublica = false;

    public Indicador(){

    }

//    public Indicador(String year, String ramo, String programa, String indicadorId) {
//        this.year = year;
//        this.ramo = ramo;
//        this.programa = programa;
//        this.indicadorId = indicadorId;
//    }
    
    public Indicador(String year, String ramo, String programa, String indicadorSEI) {
        this.year = year;
        this.ramo = ramo;
        this.programa = programa;
        this.indicadorSEI = indicadorSEI;
    }
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRamo() {
        return ramo;
    }

    public void setRamo(String ramo) {
        this.ramo = ramo;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getIndicadorId() {
        return indicadorId;
    }

    public void setIndicadorId(String indicadorId) {
        this.indicadorId = indicadorId;
    }

    public String getIndicadores() {
        return indicadores;
    }

    public void setIndicadores(String indicadores) {
        this.indicadores = indicadores;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public int getComponente() {
        return componente;
    }

    public void setComponente(int componente) {
        this.componente = componente;
    }

    public int getActividad() {
        return actividad;
    }

    public void setActividad(int actividad) {
        this.actividad = actividad;
    }

    public String getMedios() {
        return medios;
    }

    public void setMedios(String medios) {
        this.medios = medios;
    }

    public String getSupuestos() {
        return supuestos;
    }

    public void setSupuestos(String supuestos) {
        this.supuestos = supuestos;
    }

    public String getIndicadorSEI() {
        return indicadorSEI;
    }

    public void setIndicadorSEI(String indicadorSEI) {
        this.indicadorSEI = indicadorSEI;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public String getFinProposito() {
        return finProposito;
    }

    public void setFinProposito(String finProposito) {
        this.finProposito = finProposito;
    }

    public boolean isNormativo() {
        return normativo;
    }

    public void setNormativo(boolean normativo) {
        this.normativo = normativo;
    }

    public int getEtapa() {
        return etapa;
    }

    public void setEtapa(int etapa) {
        this.etapa = etapa;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public boolean isPoliticaPublica() {
        return politicaPublica;
    }

    public void setPoliticaPublica(boolean politicaPublica) {
        this.politicaPublica = politicaPublica;
    }
    
    
}
