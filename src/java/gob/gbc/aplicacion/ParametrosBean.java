/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.Parametros;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jarguelles
 */
public class ParametrosBean extends ResultSQL {

    Bitacora bitacora;

    public ParametrosBean() throws Exception {
        super();

        bitacora = new Bitacora();
    }

    public ParametrosBean(String tipoDependencia) throws Exception {
        super(tipoDependencia);
        bitacora = new Bitacora();
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

    public Parametros getParametros() {
        Parametros objParametros = new Parametros();
        boolean cerrado = false;
        try {

            objParametros = super.getResultParametros();

        } catch (Exception ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return objParametros;
    }

    public boolean saveParametros(String repWebImpFirma, String validaTrimestre, String reporteCierre, String repValidaInfoCim, String validaTodosTrimestre) {
        boolean resultado = false;
        try {
            resultado = super.getResultSQLSaveParametros(repWebImpFirma, validaTrimestre, reporteCierre, repValidaInfoCim, validaTodosTrimestre);
        } catch (Exception ex) {
            super.transaccionRollback();
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }
}
