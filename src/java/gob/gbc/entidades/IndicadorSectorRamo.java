/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.entidades;

/**
 *
 * @author jarguelles
 */
public class IndicadorSectorRamo {

    private String year;
    private String claveIndicador;
    private String nombreIndicador;
    private String ramo;
    private String ramoDescr;
    private String prg;
    private String prgDescr;

    public IndicadorSectorRamo() {
    }

    public String getClaveIndicador() {
        return claveIndicador;
    }

    public void setClaveIndicador(String claveIndicador) {
        this.claveIndicador = claveIndicador;
    }

    public String getNombreIndicador() {
        return nombreIndicador;
    }

    public void setNombreIndicador(String nombreIndicador) {
        this.nombreIndicador = nombreIndicador;
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

}
