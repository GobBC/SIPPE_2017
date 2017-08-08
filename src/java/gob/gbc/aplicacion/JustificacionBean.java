/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.Justificacion;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import gob.gbc.util.MensajeError;
import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author jarguelles
 */
public class JustificacionBean extends ResultSQL {

    Bitacora bitacora;

    public JustificacionBean() throws Exception {
        super();
        bitacora = new Bitacora();
    }

    public JustificacionBean(String tipoDependencia) throws Exception {
        super(tipoDependencia);
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

    public List<Justificacion> getJustificacionesByFolio(int folio) {
        List<Justificacion> justificacionList = new ArrayList<Justificacion>();
        try {
            justificacionList = super.getResultGetJustificacionesByFolio(folio);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        } catch (IOException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return justificacionList;
    }

    public Justificacion getJustificacionByFolioJustificacion(int folio, long idJustificacion) {
        Justificacion justificacion = null;
        try {
            justificacion = super.getResultGetJustificacionByFolioJustificacion(folio, idJustificacion);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        } catch (IOException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return justificacion;
    }

    public boolean getSaveJustificacion(int folio, long idJustificacion, String justificacion) {

        boolean resultado = false;
        resultado = super.getResultSQLSaveJustificacion(folio, idJustificacion, justificacion);
        return resultado;
    }
}
