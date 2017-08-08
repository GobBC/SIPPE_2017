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

/**
 *
 * @author jarguelles
 */
public class MovimientosBean extends ResultSQL {

    Bitacora bitacora;

    public MovimientosBean() throws Exception {
        super();
        bitacora = new Bitacora();
    }

    public MovimientosBean(String tipoDependencia) throws Exception {
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

    public List<Movimiento> getMovimientoByTipoMovEstatusMovAppLogin(String tipoMovimiento, String estatusBase, int year, String appLogin) {
        List<Movimiento> lineaList = new ArrayList<Movimiento>();
        try {
            lineaList = super.getResultMovimientoByTipoMovEstatusMovAppLogin(tipoMovimiento, estatusBase, year, appLogin);
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
        return lineaList;
    }

    public List<Movimiento> getMovimientoByTipoMovART(String tipoMovimiento, String estatusBase, int year) {
        List<Movimiento> lineaList = new ArrayList<Movimiento>();
        try {
            lineaList = super.getResultMovimientoByTipoMovART(tipoMovimiento, estatusBase, year);
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
        return lineaList;
    }

    public List<Movimiento> getMovimientoByTipoMovEstatusMovAppLoginART(String tipoMovimiento, String estatusBase, int year, String appLogin) {
        List<Movimiento> lineaList = new ArrayList<Movimiento>();
        try {
            lineaList = super.getResultMovimientoByTipoMovEstatusMovAppLoginART(tipoMovimiento, estatusBase, year, appLogin);
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
        return lineaList;
    }

    public List<Movimiento> getMovimientoByTipoMov(String tipoMovimiento, String estatusBase, int year) {
        List<Movimiento> lineaList = new ArrayList<Movimiento>();
        try {
            lineaList = super.getResultMovimientoByTipoMov(tipoMovimiento, estatusBase, year);
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
        return lineaList;
    }

    public List<Movimiento> getMovsReporteByOficio(int oficio, int year, String appLogin, boolean isNormativo) {
        List<Movimiento> lineaList = new ArrayList<Movimiento>();
        try {
            lineaList = super.getResultSQLMovsReporteByOficio(oficio, year, appLogin, isNormativo);
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
        return lineaList;
    }

    public List<Movimiento> getMovimientoByTipoMovReporte(String tipoMovimiento, String estatusBase, int year) {
        List<Movimiento> lineaList = new ArrayList<Movimiento>();
        try {
            lineaList = super.getResultMovimientoByTipoMovReporte(tipoMovimiento, estatusBase, year);
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
        return lineaList;
    }
    
    public String getEstimacionHtml(List<Estimacion> estimacionList) {

        NumberFormat numberF = NumberFormat.getInstance(Locale.US);
        
        String nombreMes;
        String strResultado = new String();
        String sufijo = "recal";
        String clase = new String();
        strResultado += "<div id='divMese' class='col-md-12'>";

        for(Estimacion estimacion : estimacionList){
            switch(estimacion.getPeriodo()){
                case 1:
                        nombreMes = "Ene";
                        break;
                case 2:
                        nombreMes = "Feb";
                        break;
                case 3:
                        nombreMes = "Mar";
                        break;
                case 4:
                        nombreMes = "Abr";
                        break;
                case 5:
                        nombreMes = "May";
                        break;
                case 6:
                        nombreMes = "Jun";
                        break;
                case 7:
                        nombreMes = "Jul";
                        break;
                case 8:
                        nombreMes = "Ago";
                        break;
                case 9:
                        nombreMes = "Sep";
                        break;
                case 10:
                        nombreMes = "Oct";
                        break;
                case 11:
                        nombreMes = "Nov";
                        break;
                case 12:
                        nombreMes = "Dic";
                        break;
                default : 
                        nombreMes = "Ene";
            }
            strResultado += "<div class='col-md-2'> "+nombreMes+" <input type='text' id='" + sufijo + "inpTxt"+nombreMes+"' class='" + clase + "'  value='" + numberF.format(estimacion.getValor()) + "' disabled /> </div>";
        }
        strResultado += "</div>";

        return strResultado;
    }
    
    public List<Movimiento> getFoliosByYearRamoCaratulaTipoMovStatusMov(int year, String ramoId, long caratula, String tipoMov, String statusMov, String appLogin, boolean isNormativo) {
        List<Movimiento> foliosList = new ArrayList<Movimiento>();
        try {
            foliosList = super.getResultGetFoliosByYearRamoCaratulaTipoMovStatusMov(year, ramoId, caratula, tipoMov, statusMov, appLogin, isNormativo);
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
        return foliosList;
    }
}
