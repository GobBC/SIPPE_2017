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
public class MovoficioLigado {

    private int year;
    private int oficio;
    private int oficioLigado;
    private boolean pendiente;
    private String ramo;
    private String ramoDescr;
    private String ligadoParaestatal;

    public MovoficioLigado() {
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getOficio() {
        return oficio;
    }

    public void setOficio(int oficio) {
        this.oficio = oficio;
    }

    public int getOficioLigado() {
        return oficioLigado;
    }

    public void setOficioLigado(int oficioLigado) {
        this.oficioLigado = oficioLigado;
    }

    public boolean isPendiente() {
        return pendiente;
    }

    public void setPendiente(boolean pendiente) {
        this.pendiente = pendiente;
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

    public String getLigadoParaestatal() {
        return ligadoParaestatal;
    }

    public void setLigadoParaestatal(String ligadoParaestatal) {
        this.ligadoParaestatal = ligadoParaestatal;
    }

    public boolean isLigadoParaestatal() {
        
        boolean isLigadoParaestatal = false;
        
        if(ligadoParaestatal.equalsIgnoreCase("S"))
            isLigadoParaestatal = true;
        
        return isLigadoParaestatal;
    }

}
