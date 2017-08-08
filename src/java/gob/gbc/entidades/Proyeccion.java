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
public class Proyeccion {
    private String ramo;
    private String ramoDescr;
    private String programa;
    private String programaDescr;
    private String partida;
    private String partidaDescr;
    private String relLaboral;
    
    private double proyectado;

    public String getRamo() {
        return ramo;
    }

    public void setRamo(String ramo) {
        this.ramo = ramo;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public double getProyectado() {
        return proyectado;
    }

    public void setProyectado(double proyectado) {
        this.proyectado = proyectado;
    }

    public String getRamoDescr() {
        return ramoDescr;
    }

    public void setRamoDescr(String ramoDescr) {
        this.ramoDescr = ramoDescr;
    }

    public String getProgramaDescr() {
        return programaDescr;
    }

    public void setProgramaDescr(String programaDescr) {
        this.programaDescr = programaDescr;
    }

    public String getPartidaDescr() {
        return partidaDescr;
    }

    public void setPartidaDescr(String partidaDescr) {
        this.partidaDescr = partidaDescr;
    }

    public String getRelLaboral() {
        return relLaboral;
    }

    public void setRelLaboral(String relLaboral) {
        this.relLaboral = relLaboral;
    }
}
