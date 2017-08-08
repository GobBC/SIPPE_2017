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
public class AccionFuenteFinanciamiento {
    private int meta;
    private String descrMeta;
    private int accion;
    private String descrAccion;
    private double total;
    private boolean isSeleccionado;

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

    public int getAccion() {
        return accion;
    }

    public void setAccion(int accion) {
        this.accion = accion;
    }

    public String getDescrAccion() {
        return descrAccion;
    }

    public void setDescrAccion(String descrAccion) {
        this.descrAccion = descrAccion;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isIsSeleccionado() {
        return isSeleccionado;
    }

    public void setIsSeleccionado(boolean isSeleccionado) {
        this.isSeleccionado = isSeleccionado;
    }
    
    
}
