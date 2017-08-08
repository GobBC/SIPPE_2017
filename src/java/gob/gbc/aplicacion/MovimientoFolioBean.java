/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.AmpliacionReduccionAccionReq;
import gob.gbc.entidades.CodigoPPTO;
import gob.gbc.entidades.EstatusMov;
import gob.gbc.entidades.Estimacion;
import gob.gbc.entidades.FlujoFirmas;
import gob.gbc.entidades.MovOficiosAccionReq;
import gob.gbc.entidades.Movimiento;
import gob.gbc.entidades.MovimientoContabilidad;
import gob.gbc.entidades.MovimientoFolio;
import gob.gbc.entidades.MovimientosAmpliacionReduccion;
import gob.gbc.entidades.MovimientosRecalendarizacion;
import gob.gbc.entidades.MovimientosReprogramacion;
import gob.gbc.entidades.MovimientosTransferencia;
import gob.gbc.entidades.Parametro;
import gob.gbc.entidades.ProcesoMomento;
import gob.gbc.entidades.ProcesoMomentoCFG;
import gob.gbc.entidades.RecalendarizacionAccionReq;
import gob.gbc.entidades.ReprogramacionAccion;
import gob.gbc.entidades.ReprogramacionMeta;
import gob.gbc.entidades.Requerimiento;
import gob.gbc.entidades.TipoFlujo;
import gob.gbc.entidades.TipoMovimiento;
import gob.gbc.entidades.TipoOficio;
import gob.gbc.entidades.Transferencia;
import gob.gbc.entidades.TransferenciaAccionReq;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import gob.gbc.util.Fechas;
import gob.gbc.util.MensajeError;
import static gob.gbc.util.Utileria.getColorRoljo;
import static gob.gbc.util.Utileria.getDisabledMonthConta;
import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jarguelles
 */
public class MovimientoFolioBean extends ResultSQL {

    Bitacora bitacora;

    public MovimientoFolioBean() throws Exception {
        super();
        bitacora = new Bitacora();
    }

    public MovimientoFolioBean(String tipoDependencia) throws Exception {
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

    public List<MovimientoFolio> getMovimientosByFolioJustificacion(int folio, long id_justificacion) {
        List<MovimientoFolio> movimientoFolioList = new ArrayList<MovimientoFolio>();
        try {
            movimientoFolioList = super.getResultGetMovimientosByFolioJustificacion(folio, id_justificacion);
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
        return movimientoFolioList;
    }

    public boolean getActualizaAsignacionMovimientos(int tabla, String asignado, long justificacion, int folio, String ramo, String programa, int proyecto, String tipoProy, int meta, int accion, String depto, String partida, String fuente, String fondo, String recurso, int diferenciador) {

        boolean resultado = false;
        resultado = super.getResultSQLActualizaAsignacionMovimientos(tabla, asignado, justificacion, folio, ramo, programa, proyecto, tipoProy, meta, accion, depto, partida, fuente, fondo, recurso, diferenciador);
        return resultado;
    }

    public List<MovimientoFolio> getMovimientosByFolio(int folio) {
        List<MovimientoFolio> movimientoFolioList = new ArrayList<MovimientoFolio>();
        try {
            movimientoFolioList = super.getResultGetMovimientosByFolio(folio);
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
        return movimientoFolioList;
    }

    public boolean isValidaJustificacionesMovsActivo() {

        boolean resultado = false;
        try {
            resultado = super.getResultSQLIsValidaJustificacionesMovsActivo();
        } catch (Exception ex) {
            Logger.getLogger(MovimientoFolioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
}
