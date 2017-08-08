/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.entidades;

/**
 *
 * @author ealonso
 */
public class Componente {
    private String strYear;
    private String ramo;
    private String prg;
    private int componente;
    private String descr;
    private int meta; 
    private String indicadorSEI;
    private String indicadores;
    private String medios;
    private String supuestos;
    private String renglon;
    private int dimension = 3;
    
    //Para permisos de edicion inline
    private boolean normativo = false;
    private int etapa = 0;
    private int estatus = 0;
    private boolean politicaPublica = false;

    public String getStrYear() {
        return strYear;
    }

    public void setStrYear(String strYear) {
        this.strYear = strYear;
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

    public int getComponente() {
        return componente;
    }

    public void setComponente(int componente) {
        this.componente = componente;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public int getMeta() {
        return meta;
    }

    public void setMeta(int meta) {
        this.meta = meta;
    }

    public String getIndicadores() {
        return indicadores;
    }

    public void setIndicadores(String indicadores) {
        this.indicadores = indicadores;
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

    public String getRenglon() {
        return renglon;
    }

    public void setRenglon(String renglon) {
        this.renglon = renglon;
    }

    public String getIndicadorSEI() {
        return indicadorSEI;
    }

    public void setIndicadorSEI(String indicadorSEI) {
        this.indicadorSEI = indicadorSEI;
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

    public int getDimension() {
        return dimension;
    }

    
}
