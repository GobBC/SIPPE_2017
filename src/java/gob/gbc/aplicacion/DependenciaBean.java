/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.Dependencia;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora; 
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ugarcia
 */
public class DependenciaBean extends ResultSQL {

    public DependenciaBean() throws Exception {
        super();
        
        Bitacora bitacora = new Bitacora();
    }
    
    public DependenciaBean(String tipoDependencia) throws Exception {
        super(tipoDependencia);
        
        Bitacora bitacora = new Bitacora();
    }

    public String getProgramaString(int programa) {
        String strProg = new String();
        strProg = String.valueOf(programa);
        if (strProg.length() == 1) {
            strProg = "00" + strProg;
        } else if (strProg.length() == 2) {
            strProg = "0" + strProg;
        }
        return strProg;
    }

    public String getRamoString(int ramo) {
        String strProg = new String();
        strProg = String.valueOf(ramo);
        if (strProg.length() == 1) {
            strProg = "0" + strProg;
        }
        return strProg;
    }

    public List<Dependencia> getDependenciaByRamoPrograma(String ramoId, String programaId, int year) throws SQLException {
        List<Dependencia> dependenciaList = new ArrayList<Dependencia>();
        dependenciaList = super.getResultDependenciasByRamoPrograma(ramoId, programaId, year);
        return dependenciaList;
    }

    public List<Dependencia> getDepartamentosByRamoPrograma(int ramoId, int programaId, int year) throws SQLException {
        List<Dependencia> dependenciaList = new ArrayList<Dependencia>();
        dependenciaList = super.getResultDepartamentosByRamoProgramaYear(getRamoString(ramoId), getProgramaString(programaId), year);
        return dependenciaList;
    }
}
