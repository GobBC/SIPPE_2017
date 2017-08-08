/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.entidades;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jarguelles
 */
public class Justificacion {

    private long id_justificacion;
    private String justificacion;

    public Justificacion() {
    }

    public long getId_justificacion() {
        return id_justificacion;
    }

    public void setId_justificacion(long id_justificacion) {
        this.id_justificacion = id_justificacion;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

}
