/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.Caratula;
import gob.gbc.entidades.RevisionCaratula;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jarguelles
 */
public class RevisionCaratulaBean extends ResultSQL {

    Bitacora bitacora;

    public RevisionCaratulaBean() throws Exception {
        super();
        bitacora = new Bitacora();
    }

    public RevisionCaratulaBean(String tipoDependecia) throws Exception {
        super(tipoDependecia);
        bitacora = new Bitacora();
    }

    public List<RevisionCaratula> getListRevisionesCaratulaByRamoCaratulaYear(String ramo, long caratula, int year, int yearSesion, int tipoModificacion, int tipoSesion) {
        List<RevisionCaratula> revisionesCaratulaList = new ArrayList<RevisionCaratula>();
        try {
            revisionesCaratulaList = super.getResultSQLGetListRevisionesCaratulaByRamoCaratulaYear(ramo, caratula, year, yearSesion, tipoModificacion, tipoSesion);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
            revisionesCaratulaList = null;
        }
        return revisionesCaratulaList;
    }

}
