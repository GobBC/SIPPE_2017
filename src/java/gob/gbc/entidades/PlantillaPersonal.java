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
public class PlantillaPersonal {
    private String ramo;
    private int year;
    private String relLaboral;
    private String relLaboralDescr;
    private double Cantidad;

    public String getRamo() {
        return ramo;
    }

    public void setRamo(String ramo) {
        this.ramo = ramo;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRelLaboral() {
        return relLaboral;
    }

    public void setRelLaboral(String relLaboral) {
        this.relLaboral = relLaboral;
    }

    public double getCantidad() {
        return Cantidad;
    }

    public void setCantidad(double Cantidad) {
        this.Cantidad = Cantidad;
    }

    public String getRelLaboralDescr() {
        return relLaboralDescr;
    }

    public void setRelLaboralDescr(String relLaboralDescr) {
        this.relLaboralDescr = relLaboralDescr;
    }
    
    
}
