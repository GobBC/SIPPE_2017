/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.entidades;

/**
 *
 * @author ealonso
 */

import gob.gbc.entidades.Ramo;
import java.util.List;

public class UsuarioSession {
    private String strUsuario;
    private String year;
    private String tipoDependencia;
    private String tipoUsuario;
    private List<Ramo> ramoList;
    private String rol;
    private String rolNormativo;
    private boolean normativo;

    public String getStrUsuario() {
        return strUsuario;
    }

    public void setStrUsuario(String strUsuario) {
        this.strUsuario = strUsuario;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTipoDependencia() {
        return tipoDependencia;
    }

    public void setTipoDependencia(String tipoDependencia) {
        this.tipoDependencia = tipoDependencia;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public List<Ramo> getRamoList() {
        return ramoList;
    }

    public void setRamoList(List<Ramo> ramoList) {
        this.ramoList = ramoList;
    }   

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getRolNormativo() {
        return rolNormativo;
    }

    public void setRolNormativo(String rolNormativo) {
        this.rolNormativo = rolNormativo;
    }

    public boolean isNormativo() {
        return normativo;
    }

    public void setNormativo(boolean normativo) {
        this.normativo = normativo;
    }
    
}
