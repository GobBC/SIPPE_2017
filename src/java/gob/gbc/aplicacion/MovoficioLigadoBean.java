/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.MovoficioLigado;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jarguelles
 */
public class MovoficioLigadoBean extends ResultSQL {

    Bitacora bitacora;

    public MovoficioLigadoBean() throws Exception {
        super();
        bitacora = new Bitacora();
    }

    public MovoficioLigadoBean(String tipoDependecia) throws Exception {
        super(tipoDependecia);
        bitacora = new Bitacora();
    }

    public boolean isParaestatal() {
        boolean isParaestatal = false;
        try {
            isParaestatal = super.getResultSQLisParaestatal();
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return isParaestatal;
    }

    public String getRolNormativo() {
        String normativo = new String();
        try {
            normativo = super.getResultSQLGetRolesPrg();
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return normativo;
    }

    public List<MovoficioLigado> getMovOficioLigadoPendiente(int year, int oficio, String tipoProceso) {
        List<MovoficioLigado> movOficiosLigados = new ArrayList<MovoficioLigado>();
        try {
            movOficiosLigados = super.getResultGetMovOficioLigadoPendiente(year, oficio, tipoProceso);
        } catch (Exception ex) {
            super.transaccionRollback();
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return movOficiosLigados;
    }

    public boolean isOficioLigado(int year, int oficio, String isParaestatal) {
        boolean isOficioLigado = false;
        try {
            isOficioLigado = super.getResultSQLIsOficioLigado(year, oficio, isParaestatal);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return isOficioLigado;
    }

    public boolean isOficioLigadoNormativo(int year, int oficio) {
        boolean isOficioLigado = false;
        try {
            isOficioLigado = super.getResultSQLIsOficioLigadoNormativo(year, oficio);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return isOficioLigado;
    }

    public List<MovoficioLigado> getMovOficioLigados(int year, int oficio) {
        List<MovoficioLigado> movOficiosLigados = new ArrayList<MovoficioLigado>();
        try {
            movOficiosLigados = super.getResultGetMovOficioLigados(year, oficio);
        } catch (Exception ex) {
            super.transaccionRollback();
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return movOficiosLigados;
    }

    public int getOficioLigadoNormativo(int year, int oficioLigado, String isParaestatal) {
        int oficioNormativo = 0;
        try {
            oficioNormativo = super.getResultSQLGetOficioLigadoNormativo(year, oficioLigado, isParaestatal);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return oficioNormativo;
    }

    public String getRamoByOficio(int oficio) {
        String ramo = new String();
        try {
            ramo = super.getResultSQLGetRamoByOficio(oficio);
        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }

        if (ramo.equals("") || ramo.isEmpty()) {
            ramo = "-1";
        }

        return ramo;
    }

}
