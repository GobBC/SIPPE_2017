/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.Proyecto;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List; 

/**
 *
 * @author ugarcia
 */
public class ProyectoBean extends ResultSQL {

    Bitacora bitacora;

    public ProyectoBean() throws Exception {
        super();               
        
        bitacora = new Bitacora();
    }
    
    public ProyectoBean(String tipoDependecia) throws Exception {
        super(tipoDependecia);
        bitacora = new Bitacora();
    }

    public List<Proyecto> getProyectosByPrograma(String ramoId, String programaId, int year) {
        List<Proyecto> proyectoList = new ArrayList<Proyecto>();
        try {
            proyectoList = super.getResultSQLProyectosByPrograma(ramoId, programaId, year);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return proyectoList;
    }
    
    public Proyecto getProyectoById(String ramoId, String programaId, int proyectoId, int year, String tipoProy){
        Proyecto proyecto = new Proyecto();
        try {
            proyecto = super.getResultSQLProyectoById(ramoId, programaId, proyectoId, year,tipoProy);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return proyecto;
    }

    public Proyecto getProyectoByTipoProyecto(String ramoId, String programaId, int proyectoId, int year, String tipoProyecto) {
        Proyecto proyecto = new Proyecto();
        try {
            proyecto = super.getResultSQLProyectoByTipoProyecto(ramoId, programaId, proyectoId, year, tipoProyecto);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return proyecto;
    }
}
