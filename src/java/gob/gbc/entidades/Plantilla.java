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
public class Plantilla {

    private String plantilla;
    private String prgId;
    private String depto;
    private String fuente;
    private String fondo;
    private String recurso;
    private int meta;
    private int accion;
    private int auto;
    private String fuenteFinanciamiento;
    private int seleccionado;
    private String descePrg;
    private String descrMeta;
    private String descrAccion;
    private String tipoGasto;
    private String descrDepto;

    public String getTipoGasto() {
        return tipoGasto;
    }

    public void setTipoGasto(String tipoGasto) {
        this.tipoGasto = tipoGasto;
    }       

    public int getAccion() {
        return accion;
    }

    public void setAccion(int accion) {
        this.accion = accion;
    }

    public String getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(String plantilla) {
        this.plantilla = plantilla;
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

    public int getMeta() {
        return meta;
    }

    public void setMeta(int meta) {
        this.meta = meta;
    }

    public String getDescrMeta() {
        return descrMeta;
    }

    public void setDescrMeta(String descrMeta) {
        this.descrMeta = descrMeta;
    }

    public String getDescrAccion() {
        return descrAccion;
    }

    public void setDescrAccion(String descrAccion) {
        this.descrAccion = descrAccion;
    }

    public int getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(int seleccionado) {
        this.seleccionado = seleccionado;
    }

    public String getPrgId() {
        return prgId;
    }

    public void setPrgId(String prgId) {
        this.prgId = prgId;
    }

    public String getFuenteFinanciamiento() {
        return fuenteFinanciamiento;
    }

    public void setFuenteFinanciamiento(String fuenteFinanciamiento) {
        this.fuenteFinanciamiento = fuenteFinanciamiento;
    }

    public String getDescePrg() {
        return descePrg;
    }

    public void setDescePrg(String descePrg) {
        this.descePrg = descePrg;
    }   

    public String getDepto() {
        return depto;
    }

    public void setDepto(String depto) {
        this.depto = depto;
    }

    public String getDescrDepto() {
        return descrDepto;
    }

    public void setDescrDepto(String descrDepto) {
        this.descrDepto = descrDepto;
    }

    public int getAuto() {
        return auto;
    }

    public void setAuto(int auto) {
        this.auto = auto;
    }
}
