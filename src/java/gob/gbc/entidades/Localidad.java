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
public class Localidad {

    private int localidadId;
    private String LocId;
    private String localidad;
    private String mpo;

    public int getLocalidadId() {
        return localidadId;
    }

    public void setLocalidadId(int localidadId) {
        this.localidadId = localidadId;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getMpo() {
        return mpo;
    }

    public void setMpo(String mpo) {
        this.mpo = mpo;
    }

    public String getLocId() {
        return LocId;
    }

    public void setLocId(String LocId) {
        this.LocId = LocId;
    }
    
    
}
