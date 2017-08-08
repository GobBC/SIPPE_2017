/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.entidades;

/**
 *
 * @author ugarcia
 */
public class Estimacion {

    private int year;    
    private String ramo;
    private String ramoDescr;
    private String prg;
    private String prgDescr;
    private int proy;
    private String proyDescr;
    private int meta;
    private String metaDescr;
    private int periodo;
    private double valor;

    public Estimacion() {
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

    public String getRamoDescr() {
        return ramoDescr;
    }

    public void setRamoDescr(String ramoDescr) {
        this.ramoDescr = ramoDescr;
    }

    public String getPrg() {
        return prg;
    }

    public void setPrg(String prg) {
        this.prg = prg;
    }

    public String getPrgDescr() {
        return prgDescr;
    }

    public void setPrgDescr(String prgDescr) {
        this.prgDescr = prgDescr;
    }

    public int getProy() {
        return proy;
    }

    public void setProy(int proy) {
        this.proy = proy;
    }

    public String getProyDescr() {
        return proyDescr;
    }

    public void setProyDescr(String proyDescr) {
        this.proyDescr = proyDescr;
    }

    public int getMeta() {
        return meta;
    }

    public void setMeta(int meta) {
        this.meta = meta;
    }

    public String getMetaDescr() {
        return metaDescr;
    }

    public void setMetaDescr(String metaDescr) {
        this.metaDescr = metaDescr;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    
}
