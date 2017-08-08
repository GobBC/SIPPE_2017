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
public class ProgramaConac {

    String anio;
    String programaConac;
    String descripcion;

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getProgramaConac() {
        return programaConac;
    }

    public void setProgramaConac(String programaConac) {
        this.programaConac = programaConac;
    }

    public String getDescripcion() {
        if (descripcion.equals("null")) {
            return "";
        } else {
            return descripcion;
        }
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
