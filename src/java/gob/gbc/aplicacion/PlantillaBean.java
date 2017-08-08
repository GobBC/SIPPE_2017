/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.Plantilla;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jarguelles
 */
public class PlantillaBean extends ResultSQL {

    Bitacora bitacora;

    public PlantillaBean() throws Exception {
        super();

        bitacora = new Bitacora();
    }

    public PlantillaBean(String tipoDependecia) throws Exception {
        super(tipoDependecia);
        bitacora = new Bitacora();
    }

    public List<Plantilla> getPlantillasByRamoProgramaDepartamento(String ramoId, String programaId, String departamentoId, int year) throws SQLException {
        List<Plantilla> plantillaList = new ArrayList<Plantilla>();
        plantillaList = super.getResultPlantillasByRamoProgramaYear(ramoId, programaId, departamentoId, year);
        return plantillaList;
    }

    public List<Plantilla> getPlantillasByRamoYear(String ramoId, int year) throws SQLException {
        List<Plantilla> plantillaList = new ArrayList<Plantilla>();
        plantillaList = super.getResultPlantillasByRamoYear(ramoId, year);
        return plantillaList;
    }

    public List<Plantilla> getPlantillaAsignadosAuto(int year, String ramo) {
        List<Plantilla> plantillaList = new ArrayList<Plantilla>();
        try {
            plantillaList = super.getResultSQLgetPlantillaAsignadosAuto(year, ramo);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return plantillaList;
    }

    public List<Plantilla> getPlantillaAsignados(int year, String ramo) {
        List<Plantilla> plantillaList = new ArrayList<Plantilla>();
        try {
            plantillaList = super.getResultSQLgetPlantillaAsignados(year, ramo);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return plantillaList;
    }

    public boolean isPlantillaByRamoProgramaDeptoMetaAccionYear(String ramoId, String programaId, String deptoId, int metaId, int accionId, int year) throws SQLException {
        boolean bResultado = false;
        bResultado = super.getResultIsPlantillaByRamoProgramaDeptoMetaAccionYear(ramoId, programaId, deptoId, metaId, accionId, year);
        return bResultado;
    }

    public boolean existPlantillaByRamoProgramaDeptoMetaAccionYear(String ramoId, String programaId, String deptoId, int metaId, int accionId, int year) throws SQLException {
        boolean bResultado = false;
        bResultado = super.getResultExistPlantillaByRamoProgramaDeptoMetaAccionYear(ramoId, programaId, deptoId, metaId, accionId, year);
        return bResultado;
    }
    
    
    
}
