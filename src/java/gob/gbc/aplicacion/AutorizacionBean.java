/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.aplicacion;

import gob.gbc.entidades.Accion;
import gob.gbc.entidades.AmpliacionReduccionAccion;
import gob.gbc.entidades.AmpliacionReduccionAccionReq;
import gob.gbc.entidades.AmpliacionReduccionMeta;
import gob.gbc.entidades.CodigoPPTO;
import gob.gbc.entidades.EstatusMov;
import gob.gbc.entidades.FlujoFirmas;
import gob.gbc.entidades.Meta;
import gob.gbc.entidades.MovOficioAccionEstimacion;
import gob.gbc.entidades.MovOficioEstimacion;
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
import gob.gbc.entidades.TipoFlujo;
import gob.gbc.entidades.TipoMovimiento;
import gob.gbc.entidades.TipoOficio;
import gob.gbc.entidades.Transferencia;
import gob.gbc.entidades.TransferenciaAccion;
import gob.gbc.entidades.TransferenciaAccionReq;
import gob.gbc.entidades.TransferenciaMeta;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Bitacora;
import gob.gbc.util.Fechas;
import gob.gbc.util.MensajeError;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ugarcia
 */
public class AutorizacionBean extends ResultSQL {

    Bitacora bitacora;

    public AutorizacionBean() throws Exception {
        super();

        bitacora = new Bitacora();
    }

    public AutorizacionBean(String tipoDependencia) throws Exception {
        super(tipoDependencia);
        bitacora = new Bitacora();
    }

    public List<TipoMovimiento> getTipoMovimiento() {
        List<TipoMovimiento> lineaList = new ArrayList<TipoMovimiento>();
        try {
            lineaList = super.getResultTipoMovimiento();
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return lineaList;
    }

    public List<TipoFlujo> getTipoFlujoByUsuario(String usuario, String tipoDependencia) {
        List<TipoFlujo> lineaList = new ArrayList<TipoFlujo>();
        try {
            lineaList = super.getResultTipoFlujoByUsuario(usuario, tipoDependencia);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return lineaList;
    }

    public List<Movimiento> getMovimientoByTipoMovUsr(String tipoMovimiento, String estatusBase, String appLogin,
            String tipoUsuario, String tipoOficio, int year, int tipoFlujo, String ramoInList) {
        List<Movimiento> lineaList = new ArrayList<Movimiento>();
        List<Movimiento> movimientoList = new ArrayList<Movimiento>();
        List<FlujoFirmas> flujoFirmasList = new ArrayList<FlujoFirmas>();
        boolean bolFirmasCompletas = false;
        try {

            movimientoList = super.getResultMovimientoByTipoMovUsr(tipoMovimiento, estatusBase, appLogin, tipoOficio, year, tipoFlujo, ramoInList);
            flujoFirmasList = getResultFirmantesAnteriores(tipoMovimiento, estatusBase, appLogin, tipoUsuario);

            if (flujoFirmasList.size() > 0) {

                for (Movimiento movimiento : movimientoList) {
                    bolFirmasCompletas = false;

                    for (FlujoFirmas flujo : flujoFirmasList) {
                        if (super.getResultValidaFirma(movimiento.getOficio(), flujo.getTipoFlujo(), flujo.getAppLogin())) {
                            bolFirmasCompletas = true;
                        }
                    }

                    if (bolFirmasCompletas) {
                        lineaList.add(movimiento);
                    }
                }
            } else {
                lineaList = movimientoList;
            }

        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        } catch (IOException io) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(io, new Throwable());
            bitacora.grabaBitacora();
        }
        return lineaList;
    }

    public EstatusMov getEstatusSiguiente(String tipoMov, String tipoUsr, String estatusActual) {
        EstatusMov estatusMov = new EstatusMov();
        try {
            estatusMov = super.getResultEstatusSiguiente(tipoMov, tipoUsr, estatusActual);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return estatusMov;
    }

    public boolean getUpdateEstatusMov(String estatus, int oficio) {
        boolean resultado = false;
        try {
            resultado = super.getResultSQLUpdateEstatusMov(estatus, oficio);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public boolean getCancelaMov(int oficio, String tipoUsr, String appLogin, String motivo, int tipoFlujo, MensajeError objMensaje) {
        boolean resultado = false;
        Movimiento objMovimiento;
        Calendar calFechaMovtoPpto = new GregorianCalendar();
        Calendar calFechaActual = new GregorianCalendar();
        String strFechaActual = "";
        Fechas objFechas = new Fechas();

        try {

            objMovimiento = super.getResultMovimientoByFolio(oficio);

            calFechaActual.setTime(super.getResultSQLgetServerDate());

            if (objMovimiento.getFecPPTO() != null) {
                calFechaMovtoPpto.setTime(objMovimiento.getFecPPTO());
            } else {
                calFechaMovtoPpto.setTime(objMovimiento.getFechaElab());
            }

            if (super.getResultSQLUpdateEstatusMotivoMov("C", motivo, oficio)) {

                if (calFechaActual.get(Calendar.YEAR) > calFechaMovtoPpto.get(Calendar.YEAR)) {
                    calFechaActual.set(calFechaMovtoPpto.get(Calendar.YEAR), 11, 31);//11 es Diciembre
                    strFechaActual = objFechas.getFechaFormato(calFechaActual, Fechas.FORMATO_CORTO);
                    if (super.getResultInsertBitMovto(oficio, appLogin, "C", "N", "A", "", tipoFlujo, tipoUsr, strFechaActual)) {
                        resultado = true;
                    } else {
                        objMensaje.setMensaje("Error al insertar la cancelaci\u00e1n en la bit\u00e1cora del movimiento");
                    }
                } else {
                    if (super.getResultInsertBitMovto(oficio, appLogin, "C", "N", "A", "", tipoFlujo, tipoUsr)) {
                        resultado = true;
                    } else {
                        objMensaje.setMensaje("Error al insertar la cancelaci\u00e1n en la bit\u00e1cora del movimiento");
                    }
                }
                
                if (resultado) {
                    if (super.getResultSQLValidaOficioSINVP(oficio)) {
                        if (super.getRestulSQLCallProcedureINVP(oficio,"C")) {
                            resultado = true;
                        } else {
                            objMensaje.setMensaje("Error al ejecutar el procedimiento de INVP");
                        }
                    } else {
                        resultado = true;
                    }
                }
                
            } else {
                objMensaje.setMensaje("Error al actualizar el estatus del movimiento a cancelado");
            }
        } catch (SQLException sql) {
            objMensaje.setMensaje("Error inesperado en la cancelaci\u00e1n del movimiento");
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        } catch (Exception ex) {
            objMensaje.setMensaje("Error inesperado en la cancelaci\u00e1n del movimiento");
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public boolean getRechazaMov(int oficio, String tipoUsr, String appLogin, String motivo, int tipoFlujo, MensajeError objMensaje) {
        boolean resultado = false;
        Movimiento objMovimiento;
        Calendar calFechaMovtoPpto = new GregorianCalendar();
        Calendar calFechaActual = new GregorianCalendar();
        String strFechaActual = "";
        Fechas objFechas = new Fechas();

        try {

            objMovimiento = super.getResultMovimientoByFolio(oficio);

            calFechaActual.setTime(super.getResultSQLgetServerDate());
            calFechaMovtoPpto.setTime(objMovimiento.getFecPPTO());

            if (super.getResultSQLUpdateEstatusMotivoMov("R", motivo, oficio)) {

                if (calFechaActual.get(Calendar.YEAR) > calFechaMovtoPpto.get(Calendar.YEAR)) {
                    calFechaActual.set(calFechaMovtoPpto.get(Calendar.YEAR), 11, 31);//11 es Diciembre
                    strFechaActual = objFechas.getFechaFormato(calFechaActual, Fechas.FORMATO_CORTO);
                    if (super.getResultInsertBitMovto(oficio, appLogin, "R", "N", "A", "", tipoFlujo, tipoUsr, strFechaActual)) {
                        resultado = true;
                    } else {
                        objMensaje.setMensaje("Error al insertar el rechazo en la bit\u00e1cora del movimiento");
                    }
                } else {
                    if (super.getResultInsertBitMovto(oficio, appLogin, "R", "N", "A", "", tipoFlujo, tipoUsr)) {
                        resultado = true;
                    } else {
                        objMensaje.setMensaje("Error al insertar el rechazo en la bit\u00e1cora del movimiento");
                    }
                }
            } else {
                objMensaje.setMensaje("Error al actualizar el estatus del movimiento a rechazado");
            }

        } catch (SQLException sql) {
            objMensaje.setMensaje("Error inesperado en el rechazo del movimiento");
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        } catch (Exception ex) {
            objMensaje.setMensaje("Error inesperado en el rechazo del movimiento");
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public boolean getAutorizaMovRecalendarizacion(int oficio, String tipoUsr, String appLogin, int tipoFlujo,
            MovimientosRecalendarizacion movRecalendarizacion, MensajeError objMensaje) {
        Movimiento objMovimiento;
        EstatusMov objEstatusMov;
        List<FlujoFirmas> flujoFirmasList = new ArrayList<FlujoFirmas>();
        boolean bolFirmasCompletas = false;
        boolean resultado = false;
        boolean bolGrabo = false;
        boolean bolGraboPPTO = true;
        Calendar calFechaMovtoPpto = new GregorianCalendar();
        Calendar calFechaActual = new GregorianCalendar();
        String strFechaPpto = "";
        String strFechaActual = "";
        Fechas objFechas = new Fechas();
        boolean isActual = true;

        try {

            objMovimiento = super.getResultMovimientoByFolio(oficio);

            calFechaActual.setTime(super.getResultSQLgetServerDate());
            calFechaMovtoPpto.setTime(objMovimiento.getFecPPTO());
            strFechaPpto = objFechas.getFechaFormato(calFechaMovtoPpto, Fechas.FORMATO_CORTO);

            if (calFechaActual.get(Calendar.YEAR) > calFechaMovtoPpto.get(Calendar.YEAR)) {
                isActual = false;
                calFechaActual.set(calFechaMovtoPpto.get(Calendar.YEAR), 11, 31);//11 es Diciembre
                strFechaActual = objFechas.getFechaFormato(calFechaActual, Fechas.FORMATO_CORTO);
                if (super.getResultInsertBitMovto(oficio, appLogin, "A", "N", "A", "", tipoFlujo, tipoUsr, strFechaActual)) {
                    bolGrabo = true;
                } else {
                    objMensaje.setMensaje("Autorizaci\u00f3n de Recalendarizaci\u00f3n. Error al insertar la autorizaci\u00f3n (captura especial) en la bit\u00e1cora del movimiento.");
                }

            } else {
                if (super.getResultInsertBitMovto(oficio, appLogin, "A", "N", "A", "", tipoFlujo, tipoUsr)) {
                    bolGrabo = true;
                } else {
                    objMensaje.setMensaje("Autorizaci\u00f3n de Recalendarizaci\u00f3n. Error al insertar la autorizaci\u00f3n en la bit\u00e1cora del movimiento.");
                }
            }

            if (bolGrabo) {

                //Se cambia el estatus del movimiento cuando cumple con las firmas del flujo
                flujoFirmasList = super.getResultUsuariosAFirmar(tipoFlujo, tipoUsr);

                if (flujoFirmasList.size() > 0) {

                    for (FlujoFirmas flujoFirmas : flujoFirmasList) {

                        if (super.getResultValidaFirma(oficio, tipoFlujo, flujoFirmas.getAppLogin())) {
                            bolFirmasCompletas = true;
                        }
                    }

                    if (bolFirmasCompletas) {
                        bitacora.setStrUbicacion(getStrUbicacion());
                        bitacora.setStrServer(getStrServer());
                        bitacora.setITipoBitacora(3);
                        bitacora.setStrInformacion(objMovimiento.getOficio() + " Parametros antes de método\n tipoMov : " + objMovimiento.getTipoMovimiento() + "\n tipoUsr: " + tipoUsr + "\n estatus: " + objMovimiento.getStatus());
                        bitacora.grabaBitacora();
                        objEstatusMov = super.getResultEstatusSiguiente(objMovimiento.getTipoMovimiento(), tipoUsr, objMovimiento.getStatus());
                        if (super.getResultSQLUpdateEstatusMov(objEstatusMov.getEstatusMovId(), oficio)) {

                            if (objEstatusMov.getEstatusMovId().equals("A")) {

                                if (movRecalendarizacion.getMovEstimacionList().size() > 0) {
                                    bolGrabo = super.getResultInsertHistCalendarizacionMeta(oficio);
                                    if (bolGrabo) {
                                        if (super.getResultUpdateCalendarizacionMeta(oficio)) {
                                            bolGrabo = true;
                                        } else {
                                            objMensaje.setMensaje("Autorizaci\u00f3n de Recalendarizaci\u00f3n. Error al actualizar la calendarizac\u00f3n de la meta.");
                                        }
                                    } else {
                                        objMensaje.setMensaje("Autorizaci\u00f3n de Recalendarizaci\u00f3n. Error al insertar el hist\u00f3rico de la calendarizac\u00f3n de la meta.");
                                    }
                                }

                                if (bolGrabo && movRecalendarizacion.getMovAccionEstimacionList().size() > 0) {
                                    if (bolGrabo) {
                                        bolGrabo = super.getResultInsertHistCalendarizacionAccion(oficio);
                                        if (bolGrabo) {
                                            if (super.getResultUpdateCalendarizacionAccion(oficio)) {
                                                bolGrabo = true;
                                            } else {
                                                objMensaje.setMensaje("Autorizaci\u00f3n de Recalendarizaci\u00f3n. Error al actualizar la calendarizac\u00f3n de la acci\u00f3n.");
                                            }
                                        } else {
                                            objMensaje.setMensaje("Autorizaci\u00f3n de Recalendarizaci\u00f3n. Error al insertar el hist\u00f3rico de la calendarizac\u00f3n de la acci\u00f3n.");
                                        }
                                    }
                                }

                                if (bolGrabo && movRecalendarizacion.getMovOficiosAccionReq().size() > 0) {

                                    if (bolGrabo) {

                                        for (RecalendarizacionAccionReq recalendarizacionAccionReq : movRecalendarizacion.getMovOficiosAccionReq()) {

                                            if (!getUpdatePPTORecalendarizacion(recalendarizacionAccionReq.getMovAccionReq())) {
                                                bolGraboPPTO = false;
                                                objMensaje.setMensaje("Autorizaci\u00f3n de Recalendarizaci\u00f3n. Error al actualizar el presupuesto.");
                                                break;
                                            }
                                        }

                                        if (bolGraboPPTO) {
                                            bolGrabo = super.getResultInsertHistCalendarizacionRequerimiento(oficio);
                                            if (bolGrabo) {
                                                if (super.getResultUpdateCalendarizacionRequerimiento(oficio)) {
                                                    bolGrabo = true;
                                                } else {
                                                    objMensaje.setMensaje("Autorizaci\u00f3n de Recalendarizaci\u00f3n. Error al actualizar la calendarizac\u00f3n del requerimiento.");
                                                }
                                            } else {
                                                objMensaje.setMensaje("Autorizaci\u00f3n de Recalendarizaci\u00f3n. Error al insertar el hist\u00f3rico de la calendarizac\u00f3n del requerimiento.");
                                            }
                                        } else {
                                            bolGrabo = false;
                                        }
                                    }

                                }

                                if (bolGrabo) {
                                    resultado = true;
                                }
                            } else {
                                resultado = true;
                            }
                        } else {
                            objMensaje.setMensaje("Autorizaci\u00f3n de Recalendarizaci\u00f3n. Error al actualizar el estatus del movimiento.");
                        }
                    } else {
                        resultado = true;
                    }
                }

            }

        } catch (SQLException sql) {
            objMensaje.setMensaje("Error inesperado de SQL en la autorizaci\u00f3n de la recalendarizaci\u00f3n");
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        } catch (Exception ex) {
            objMensaje.setMensaje("Error inesperado la autorizaci\u00f3n de la recalendarizaci\u00f3n");
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        //return false;
        return resultado;
    }

    public boolean getInsertBitMovto(int oficio, String appLogin, String fechaAut, String autorizo,
            String impFirma, String tipoOficio, String terminal, int tipoFlujo, String tipoUsr) {
        boolean resultado = false;
        try {
            resultado = super.getResultInsertBitMovto(oficio, appLogin, autorizo, impFirma, tipoOficio, terminal, tipoFlujo, tipoUsr);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public boolean getDeleteBitMovtoByOficio(int oficio) {
        boolean resultado = false;
        try {
            resultado = super.getResultDeleteBitMovtoByOficio(oficio);
        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public boolean getUpdatePPTORecalendarizacion(MovOficiosAccionReq movAccionReq) throws SQLException {
        boolean bolGrabo = true;
        CodigoPPTO codPpto;
        int contMes = 1;
        double cantXmes = 0;
        String mensaje = "";

        codPpto = super.getRestulSQLGetCodigoPPTOByReq(movAccionReq.getYear(), movAccionReq.getRamo(),
                movAccionReq.getPrograma(), movAccionReq.getRequerimiento(), movAccionReq.getPartida(),
                movAccionReq.getMeta(), movAccionReq.getAccion());

        if (codPpto != null) {

            while (bolGrabo && contMes <= 12) {

                cantXmes = 0;

                switch (contMes) {
                    case 1:
                        cantXmes = movAccionReq.getEne();
                        break;
                    case 2:
                        cantXmes = movAccionReq.getFeb();
                        break;
                    case 3:
                        cantXmes = movAccionReq.getMar();
                        break;
                    case 4:
                        cantXmes = movAccionReq.getAbr();
                        break;
                    case 5:
                        cantXmes = movAccionReq.getMay();
                        break;
                    case 6:
                        cantXmes = movAccionReq.getJun();
                        break;
                    case 7:
                        cantXmes = movAccionReq.getJul();
                        break;
                    case 8:
                        cantXmes = movAccionReq.getAgo();
                        break;
                    case 9:
                        cantXmes = movAccionReq.getSep();
                        break;
                    case 10:
                        cantXmes = movAccionReq.getOct();
                        break;
                    case 11:
                        cantXmes = movAccionReq.getNov();
                        break;
                    case 12:
                        cantXmes = movAccionReq.getDic();
                        break;

                }

                mensaje = super.getRestulSQLCallRecalendarizaCodPPTO(movAccionReq.getOficio(), movAccionReq.getYear(), movAccionReq.getRamo(),
                        movAccionReq.getDepto(), codPpto.getFinalidad(), codPpto.getFuncion(), codPpto.getSubfuncion(),
                        codPpto.getProgCONAC(), movAccionReq.getPrograma(), codPpto.getTipoProy(), codPpto.getProyecto(),
                        movAccionReq.getMeta(), movAccionReq.getAccion(), movAccionReq.getPartida(), movAccionReq.getTipoGasto(),
                        movAccionReq.getFuente(), movAccionReq.getFondo(), movAccionReq.getRecurso(), codPpto.getMunicipio(),
                        codPpto.getDelegacion(), movAccionReq.getRelLaboral(), movAccionReq.getRequerimiento(), cantXmes, contMes,
                        movAccionReq.getCostoUnitario());

                if (!mensaje.equals("exito")) {
                    bolGrabo = false;
                }

                contMes++;
            }
        } else {
            bolGrabo = false;
        }

        return bolGrabo;
    }

    public boolean getAutorizaMovReprogramacion(int year, int oficio, String tipoUsr, String appLogin, int tipoFlujo,
            MovimientosReprogramacion movReprogramacion, MensajeError objMensaje) {
        Movimiento objMovimiento;
        EstatusMov objEstatusMov;
        Meta metaTemp;
        Accion accionTemp;

        List<FlujoFirmas> flujoFirmasList = new ArrayList<FlujoFirmas>();
        List<Meta> metaList = new ArrayList<Meta>();
        List<Accion> accionList = new ArrayList<Accion>();

        boolean bolFirmasCompletas = false;
        boolean resultado = false;
        boolean bolGrabo = false;
        Calendar calFechaMovtoPpto = new GregorianCalendar();
        Calendar calFechaActual = new GregorianCalendar();
        String strFechaPpto = "";
        String strFechaActual = "";
        Fechas objFechas = new Fechas();
        boolean isActual = true;
        int metaId = 0;
        int accionId = 0;
        double estimacion = 0.0;

        try {

            objMovimiento = super.getResultMovimientoByFolio(oficio);

            calFechaActual.setTime(super.getResultSQLgetServerDate());
            calFechaMovtoPpto.setTime(objMovimiento.getFecPPTO());
            strFechaPpto = objFechas.getFechaFormato(calFechaMovtoPpto, Fechas.FORMATO_CORTO);

            for (ReprogramacionMeta meta : movReprogramacion.getMovOficioMetaList()) {
                metaTemp = new Meta();
                estimacion = 0.0;
                for (MovOficioEstimacion metaEstimacion : meta.getMovEstimacionList()) {
                    estimacion += metaEstimacion.getValor();
                }
                if (estimacion == 0.0) {
                    if (meta.getMetaInfo().getNvaCreacion().equals("N")) {
                        if (this.validaMetaInhabilitada(year,
                                meta.getMetaInfo().getRamoId(),
                                meta.getMetaInfo().getMetaId())) {
                            metaTemp.setYear(year);
                            metaTemp.setRamo(meta.getMetaInfo().getRamoId());
                            metaTemp.setMetaId(meta.getMetaInfo().getMetaId());
                            metaList.add(metaTemp);
                        }
                    }
                }
            }
            for (ReprogramacionAccion accion : movReprogramacion.getMovOficioAccionList()) {
                accionTemp = new Accion();
                estimacion = 0.0;
                for (MovOficioAccionEstimacion accionEstimacion : accion.getMovAcionEstimacionList()) {
                    estimacion += accionEstimacion.getValor();
                }
                if (estimacion == 0.0) {
                    if (accion.getMovOficioAccion().getNvaCreacion().equals("N")) {
                        if (this.validaAccionInhabilitada(year,
                                accion.getMovOficioAccion().getRamoId(),
                                accion.getMovOficioAccion().getMetaId(),
                                accion.getMovOficioAccion().getAccionId())) {
                            accionTemp.setYear(year);
                            accionTemp.setRamo(accion.getMovOficioAccion().getRamoId());
                            accionTemp.setMeta(accion.getMovOficioAccion().getMetaId());
                            accionTemp.setAccionId(accion.getMovOficioAccion().getAccionId());
                            accionList.add(accionTemp);
                        }
                    }
                }
            }

            if (calFechaActual.get(Calendar.YEAR) > calFechaMovtoPpto.get(Calendar.YEAR)) {
                isActual = false;
                calFechaActual.set(calFechaMovtoPpto.get(Calendar.YEAR), 11, 31);//11 es Diciembre 
                strFechaActual = objFechas.getFechaFormato(calFechaActual, Fechas.FORMATO_CORTO);
                if (super.getResultInsertBitMovto(oficio, appLogin, "A", "N", "A", "", tipoFlujo, tipoUsr, strFechaActual)) {
                    bolGrabo = true;
                } else {
                    objMensaje.setMensaje("Autorizaci\u00f3n de Reprogramaci\u00f3n. Error al insertar la autorizaci\u00f3n (captura especial) en la bit\u00e1cora del movimiento.");
                }

            } else {
                if (super.getResultInsertBitMovto(oficio, appLogin, "A", "N", "A", "", tipoFlujo, tipoUsr)) {
                    bolGrabo = true;
                } else {
                    objMensaje.setMensaje("Autorizaci\u00f3n de Reprogramaci\u00f3n. Error al insertar la autorizaci\u00f3n en la bit\u00e1cora del movimiento.");
                }
            }

            if (bolGrabo) {

                //Se cambia el estatus del movimiento cuando cumple con las firmas del flujo
                flujoFirmasList = super.getResultUsuariosAFirmar(tipoFlujo, tipoUsr);

                if (flujoFirmasList.size() > 0) {

                    for (FlujoFirmas flujoFirmas : flujoFirmasList) {

                        if (super.getResultValidaFirma(oficio, tipoFlujo, flujoFirmas.getAppLogin())) {
                            bolFirmasCompletas = true;
                        }
                    }

                    if (bolFirmasCompletas) {
                        bitacora.setStrUbicacion(getStrUbicacion());
                        bitacora.setStrServer(getStrServer());
                        bitacora.setITipoBitacora(3);
                        bitacora.setStrInformacion(objMovimiento.getOficio() + " Parametros antes de método\n tipoMov : " + objMovimiento.getTipoMovimiento() + "\n tipoUsr: " + tipoUsr + "\n estatus: " + objMovimiento.getStatus());
                        bitacora.grabaBitacora();
                        objEstatusMov = super.getResultEstatusSiguiente(objMovimiento.getTipoMovimiento(), tipoUsr, objMovimiento.getStatus());
                        if (super.getResultSQLUpdateEstatusMov(objEstatusMov.getEstatusMovId(), oficio)) {

                            if (objEstatusMov.getEstatusMovId().equals("A")) {

                                Iterator iterator = (Iterator) movReprogramacion.getMovOficioMetaList().iterator();
                                while (bolGrabo && iterator.hasNext()) {
                                    ReprogramacionMeta reprogMeta = (ReprogramacionMeta) iterator.next();

                                    if (reprogMeta.getMetaInfo().getNvaCreacion().equals("S") && reprogMeta.getMetaInfo().getMetaId() < 0) {

                                        //Inserta en meta las metas nuevas 
                                        if (bolGrabo && super.getResultSQLGetValidaMetasNuevasFromMovoficios(oficio, year, reprogMeta.getMetaInfo().getRamoId(), reprogMeta.getMetaInfo().getMetaId())) {
                                            metaId = super.getMaxMeta(year, reprogMeta.getMetaInfo().getRamoId());
                                            bolGrabo = super.getResultSQLInsertMetaFromMovoficios(oficio, year, reprogMeta.getMetaInfo().getRamoId(), reprogMeta.getMetaInfo().getMetaId(), metaId, "A");
                                            if (bolGrabo) {
                                                bolGrabo = super.getResultSQLInsertEstimacionFromMovoficios(oficio, year, reprogMeta.getMetaInfo().getRamoId(), reprogMeta.getMetaInfo().getMetaId(), metaId);
                                                if (bolGrabo) {
                                                    bolGrabo = super.getResultSQLUpdateMovoficiosMeta(metaId, oficio, year, reprogMeta.getMetaInfo().getRamoId(), reprogMeta.getMetaInfo().getMetaId());
                                                    if (bolGrabo) {
                                                        if(super.getResultSQLCountMovoficiosAccion(oficio, reprogMeta.getMetaInfo().getRamoId(), reprogMeta.getMetaInfo().getMetaId())){
                                                            bolGrabo = super.getResultSQLUpdateMovoficiosMetaAccion(metaId, oficio, year, reprogMeta.getMetaInfo().getRamoId(), reprogMeta.getMetaInfo().getMetaId());
                                                            if (bolGrabo) {
                                                                bolGrabo = super.getResultSQLUpdateMovoficiosMetaAccionEstimacion(metaId, oficio, year, reprogMeta.getMetaInfo().getRamoId(), reprogMeta.getMetaInfo().getMetaId());
                                                                if (bolGrabo) {
                                                                    for (ReprogramacionAccion reproAccionAux : movReprogramacion.getMovOficioAccionList()) {
                                                                        if (reproAccionAux.getMovOficioAccion().getRamoId().equals(reprogMeta.getMetaInfo().getRamoId())
                                                                                && reproAccionAux.getMovOficioAccion().getMetaId() == reprogMeta.getMetaInfo().getMetaId()) {
                                                                            reproAccionAux.setMetaAux(metaId);
                                                                        }
                                                                    }
                                                                } else {
                                                                    objMensaje.setMensaje("Error al actualizar la meta en la estimaci\u00f3n del movimiento acci\u00f3n. Meta " + reprogMeta.getMetaInfo().getMetaId() + ", ramo " + reprogMeta.getMetaInfo().getRamoId() + ".");
                                                                }
                                                            } else {
                                                                objMensaje.setMensaje("Error al actualizar la meta en el movimiento acci\u00f3n. Meta " + reprogMeta.getMetaInfo().getMetaId() + ", ramo " + reprogMeta.getMetaInfo().getRamoId() + ".");
                                                            }
                                                        }
                                                    } else {
                                                        objMensaje.setMensaje("Error al actualizar la meta en el movimiento. Meta " + reprogMeta.getMetaInfo().getMetaId() + ", ramo " + reprogMeta.getMetaInfo().getRamoId() + ".");
                                                    }
                                                } else {
                                                    objMensaje.setMensaje("Error al insertar la estimaci\u00f3n de la meta " + reprogMeta.getMetaInfo().getMetaId() + ", ramo " + reprogMeta.getMetaInfo().getRamoId() + ".");
                                                }
                                            } else {
                                                objMensaje.setMensaje("Error al insertar la meta " + reprogMeta.getMetaInfo().getMetaId() + ", ramo " + reprogMeta.getMetaInfo().getRamoId() + ".");
                                            }
                                        }
                                    }
                                }

                                iterator = (Iterator) movReprogramacion.getMovOficioAccionList().iterator();
                                while (bolGrabo && iterator.hasNext()) {
                                    ReprogramacionAccion reprogAccion = (ReprogramacionAccion) iterator.next();

                                    if (reprogAccion.getMovOficioAccion().getNvaCreacion().equals("S") && reprogAccion.getMovOficioAccion().getAccionId() < 0) {

                                        if (reprogAccion.getMetaAux() > 0) {
                                            metaId = reprogAccion.getMetaAux();
                                        } else {
                                            metaId = reprogAccion.getMovOficioAccion().getMetaId();
                                        }

                                        //Inserta en accion las acciones nuevas 
                                        if (bolGrabo && super.getResultSQLGetValidaAccionesNuevasFromMovoficios(oficio, year, reprogAccion.getMovOficioAccion().getRamoId(), metaId, reprogAccion.getMovOficioAccion().getAccionId())) {

                                            accionId = super.getResultSQLMaxAccion(year, reprogAccion.getMovOficioAccion().getRamoId(), metaId);
                                            bolGrabo = super.getResultSQLInsertAccionFromMovoficios(oficio, year, reprogAccion.getMovOficioAccion().getRamoId(), metaId, reprogAccion.getMovOficioAccion().getAccionId(), accionId, "A");
                                            if (bolGrabo) {
                                                bolGrabo = super.getResultSQLInsertAccionEstimacionFromMovoficios(oficio, year, reprogAccion.getMovOficioAccion().getRamoId(), metaId, reprogAccion.getMovOficioAccion().getAccionId(), accionId);
                                                if (bolGrabo) {
                                                    bolGrabo = super.getResultSQLUpdateMovoficiosAccion(accionId, oficio, year, reprogAccion.getMovOficioAccion().getRamoId(), metaId, reprogAccion.getMovOficioAccion().getAccionId());
                                                    if (!bolGrabo) {
                                                        objMensaje.setMensaje("Error al actualizar la acci\u00f3n en el movimiento. Acci\u00f3n " + reprogAccion.getMovOficioAccion().getAccionId() + ", meta " + reprogAccion.getMovOficioAccion().getMetaId() + ", ramo " + reprogAccion.getMovOficioAccion().getRamoId() + ".");
                                                    }
                                                } else {
                                                    objMensaje.setMensaje("Error al insertar la estimaci\u00f3n de la acci\u00f3n " + reprogAccion.getMovOficioAccion().getAccionId() + ", meta " + reprogAccion.getMovOficioAccion().getMetaId() + ", ramo " + reprogAccion.getMovOficioAccion().getRamoId() + ".");
                                                }
                                            } else {
                                                objMensaje.setMensaje("Error al insertar la acci\u00f3n " + reprogAccion.getMovOficioAccion().getAccionId() + ", meta " + reprogAccion.getMovOficioAccion().getMetaId() + ", ramo " + reprogAccion.getMovOficioAccion().getRamoId() + ".");
                                            }
                                        } else {

                                        }
                                    }
                                }

                                if (bolGrabo && super.getResultSQLCountMovoficiosMetasEditadas(oficio) > 0) {
                                    bolGrabo = super.getResultInsertHistMeta(oficio);
                                    if (bolGrabo) {
                                        bolGrabo = super.getResultUpdateReprogramacionMeta(oficio);
                                        if (bolGrabo) {
                                            bolGrabo = super.getResultInsertHistCalendarizacionMeta(oficio);
                                            if (bolGrabo) {
                                                if (super.getResultUpdateCalendarizacionMeta(oficio)) {
                                                    bolGrabo = true;
                                                } else {
                                                    objMensaje.setMensaje("Autorizaci\u00f3n de Reprogramaci\u00f3n. Error al actualizar la calendarizac\u00f3n de la meta.");
                                                }
                                            } else {
                                                objMensaje.setMensaje("Autorizaci\u00f3n de Reprogramaci\u00f3n. Error al insertar el hist\u00f3rico de la calendarizac\u00f3n de la meta.");
                                            }
                                        } else {
                                            objMensaje.setMensaje("Autorizaci\u00f3n de Reprogramaci\u00f3n. Error al actualizar la meta.");
                                        }
                                    } else {
                                        objMensaje.setMensaje("Autorizaci\u00f3n de Reprogramaci\u00f3n. Error al insertar el hist\u00f3rico de la meta.");
                                    }
                                }

                                if (bolGrabo && super.getResultSQLCountMovoficiosAccionEditadas(oficio) > 0) {
                                    bolGrabo = super.getResultInsertHistAccion(oficio);
                                    if (bolGrabo) {
                                        bolGrabo = super.getResultUpdateReprogramacionAccion(oficio);
                                        if (bolGrabo) {
                                            bolGrabo = super.getResultInsertHistCalendarizacionAccion(oficio);
                                            if (bolGrabo) {
                                                if (super.getResultUpdateCalendarizacionAccion(oficio)) {
                                                    bolGrabo = true;
                                                } else {
                                                    objMensaje.setMensaje("Autorizaci\u00f3n de Reprogramaci\u00f3n. Error al actualizar la calendarizac\u00f3n de la acci\u00f3n.");
                                                }
                                            } else {
                                                objMensaje.setMensaje("Autorizaci\u00f3n de Reprogramaci\u00f3n. Error al insertar el hist\u00f3rico de la calendarizac\u00f3n de la acci\u00f3n.");
                                            }
                                        } else {
                                            objMensaje.setMensaje("Autorizaci\u00f3n de Reprogramaci\u00f3n. Error al actualizar la acci\u00f3n.");
                                        }
                                    } else {
                                        objMensaje.setMensaje("Autorizaci\u00f3n de Reprogramaci\u00f3n. Error al insertar el hist\u00f3rico de la acci\u00f3n.");
                                    }
                                }
                                if (bolGrabo) {
                                    for (Meta meta : metaList) {
                                        if (!super.getResultSQLUpdatePeriodoMeta(meta.getYear(), meta.getRamo(), meta.getMetaId())) {
                                            bolGrabo = false;
                                            objMensaje.setMensaje("Error al inhabilitar periodo en meta");
                                            break;
                                        } else {
                                            if (!super.getResultSQLUpdatePeriodoAccionByMeta(meta.getYear(), meta.getRamo(), meta.getMetaId())) {
                                                bolGrabo = false;
                                                objMensaje.setMensaje("Error al inhabilitar periodo en acciones de meta inhabiltada");
                                                break;
                                            }
                                        }
                                    }
                                    if (bolGrabo) {
                                        for (Accion accion : accionList) {
                                            if (!super.getResultSQLUpdatePeriodoAccion(accion.getYear(), accion.getRamo(),
                                                    accion.getMeta(), accion.getAccionId())) {
                                                bolGrabo = false;
                                                objMensaje.setMensaje("Error al inhabilitar periodo en acci\u00f3n");
                                                break;
                                            }
                                        }
                                    }
                                }
                                
                                //Actualizar estatus de SINVP
                                if (bolGrabo) {
                                    if (super.getResultSQLValidaOficioSINVP(oficio)) {
                                        if (super.getRestulSQLCallProcedureINVP(oficio, objEstatusMov.getEstatusMovId())) {
                                            bolGrabo = true;
                                        } else {
                                            objMensaje.setMensaje("Error al ejecutar el procedimiento de INVP");
                                        }
                                    } else {
                                        bolGrabo = true;
                                    }
                                }
                                
                                if (bolGrabo) {
                                    resultado = true;
                                }
                            } else {
                                resultado = true;
                            }
                        } else {
                            objMensaje.setMensaje("Autorizaci\u00f3n de Reprogramaci\u00f3n. Error al actualizar el estatus del movimiento.");
                        }
                    } else {
                        resultado = true;
                    }
                }

            }

        } catch (SQLException sql) {
            objMensaje.setMensaje("Error inesperado de SQL en la autorizaci\u00f3n de la reprogramaci\u00f3n");
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        } catch (Exception ex) {
            objMensaje.setMensaje("Error inesperado en la autorizaci\u00f3n de la reprogramaci\u00f3n");
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public List<TipoOficio> getMovimientoByTipoMovUsrTipoOficio(String tipoMovimiento, String estatusBase, String appLogin,
            String tipoUsuario, int year, int tipoFlujo, String ramoInList) {
        List<TipoOficio> lineaList = new ArrayList<TipoOficio>();
        List<TipoOficio> movimientoList = new ArrayList<TipoOficio>();
        List<FlujoFirmas> flujoFirmasList = new ArrayList<FlujoFirmas>();
        boolean bolFirmasCompletas = false;
        try {

            movimientoList = super.getResultMovimientoByTipoMovUsrTipoOficio(tipoMovimiento, estatusBase, appLogin, year, tipoFlujo, ramoInList);
            flujoFirmasList = getResultFirmantesAnteriores(tipoMovimiento, estatusBase, appLogin, tipoUsuario);

            if (flujoFirmasList.size() > 0) {

                for (TipoOficio movimiento : movimientoList) {
                    bolFirmasCompletas = false;

                    for (FlujoFirmas flujo : flujoFirmasList) {
                        if (super.getResultValidaFirma(movimiento.getOficio(), flujo.getTipoFlujo(), flujo.getAppLogin())) {
                            bolFirmasCompletas = true;
                        }
                    }

                    if (bolFirmasCompletas) {
                        lineaList.add(movimiento);
                    }
                }
            } else {
                lineaList = movimientoList;
            }

        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        } catch (IOException io) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(io, new Throwable());
            bitacora.grabaBitacora();
        }
        return lineaList;
    }

    public boolean getCancelaRechazaMovAmpRedTipoOficio(int oficio, String tipoUsr, String appLogin, String motivo, String tipoOficio, String estatusMov,
            int tipoFlujo, MovimientosAmpliacionReduccion movAmpRed, MensajeError objMensaje) {
        TipoOficio objMovimiento;
        boolean bolResultado = false;
        boolean bolGrabo = true;
        Calendar calFechaReduccion = new GregorianCalendar();
        Calendar calFechaMovtoPpto = new GregorianCalendar();
        Calendar calFechaActual = new GregorianCalendar();
        String strFechaActual = "";
        Fechas objFechas = new Fechas();

        try {
            objMovimiento = super.getResultMovimientoByFolioTipoOficio(oficio, tipoOficio);

            calFechaActual.setTime(super.getResultSQLgetServerDate());
            calFechaMovtoPpto.setTime(objMovimiento.getFecPPTO());

            if (super.getResultSQLUpdateEstatusMotivoMovTipoOficio(estatusMov, motivo, oficio, tipoOficio)) {
                if (super.getResultSQLUpdateEstatusAmpliacionesTipoOficio(estatusMov, oficio, tipoOficio)) {

                    if (calFechaActual.get(Calendar.YEAR) > calFechaMovtoPpto.get(Calendar.YEAR)) {
                        calFechaActual.set(calFechaMovtoPpto.get(Calendar.YEAR), 11, 31);//11 es Diciembre
                        strFechaActual = objFechas.getFechaFormato(calFechaActual, Fechas.FORMATO_CORTO);
                        bolGrabo = super.getResultInsertBitMovto(oficio, appLogin, estatusMov, "N", tipoOficio, "", tipoFlujo, tipoUsr, strFechaActual);
                    } else {
                        bolGrabo = super.getResultInsertBitMovto(oficio, appLogin, estatusMov, "N", tipoOficio, "", tipoFlujo, tipoUsr);
                    }

                    if (bolGrabo) {

                        //Regresa el valor tomado del ppto en las reducciones
                        Iterator iterator = (Iterator) movAmpRed.getAmpReducAccionReqList().iterator();
                        while (bolGrabo && iterator.hasNext()) {
                            AmpliacionReduccionAccionReq ampRedAccionReq = (AmpliacionReduccionAccionReq) iterator.next();

                            if (ampRedAccionReq.getTipoMovAmpRed().equals("R")) {//Reducción
                                //Se afecta el ACTUALIZADO en PPTO 
                                calFechaReduccion.setTime(objMovimiento.getFecPPTO());
                                bolGrabo = super.getResultSQLupdatePPTOreduccion(ampRedAccionReq.getImporte() * -1, calFechaReduccion.get(Calendar.YEAR),
                                        ampRedAccionReq.getRamo(), ampRedAccionReq.getDepto(), ampRedAccionReq.getFinalidad(), ampRedAccionReq.getFuncion(),
                                        ampRedAccionReq.getSubfuncion(), ampRedAccionReq.getPrgConac(), ampRedAccionReq.getPrograma(), ampRedAccionReq.getTipoProy(),
                                        ampRedAccionReq.getProy(), ampRedAccionReq.getMeta(), ampRedAccionReq.getAccion(), ampRedAccionReq.getPartida(),
                                        ampRedAccionReq.getTipoGasto(), ampRedAccionReq.getFuente(), ampRedAccionReq.getFondo(), ampRedAccionReq.getRecurso(),
                                        ampRedAccionReq.getMunicipio(), ampRedAccionReq.getDelegacion(), ampRedAccionReq.getRelLaboral(),
                                        calFechaReduccion.get(Calendar.MONTH) + 1);

                                if (!bolGrabo) {
                                    objMensaje.setMensaje("Error al devolver el importe al presupuesto");
                                }
                            }

                        }

                        if (bolGrabo) {
                            if (getProcesosTipoOficioByOficio(oficio, appLogin, motivo, tipoFlujo, tipoUsr, "A", tipoOficio, objMensaje, new ArrayList<Meta>(), new ArrayList<Accion>())) {
                                bolResultado = true;
                            }
                        }
                    } else {
                        objMensaje.setMensaje("Error al insertar en la bit\u00e1cora del movimiento");
                    }
                } else {
                    objMensaje.setMensaje("Error al actualizar el estatus de ampliaciones");
                }
            } else {
                objMensaje.setMensaje("Error al actualizar el estatus del tipo de oficio");
            }

        } catch (SQLException sql) {
            objMensaje.setMensaje("Error inesperado al cancelar/rechazar la ampliaci\u00f3n por tipo de oficio");
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        } catch (Exception ex) {
            objMensaje.setMensaje("Error inesperado al cancelar/rechazar la ampliaci\u00f3n por tipo de oficio");
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return bolResultado;
    }

    public boolean getValidaContabilidad(int ejercicio) {
        boolean resultado = false;
        int ejercicioActivoConta = 0;
        Calendar calFechaAplicacionGasto = new GregorianCalendar();
        Calendar calFechaActual = new GregorianCalendar();
        Date fechaAplicacionGasto = null;
        Date fechaActual = null;

        try {
            ejercicioActivoConta = super.getResultSQLGetEjercicioActivoConta();
            fechaAplicacionGasto = super.getResultSQLGetFechaAplicacionGasto(ejercicio);
            calFechaAplicacionGasto.setTime(fechaAplicacionGasto);

            if (ejercicio == ejercicioActivoConta && ejercicio == calFechaAplicacionGasto.get(Calendar.YEAR)) {
                fechaActual = super.getResultSQLgetServerDate();
                calFechaActual.setTime(fechaActual);

                if (calFechaAplicacionGasto.get(Calendar.MONTH) != calFechaActual.get(Calendar.MONTH)) {
                    resultado = true;
                }
            }

        } catch (Exception sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }
        return resultado;
    }

    public boolean getAutorizaMovAmpliacionReduccionTipoOficio(int year, int oficio, String tipoOficio, String tipoUsr, String appLogin,
            boolean capturaEspecial, int tipoFlujo, MovimientosAmpliacionReduccion movAmpRed, MensajeError objMensaje) {
        TipoOficio objMovimiento;
        EstatusMov objEstatusMov;
        Accion accionTemp = new Accion();
        Meta metaTemp = new Meta();

        List<FlujoFirmas> flujoFirmasList = new ArrayList<FlujoFirmas>();
        List<Accion> accionList = new ArrayList<Accion>();
        List<Meta> metaList = new ArrayList<Meta>();
        List<AmpliacionReduccionAccion> movAccionList = null;
        List<AmpliacionReduccionMeta> movMetaList = null;
        boolean bolFirmasCompletas = false;
        boolean bolResultado = false;
        boolean bolGrabo = false;
        int metaId = 0;
        int accionId = 0;
        int requerimientoId = 0;

        double estimacion = 0.0;

        Calendar calFechaMovtoPpto = new GregorianCalendar();
        Calendar calFechaActual = new GregorianCalendar();
        String strFechaPpto = "";
        String strFechaActual = "";
        Fechas objFechas = new Fechas();
        boolean isActual = true;

        try {

            objMovimiento = super.getResultMovimientoByFolioTipoOficio(oficio, tipoOficio);

            calFechaActual.setTime(super.getResultSQLgetServerDate());
            calFechaMovtoPpto.setTime(objMovimiento.getFecPPTO());
            strFechaPpto = objFechas.getFechaFormato(calFechaMovtoPpto, Fechas.FORMATO_CORTO);

            movAccionList = movAmpRed.getAmpReducAccionList();
            movMetaList = movAmpRed.getAmpReducMetaList();

            for (AmpliacionReduccionMeta meta : movMetaList) {
                metaTemp = new Meta();
                estimacion = 0.0;
                for (MovOficioEstimacion metaEstimacion : meta.getMovOficioEstimacionList()) {
                    estimacion += metaEstimacion.getValor();
                }
                if (estimacion == 0.0) {
                    if (this.validaMetaInhabilitada(year,
                            meta.getMovOficioMeta().getRamoId(),
                            meta.getMovOficioMeta().getMetaId())) {
                        metaTemp.setYear(year);
                        metaTemp.setRamo(meta.getMovOficioMeta().getRamoId());
                        metaTemp.setMetaId(meta.getMovOficioMeta().getMetaId());
                        metaList.add(metaTemp);
                    }
                }
            }
            for (AmpliacionReduccionAccion accion : movAccionList) {
                accionTemp = new Accion();
                estimacion = 0.0;
                for (MovOficioAccionEstimacion accionEstimacion : accion.getMovOficioAccionEstList()) {
                    estimacion += accionEstimacion.getValor();
                }
                if (estimacion == 0.0) {
                    if (this.validaAccionInhabilitada(year,
                            accion.getMovOficioAccion().getRamoId(),
                            accion.getMovOficioAccion().getMetaId(),
                            accion.getMovOficioAccion().getAccionId())) {
                        accionTemp.setYear(year);
                        accionTemp.setRamo(accion.getMovOficioAccion().getRamoId());
                        accionTemp.setMeta(accion.getMovOficioAccion().getMetaId());
                        accionTemp.setAccionId(accion.getMovOficioAccion().getAccionId());
                        accionList.add(accionTemp);
                    }
                }
            }

            if (capturaEspecial) {
                calFechaActual = calFechaMovtoPpto;
                if (super.getResultInsertBitMovto(oficio, appLogin, "A", "N", tipoOficio, "", tipoFlujo, tipoUsr, strFechaPpto)) {
                    bolGrabo = true;
                } else {
                    objMensaje.setMensaje("Error al insertar la autorizaci\u00f3n (captura especial) en la bit\u00e1cora del movimiento.");
                }
            } else if (calFechaActual.get(Calendar.YEAR) > calFechaMovtoPpto.get(Calendar.YEAR)) {
                isActual = false;
                calFechaActual.set(calFechaMovtoPpto.get(Calendar.YEAR), 11, 31);//11 es Diciembre
                strFechaActual = objFechas.getFechaFormato(calFechaActual, Fechas.FORMATO_CORTO);
                if (super.getResultInsertBitMovto(oficio, appLogin, "A", "N", tipoOficio, "", tipoFlujo, tipoUsr, strFechaActual)) {
                    bolGrabo = true;
                } else {
                    objMensaje.setMensaje("Error al insertar la autorizaci\u00f3n (fecha actual) en la bit\u00e1cora del movimiento.");
                }
            } else {
                if (super.getResultInsertBitMovto(oficio, appLogin, "A", "N", tipoOficio, "", tipoFlujo, tipoUsr)) {
                    bolGrabo = true;
                } else {
                    objMensaje.setMensaje("Error al insertar la autorizaci\u00f3n en la bit\u00e1cora del movimiento.");
                }
            }

            if (bolGrabo) {

                //Se cambia el estatus del movimiento cuando cumple con las firmas del flujo
                flujoFirmasList = super.getResultUsuariosAFirmar(tipoFlujo, tipoUsr);

                if (flujoFirmasList.size() > 0) {

                    for (FlujoFirmas flujoFirmas : flujoFirmasList) {

                        if (super.getResultValidaFirma(oficio, tipoFlujo, flujoFirmas.getAppLogin())) {
                            bolFirmasCompletas = true;
                        }
                    }

                    if (bolFirmasCompletas) {
                        objEstatusMov = super.getResultEstatusSiguiente(objMovimiento.getTipoMovimiento(), tipoUsr, objMovimiento.getStatus());

                        /*if (super.getResultSQLUpdateEstatusMovTipoOficio(objEstatusMov.getEstatusMovId(), oficio, tipoOficio)
                         && super.getResultSQLUpdateEstatusAmpliacionesTipoOficio(objEstatusMov.getEstatusMovId(), oficio, tipoOficio)) {*/
                        if (objEstatusMov.getEstatusMovId().equals("A")) {

                            Iterator iterator = (Iterator) movAmpRed.getAmpReducAccionReqList().iterator();
                            while (bolGrabo && iterator.hasNext()) {
                                AmpliacionReduccionAccionReq ampRedAccionReq = (AmpliacionReduccionAccionReq) iterator.next();

                                if (ampRedAccionReq.getTipoMovAmpRed().equals("A") || ampRedAccionReq.getTipoMovAmpRed().equals("C")) {

                                    //Inserta en meta las metas nuevas relacionadas al req autorizado en caso de existir (se actualiza movoficios_meta
                                    //con el id generado para que no se vuelva a insertar)
                                    if (bolGrabo && super.getResultSQLGetValidaMetasNuevasFromMovoficios(oficio, year, ampRedAccionReq.getRamo(), ampRedAccionReq.getMeta())) {
                                        metaId = super.getMaxMeta(year, ampRedAccionReq.getRamo());
                                        bolGrabo = super.getResultSQLInsertMetaFromMovoficios(oficio, year, ampRedAccionReq.getRamo(), ampRedAccionReq.getMeta(), metaId, tipoOficio);
                                        if (bolGrabo) {
                                            bolGrabo = super.getResultSQLInsertEstimacionFromMovoficios(oficio, year, ampRedAccionReq.getRamo(), ampRedAccionReq.getMeta(), metaId);
                                            if (bolGrabo) {
                                                bolGrabo = super.getResultSQLUpdateMovoficiosMeta(metaId, oficio, year, ampRedAccionReq.getRamo(), ampRedAccionReq.getMeta());
                                                if (bolGrabo) {
                                                    bolGrabo = super.getResultSQLUpdateMovoficiosMetaAccion(metaId, oficio, year, ampRedAccionReq.getRamo(), ampRedAccionReq.getMeta());
                                                    if (bolGrabo) {
                                                        bolGrabo = super.getResultSQLUpdateMovoficiosMetaAccionEstimacion(metaId, oficio, year, ampRedAccionReq.getRamo(), ampRedAccionReq.getMeta());
                                                        if (bolGrabo) {
                                                            bolGrabo = super.getResultSQLUpdateMetaAmpliaciones(oficio, ampRedAccionReq.getRamo(), metaId, ampRedAccionReq.getMeta());
                                                            if (bolGrabo) {
                                                                bolGrabo = super.getResultSQLUpdateMovoficiosMetaAccionReq(metaId, oficio, year, ampRedAccionReq.getRamo(), ampRedAccionReq.getMeta());
                                                                if (bolGrabo) {
                                                                    for (AmpliacionReduccionAccionReq ampRedAccReqAux : movAmpRed.getAmpReducAccionReqList()) {
                                                                        if (ampRedAccReqAux.getRamo().equals(ampRedAccionReq.getRamo())
                                                                                && ampRedAccReqAux.getMeta() == ampRedAccionReq.getMeta()) {
                                                                            ampRedAccReqAux.setbMetaInsertada(true);
                                                                            ampRedAccReqAux.setMetaAux(metaId);
                                                                        }
                                                                    }
                                                                }
                                                            } else {
                                                                objMensaje.setMensaje("Error al actualizar la meta en ampliaci\u00f3n. Meta " + ampRedAccionReq.getMeta() + ", ramo " + ampRedAccionReq.getRamo() + ".");
                                                            }
                                                        } else {
                                                            objMensaje.setMensaje("Error al actualizar la meta en la estimaci\u00f3n del movimiento acci\u00f3n. Meta " + ampRedAccionReq.getMeta() + ", ramo " + ampRedAccionReq.getRamo() + ".");
                                                        }
                                                    } else {
                                                        objMensaje.setMensaje("Error al actualizar la meta en el movimiento acci\u00f3n. Meta " + ampRedAccionReq.getMeta() + ", ramo " + ampRedAccionReq.getRamo() + ".");
                                                    }
                                                } else {
                                                    objMensaje.setMensaje("Error al actualizar la meta en el movimiento. Meta " + ampRedAccionReq.getMeta() + ", ramo " + ampRedAccionReq.getRamo() + ".");
                                                }
                                            } else {
                                                objMensaje.setMensaje("Error al insertar la estimaci\u00f3n de la meta " + ampRedAccionReq.getMeta() + ", ramo " + ampRedAccionReq.getRamo() + ".");
                                            }
                                        } else {
                                            objMensaje.setMensaje("Error al insertar la meta " + ampRedAccionReq.getMeta() + ", ramo " + ampRedAccionReq.getRamo() + ".");
                                        }

                                    } else {
                                        if (ampRedAccionReq.isbMetaInsertada()) {
                                            metaId = ampRedAccionReq.getMetaAux();
                                        } else {
                                            metaId = ampRedAccionReq.getMeta();
                                        }
                                    }

                                    //Inserta en accion las acciones nuevas relacionadas al req autorizado en caso de existir(se actualiza movoficios_accion
                                    //con el id generado para que no se vuelva a insertar)
                                    if (bolGrabo && super.getResultSQLGetValidaAccionesNuevasFromMovoficios(oficio, year, ampRedAccionReq.getRamo(), metaId, ampRedAccionReq.getAccion())) {
                                        accionId = super.getResultSQLMaxAccion(year, ampRedAccionReq.getRamo(), metaId);
                                        bolGrabo = super.getResultSQLInsertAccionFromMovoficios(oficio, year, ampRedAccionReq.getRamo(), metaId, ampRedAccionReq.getAccion(), accionId, tipoOficio);
                                        if (bolGrabo) {
                                            bolGrabo = super.getResultSQLInsertAccionEstimacionFromMovoficios(oficio, year, ampRedAccionReq.getRamo(), metaId, ampRedAccionReq.getAccion(), accionId);
                                            if (bolGrabo) {
                                                bolGrabo = super.getResultSQLUpdateMovoficiosAccion(accionId, oficio, year, ampRedAccionReq.getRamo(), metaId, ampRedAccionReq.getAccion());
                                                if (bolGrabo) {
                                                    bolGrabo = super.getResultSQLUpdateAccionAmpliaciones(oficio, ampRedAccionReq.getRamo(), metaId, accionId, ampRedAccionReq.getAccion());
                                                    if (bolGrabo) {
                                                        bolGrabo = super.getResultSQLUpdateMovoficiosAccionReq(accionId, oficio, year, ampRedAccionReq.getRamo(), metaId, ampRedAccionReq.getAccion());
                                                        if (bolGrabo) {
                                                            for (AmpliacionReduccionAccionReq ampRedAccReqAux : movAmpRed.getAmpReducAccionReqList()) {
                                                                if (ampRedAccReqAux.getRamo().equals(ampRedAccionReq.getRamo())
                                                                        && ampRedAccReqAux.getMeta() == ampRedAccionReq.getMeta()
                                                                        && ampRedAccReqAux.getAccion() == ampRedAccionReq.getAccion()) {
                                                                    ampRedAccReqAux.setbAccionInsertada(true);
                                                                    //ampRedAccReqAux.setMetaAux(metaId);
                                                                    ampRedAccReqAux.setAccionAux(accionId);
                                                                }
                                                            }
                                                        } else {
                                                            objMensaje.setMensaje("Error al actualizar la acci\u00f3n en el requerimiento. Acci\u00f3n " + ampRedAccionReq.getAccion() + ", meta " + ampRedAccionReq.getMeta() + ", ramo " + ampRedAccionReq.getRamo() + ".");
                                                        }
                                                    } else {
                                                        objMensaje.setMensaje("Error al actualizar la acci\u00f3n en la ampliaci\u00f3n. Acci\u00f3n " + ampRedAccionReq.getAccion() + ", meta " + ampRedAccionReq.getMeta() + ", ramo " + ampRedAccionReq.getRamo() + ".");
                                                    }
                                                } else {
                                                    objMensaje.setMensaje("Error al actualizar la acci\u00f3n en el movimiento. Acci\u00f3n " + ampRedAccionReq.getAccion() + ", meta " + ampRedAccionReq.getMeta() + ", ramo " + ampRedAccionReq.getRamo() + ".");
                                                }
                                            } else {
                                                objMensaje.setMensaje("Error al insertar la estimaci\u00f3n de la acci\u00f3n " + ampRedAccionReq.getAccion() + ", meta " + ampRedAccionReq.getMeta() + ", ramo " + ampRedAccionReq.getRamo() + ".");
                                            }
                                        } else {
                                            objMensaje.setMensaje("Error al insertar la acci\u00f3n " + ampRedAccionReq.getAccion() + ", meta " + ampRedAccionReq.getMeta() + ", ramo " + ampRedAccionReq.getRamo() + ".");
                                        }
                                    } else {
                                        if (ampRedAccionReq.isbMetaInsertada()) {
                                            metaId = ampRedAccionReq.getMetaAux();
                                        } else {
                                            metaId = ampRedAccionReq.getMeta();
                                        }
                                        if (ampRedAccionReq.isbAccionInsertada()) {
                                            accionId = ampRedAccionReq.getAccionAux();
                                        } else {
                                            accionId = ampRedAccionReq.getAccion();
                                        }
                                    }

                                    if (bolGrabo && ampRedAccionReq.getTipoMovAmpRed().equals("C")) {

                                        bolGrabo = super.getRestulSQLGetFuncionCreaCodigo(year, ampRedAccionReq.getRamo(), ampRedAccionReq.getDepto(),
                                                ampRedAccionReq.getFinalidad(), ampRedAccionReq.getFuncion(), ampRedAccionReq.getSubfuncion(),
                                                ampRedAccionReq.getPrgConac(), ampRedAccionReq.getPrograma(), ampRedAccionReq.getTipoProy(),
                                                ampRedAccionReq.getProy(), metaId, accionId, ampRedAccionReq.getPartida(), ampRedAccionReq.getTipoGasto(),
                                                ampRedAccionReq.getFuente(), ampRedAccionReq.getFondo(), ampRedAccionReq.getRecurso(),
                                                ampRedAccionReq.getMunicipio(), ampRedAccionReq.getDelegacion(), ampRedAccionReq.getRelLaboral());

                                        if (!bolGrabo) {
                                            objMensaje.setMensaje("Error al crear el c\u00f3digo para la ampliaci\u00f3n.");
                                        }
                                    }

                                    //si hay meses calendarizados menores al actual se suman esos importes y se pasan
                                    //al actual, incluyendo años anteriores con meses menores al 31 de diciembre 
                                    //afectando los registros de MOVOFICIOS_ACCION_REQ
                                    if (bolGrabo) {
                                        if (!capturaEspecial && (!super.getResultSQLisParaestatal() || super.getResultSqlGetIsAyuntamiento())) {
                                            bolGrabo = super.getResultSQLAjustarAcumuladoMesesAnteriores(ampRedAccionReq.getMovOficioAccionReq(), metaId, accionId, calFechaActual.get(Calendar.MONTH) + 1);
                                            if (!bolGrabo) {
                                                objMensaje.setMensaje("Error al ajustar el acumulado de meses anteriores.");
                                            }
                                        }
                                        //Inserta en accion_req los requerimientos del tipoficio autorizado
                                        if (bolGrabo) {
                                            requerimientoId = super.getResultSQLMaxRequerimiento(year, ampRedAccionReq.getRamo(), metaId, accionId);
                                            bolGrabo = super.getResultSQLInsertRequerimientoFromMovoficios(requerimientoId, oficio, year, ampRedAccionReq.getRamo(), ampRedAccionReq.getPrograma(),
                                                    ampRedAccionReq.getDepto(), metaId, accionId, ampRedAccionReq.getMovOficioAccionReq().getRequerimiento());

                                            if (bolGrabo) {
                                                bolGrabo = super.getResultSQLUpdateMovoficiosRequerimiento(requerimientoId, oficio, year, ampRedAccionReq.getRamo(), ampRedAccionReq.getPrograma(),
                                                        ampRedAccionReq.getDepto(), metaId, accionId, ampRedAccionReq.getMovOficioAccionReq().getRequerimiento());

                                                if (bolGrabo) {
                                                    bolGrabo = super.getResultSQLUpdateRequerimientoAmpliaciones(oficio, ampRedAccionReq.getConsecutivo(), metaId, accionId, requerimientoId);

                                                    //Se afecta el ACTUALIZADO en PPTO 
                                                    if (bolGrabo) {
                                                        bolGrabo = getUpdatePPTOAmpliacion(ampRedAccionReq, metaId, accionId, requerimientoId);
                                                        if (!bolGrabo) {
                                                            objMensaje.setMensaje("Error al actualizar el presupuesto. Requerimiento " + ampRedAccionReq.getMovOficioAccionReq().getRequerimiento() + ", acci\u00f3n " + ampRedAccionReq.getAccion() + ", meta " + ampRedAccionReq.getMeta() + ", ramo " + ampRedAccionReq.getRamo() + ".");
                                                        }
                                                    } else {
                                                        objMensaje.setMensaje("Error al actualizar la ampliaci\u00f3n. Requerimiento " + ampRedAccionReq.getMovOficioAccionReq().getRequerimiento() + ", acci\u00f3n " + ampRedAccionReq.getAccion() + ", meta " + ampRedAccionReq.getMeta() + ", ramo " + ampRedAccionReq.getRamo() + ".");
                                                    }
                                                } else {
                                                    objMensaje.setMensaje("Error al actualizar el movimiento. Requerimiento " + ampRedAccionReq.getMovOficioAccionReq().getRequerimiento() + ", acci\u00f3n " + ampRedAccionReq.getAccion() + ", meta " + ampRedAccionReq.getMeta() + ", ramo " + ampRedAccionReq.getRamo() + ".");
                                                }
                                            } else {
                                                objMensaje.setMensaje("Error al insertar el requerimiento " + ampRedAccionReq.getMovOficioAccionReq().getRequerimiento() + ", acci\u00f3n " + ampRedAccionReq.getAccion() + ", meta " + ampRedAccionReq.getMeta() + ", ramo " + ampRedAccionReq.getRamo() + ".");
                                            }
                                        }
                                    }
                                }

                            }

                            if (bolGrabo) {
                                if (!super.getResultSQLisParaestatal() || super.getResultSqlGetIsAyuntamiento()) {
                                    bolGrabo = generaMomentosContables(year, oficio, tipoOficio, isActual, calFechaActual, appLogin,
                                            "A", objMovimiento.getAppLogin(), objMensaje);
                                    if (!bolGrabo) {
                                        objMensaje.setMensaje("Error al generar momentos contables. " + objMensaje.getMensaje());
                                    }
                                }
                                if (bolGrabo) {
                                    bolGrabo = super.getResultSQLUpdateEstatusMovTipoOficio(objEstatusMov.getEstatusMovId(), oficio, tipoOficio);
                                    if (bolGrabo) {
                                        bolGrabo = super.getResultSQLUpdateEstatusAmpliacionesTipoOficio(objEstatusMov.getEstatusMovId(), oficio, tipoOficio);
                                        if (bolGrabo) {
                                            if (getProcesosTipoOficioByOficio(oficio, appLogin, "", tipoFlujo, tipoUsr, "A", tipoOficio, objMensaje, metaList, accionList)) {
                                                bolResultado = true;
                                            }
                                        } else {
                                            objMensaje.setMensaje("Error al actualizar el estatus de la ampliaci\u00f3n por tipo de oficio.");
                                        }
                                    } else {
                                        objMensaje.setMensaje("Error al actualizar el estatus del movimiento por tipo de oficio.");
                                    }
                                }
                            }

                        } else {
                            bolResultado = true;
                        }
                        //}
                    } else {
                        bolResultado = true;
                    }
                }

            }

        } catch (SQLException sql) {
            objMensaje.setMensaje("Error inesperado de sql al autorizar la ampliaci\u00f3n por tipo de oficio");
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        } catch (Exception ex) {
            objMensaje.setMensaje("Error inesperado al autorizar la ampliaci\u00f3n por tipo de oficio");
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        } finally {
        }
        return bolResultado;
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

    public boolean getUpdatePPTOAmpliacion(AmpliacionReduccionAccionReq ampRedAccionReq, int meta, int accion, int requerimiento) throws Exception, SQLException {
        boolean bolGrabo = true;
        boolean bolResultado = false;
        int contMes = 1;
        double cantXmes = 0;
        String mensaje = "";
        double costoUnitario = 0;

        costoUnitario = ampRedAccionReq.getMovOficioAccionReq().getCostoUnitario();

        if (!super.getResultSQLisCodigoRepetido(ampRedAccionReq.getMovOficioAccionReq().getYear(), ampRedAccionReq.getRamo(), ampRedAccionReq.getDepto(),
                ampRedAccionReq.getFinalidad(), ampRedAccionReq.getFuncion(), ampRedAccionReq.getSubfuncion(), ampRedAccionReq.getPrgConac(),
                ampRedAccionReq.getPrograma(), ampRedAccionReq.getTipoProy(), ampRedAccionReq.getProy(), meta, accion, ampRedAccionReq.getPartida(),
                ampRedAccionReq.getTipoGasto(), ampRedAccionReq.getFuente(), ampRedAccionReq.getFondo(), ampRedAccionReq.getRecurso(), ampRedAccionReq.getMunicipio(),
                ampRedAccionReq.getDelegacion(), ampRedAccionReq.getRelLaboral())) {

            bolGrabo = super.getResultSQLInsertCodigo(ampRedAccionReq.getMovOficioAccionReq().getYear(), ampRedAccionReq.getRamo(), ampRedAccionReq.getDepto(),
                    ampRedAccionReq.getFinalidad(), ampRedAccionReq.getFuncion(), ampRedAccionReq.getSubfuncion(), ampRedAccionReq.getPrgConac(),
                    ampRedAccionReq.getPrograma(), ampRedAccionReq.getTipoProy(), ampRedAccionReq.getProy(), meta, accion, ampRedAccionReq.getPartida(),
                    ampRedAccionReq.getTipoGasto(), ampRedAccionReq.getFuente(), ampRedAccionReq.getFondo(), ampRedAccionReq.getRecurso(), ampRedAccionReq.getMunicipio(),
                    ampRedAccionReq.getDelegacion(), ampRedAccionReq.getRelLaboral());

        }

        while (bolGrabo && contMes <= 12) {
            if(ampRedAccionReq.getConsecutivo() == 144){
                System.out.println("OFICIO " + ampRedAccionReq.getMovOficioAccionReq().getOficio()+
                    "\nYEAR "+ ampRedAccionReq.getMovOficioAccionReq().getYear()+
                        "\nRAMO" + ampRedAccionReq.getMovOficioAccionReq().getRamo()+
                    "\nDEPTO"+ampRedAccionReq.getMovOficioAccionReq().getDepto()+
                        "\nFINALIDAD" +ampRedAccionReq.getFinalidad()+"\nFUNCION"+ ampRedAccionReq.getFuncion()+
                    "\nSUBFUNCION" + ampRedAccionReq.getSubfuncion() + "\nPRG_CONAC" +ampRedAccionReq.getPrgConac() +
                        "\nPRG " + ampRedAccionReq.getMovOficioAccionReq().getPrograma() + 
                    "\nTIPO_PRYO " + ampRedAccionReq.getTipoProy()+
                        "\nPROY" + ampRedAccionReq.getProy()+ "\nMETA "+meta + "\nACCION " +accion+ "\nPARTIDA " +ampRedAccionReq.getMovOficioAccionReq().getPartida() + 
                    "\nTIPO GASTO " + ampRedAccionReq.getMovOficioAccionReq().getTipoGasto() + "\nFUENTE " + ampRedAccionReq.getMovOficioAccionReq().getFuente() +
                    "\nFONDO " + ampRedAccionReq.getMovOficioAccionReq().getFondo() + "\nREL_LABORAL" + ampRedAccionReq.getMovOficioAccionReq().getRecurso() +
                    "\nMPO " + ampRedAccionReq.getMunicipio() + "\nDELEGACION " + ampRedAccionReq.getDelegacion() + "\n REL LAB" + ampRedAccionReq.getMovOficioAccionReq().getRelLaboral() +
                    "\nRREQ " + requerimiento);
            }
            mensaje = super.getRestulSQLCallAmpliacionCodPPTO(ampRedAccionReq.getMovOficioAccionReq().getOficio(),
                    ampRedAccionReq.getMovOficioAccionReq().getYear(), ampRedAccionReq.getMovOficioAccionReq().getRamo(),
                    ampRedAccionReq.getMovOficioAccionReq().getDepto(), ampRedAccionReq.getFinalidad(), ampRedAccionReq.getFuncion(),
                    ampRedAccionReq.getSubfuncion(), ampRedAccionReq.getPrgConac(), ampRedAccionReq.getMovOficioAccionReq().getPrograma(),
                    ampRedAccionReq.getTipoProy(), ampRedAccionReq.getProy(), meta, accion, ampRedAccionReq.getMovOficioAccionReq().getPartida(),
                    ampRedAccionReq.getMovOficioAccionReq().getTipoGasto(), ampRedAccionReq.getMovOficioAccionReq().getFuente(),
                    ampRedAccionReq.getMovOficioAccionReq().getFondo(), ampRedAccionReq.getMovOficioAccionReq().getRecurso(),
                    ampRedAccionReq.getMunicipio(), ampRedAccionReq.getDelegacion(), ampRedAccionReq.getMovOficioAccionReq().getRelLaboral(),
                    requerimiento, contMes);

            if (!mensaje.equals("exito")) {
                bolGrabo = false;
            }

            contMes++;
        }

        if (bolGrabo) {
            bolResultado = true;
        }

        return bolResultado;
    }

    public boolean getProcesosTipoOficioByOficio(int oficio, String appLogin,
            String motivo, int tipoFlujo, String tipoUsr, String tipoMovto,
            String tipoOficio, MensajeError objMensaje, List<Meta> metaList,
            List<Accion> accionList) throws SQLException {
        boolean bolGrabo = true;
        boolean bolResultado = false;
        List<TipoOficio> tipoOficioList;
        boolean bolTodosCancelados = true;
        boolean bolTodosAutorizados = true;
        boolean bolTodosRechazadosTipoOficio = true;
        String estatus = new String();

        //Revisar estatus de los tipoficio del movoficios si todos son <> T
        //Si todos los tipo oficio = C --> movoficios.estatus = C
        //Si por lo menos uno es = A --> movoficios.estatus = A
        if (super.getResultSQLCountTipoOficioByOficioEstatus(oficio, "T") == 0) {

            if (super.getResultSQLCountTipoOficioByOficioEstatus(oficio, "A") > 0) {
                bolGrabo = super.getResultSQLUpdateEstatusMov("A", oficio);
                if (!bolGrabo) {
                    objMensaje.setMensaje("Error al actualizar a autorizado el estatus del movimiento");
                }
            }

            if (bolGrabo) {
                tipoOficioList = super.getResultSQLGetStatusTipoOficioByOficio(oficio);
                
                if(tipoOficioList.isEmpty()){
                    bolTodosCancelados = false;
                    bolTodosAutorizados = false;
                    bolTodosRechazadosTipoOficio = false;
                }
                
                for (TipoOficio tipOf : tipoOficioList) {
                    if (!tipOf.getStatusTipoOficio().equals("C")) {
                        bolTodosCancelados = false;
                    }
                    if (!tipOf.getStatusTipoOficio().equals("A")) {
                        bolTodosAutorizados = false;
                    }
                    if (!tipOf.getStatusTipoOficio().equals("K")) {
                        bolTodosRechazadosTipoOficio = false;
                    }
                }

                if (bolTodosCancelados || bolTodosAutorizados) {

                    if (bolTodosCancelados) {
                        estatus = "C";
                        bolGrabo = super.getResultSQLUpdateEstatusMov("C", oficio);
                        if (!bolGrabo) {
                            objMensaje.setMensaje("Error al actualizar a cancelado el estatus del movimiento");
                        }
                    }

                    if (bolTodosAutorizados) {

                        estatus = "A";

                        if (bolGrabo && super.getResultSQLCountMovoficiosMetasEditadas(oficio) > 0) {
                            bolGrabo = super.getResultInsertHistMeta(oficio);
                            if (bolGrabo) {
                                bolGrabo = super.getResultUpdateReprogramacionMeta(oficio);
                                if (bolGrabo) {
                                    bolGrabo = super.getResultInsertHistCalendarizacionMeta(oficio);
                                    if (bolGrabo) {
                                        if (super.getResultUpdateCalendarizacionMeta(oficio)) {
                                            bolGrabo = true;
                                        } else {
                                            objMensaje.setMensaje("Error al insertar la calendarizaci\u00f3n las metas editadas");
                                        }
                                    } else {
                                        objMensaje.setMensaje("Error al insertar el hist\u00f3rico de la calendarizaci\u00f3n las metas editadas");
                                    }
                                } else {
                                    objMensaje.setMensaje("Error al actualizar metas editadas");
                                }
                            } else {
                                objMensaje.setMensaje("Error al insertar el hist\u00f3rico de las metas editadas");
                            }
                        }

                        if (bolGrabo && super.getResultSQLCountMovoficiosAccionEditadas(oficio) > 0) {
                            bolGrabo = super.getResultInsertHistAccion(oficio);
                            if (bolGrabo) {
                                bolGrabo = super.getResultUpdateReprogramacionAccion(oficio);
                                if (bolGrabo) {
                                    bolGrabo = super.getResultInsertHistCalendarizacionAccion(oficio);
                                    if (bolGrabo) {
                                        if (super.getResultUpdateCalendarizacionAccion(oficio)) {
                                            bolGrabo = true;
                                        } else {
                                            objMensaje.setMensaje("Error al insertar la calendarizaci\u00f3n las acciones editadas");
                                        }
                                    } else {
                                        objMensaje.setMensaje("Error al insertar el hist\u00f3rico de la calendarizaci\u00f3n las acciones editadas");
                                    }
                                } else {
                                    objMensaje.setMensaje("Error al actualizar las acciones editadas");
                                }
                            } else {
                                objMensaje.setMensaje("Error al insertar el hist\u00f3rico de las acciones editadas");
                            }
                        }

                        if (bolGrabo) {
                            for (Meta meta : metaList) {
                                if (!super.getResultSQLUpdatePeriodoMeta(meta.getYear(), meta.getRamo(), meta.getMetaId())) {
                                    bolGrabo = false;
                                    objMensaje.setMensaje("Error al inhabilitar periodo en meta");
                                    break;
                                } else {
                                    if (!super.getResultSQLUpdatePeriodoAccionByMeta(meta.getYear(), meta.getRamo(), meta.getMetaId())) {
                                        bolGrabo = false;
                                        objMensaje.setMensaje("Error al inhabilitar periodo en acciones de meta inhabiltada");
                                        break;
                                    }
                                }
                            }
                            if (bolGrabo) {
                                for (Accion accion : accionList) {
                                    if (!super.getResultSQLUpdatePeriodoAccion(accion.getYear(), accion.getRamo(),
                                            accion.getMeta(), accion.getAccionId())) {
                                        bolGrabo = false;
                                        objMensaje.setMensaje("Error al inhabilitar periodo en acci\u00f3n");
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    //Actualizar estatus de SINVP
                    if (bolGrabo) {
                        if (super.getResultSQLValidaOficioSINVP(oficio)) {
                            if (super.getRestulSQLCallProcedureINVP(oficio, estatus)) {
                                bolResultado = true;
                            } else {
                                objMensaje.setMensaje("Error al ejecutar el procedimiento de INVP");
                            }
                        } else {
                            bolResultado = true;
                        }
                    }

                } else if (bolTodosRechazadosTipoOficio) {

                    if (super.getResultSQLUpdateEstatusMotivoMov("R", motivo, oficio)) {
                        if (super.getResultSQLUpdateEstatusTipoOficio("R", oficio)) {
                            if (tipoMovto.equals("A")) {//Ampliación
                                bolGrabo = super.getResultSQLUpdateEstatusAmpliaciones("R", oficio);
                                if (!bolGrabo) {
                                    objMensaje.setMensaje("Error al actualizar el estatus en ampliaciones");
                                }
                            } else if (tipoMovto.equals("T")) {//Transferencia
                                bolGrabo = super.getResultSQLUpdateEstatusTransferencia("R", oficio);
                                if (!bolGrabo) {
                                    objMensaje.setMensaje("Error al actualizar el estatus en transferencias");
                                }
                            }
                            if (bolGrabo) {
                                if (super.getResultSQLgetUpdateAutorizoBitmovtos(oficio, appLogin, "R", tipoFlujo, tipoUsr)) {
                                    bolResultado = true;
                                } else {
                                    objMensaje.setMensaje("Error insertar en la bit\u00e1cora");
                                }
                            }
                        } else {
                            objMensaje.setMensaje("Error al actualizar el estatus de tipo oficio");
                        }
                    } else {
                        objMensaje.setMensaje("Error al actualizar el estatus del movimiento");
                    }
                } else {
                    bolResultado = true;
                }
            }
        } else {
            bolResultado = true;
        }

        return bolResultado;
    }

    public boolean getCancelaRechazaMovAmpRed(int oficio, String tipoUsr, String appLogin, String motivo, String estatusActual,
            String estatusNvo, int tipoFlujo, MovimientosAmpliacionReduccion movAmpRed, MensajeError objMensaje) throws SQLException {
        Movimiento objMovimiento;
        boolean bolResultado = false;
        boolean bolGrabo = true;
        Calendar calFechaReduccion = new GregorianCalendar();
        String tipoOficio = "A";
        Calendar calFechaMovtoPpto = new GregorianCalendar();
        Calendar calFechaActual = new GregorianCalendar();
        String strFechaActual = "";
        Fechas objFechas = new Fechas();

        try {
            objMovimiento = super.getResultMovimientoByFolio(oficio);

            calFechaActual.setTime(super.getResultSQLgetServerDate());

            if (objMovimiento.getFecPPTO() != null) {
                calFechaMovtoPpto.setTime(objMovimiento.getFecPPTO());
            } else {
                calFechaMovtoPpto.setTime(objMovimiento.getFechaElab());
            }

            if (super.getResultSQLUpdateEstatusMotivoMov(estatusNvo, motivo, oficio)) {
                if (!estatusActual.equals("X")) {
                    bolGrabo = super.getResultSQLUpdateEstatusTipoOficio(estatusNvo, oficio);
                } else {
                    bolGrabo = true;
                }
                if (bolGrabo) {
                    if (super.getResultSQLUpdateEstatusAmpliaciones(estatusNvo, oficio)) {

                        if (calFechaActual.get(Calendar.YEAR) > calFechaMovtoPpto.get(Calendar.YEAR)) {
                            calFechaActual.set(calFechaMovtoPpto.get(Calendar.YEAR), 11, 31);//11 es Diciembre
                            strFechaActual = objFechas.getFechaFormato(calFechaActual, Fechas.FORMATO_CORTO);
                            bolGrabo = super.getResultInsertBitMovto(oficio, appLogin, estatusNvo, "N", tipoOficio, "", tipoFlujo, tipoUsr, strFechaActual);
                        } else {
                            bolGrabo = super.getResultInsertBitMovto(oficio, appLogin, estatusNvo, "N", tipoOficio, "", tipoFlujo, tipoUsr);
                        }

                        if (bolGrabo) {

                            if (!estatusActual.equals("X") && !estatusActual.equals("R")) {
                                //Regresa el valor tomado del ppto en las reducciones
                                Iterator iterator = (Iterator) movAmpRed.getAmpReducAccionReqList().iterator();
                                while (bolGrabo && iterator.hasNext()) {
                                    AmpliacionReduccionAccionReq ampRedAccionReq = (AmpliacionReduccionAccionReq) iterator.next();

                                    if (ampRedAccionReq.getTipoMovAmpRed().equals("R")) {
                                        //Se afecta el ACTUALIZADO en PPTO 
                                        calFechaReduccion.setTime(objMovimiento.getFecPPTO());
                                        bolGrabo = super.getResultSQLupdatePPTOreduccion(ampRedAccionReq.getImporte() * -1, calFechaReduccion.get(Calendar.YEAR),
                                                ampRedAccionReq.getRamo(), ampRedAccionReq.getDepto(), ampRedAccionReq.getFinalidad(), ampRedAccionReq.getFuncion(),
                                                ampRedAccionReq.getSubfuncion(), ampRedAccionReq.getPrgConac(), ampRedAccionReq.getPrograma(), ampRedAccionReq.getTipoProy(),
                                                ampRedAccionReq.getProy(), ampRedAccionReq.getMeta(), ampRedAccionReq.getAccion(), ampRedAccionReq.getPartida(),
                                                ampRedAccionReq.getTipoGasto(), ampRedAccionReq.getFuente(), ampRedAccionReq.getFondo(), ampRedAccionReq.getRecurso(),
                                                ampRedAccionReq.getMunicipio(), ampRedAccionReq.getDelegacion(), ampRedAccionReq.getRelLaboral(),
                                                calFechaReduccion.get(Calendar.MONTH) + 1);

                                        if (!bolGrabo) {
                                            objMensaje.setMensaje("Error al devolver el importe al presupuesto");
                                        }
                                    }

                                }
                            }

                            if (bolGrabo) {
                                if (getProcesosTipoOficioByOficio(oficio, appLogin, motivo, tipoFlujo, tipoUsr, "A", tipoOficio, objMensaje, new ArrayList<Meta>(), new ArrayList<Accion>())) {
                                    bolResultado = true;
                                }
                            }
                        } else {
                            objMensaje.setMensaje("Error al insertar en la bit\u00e1cora del movimiento");
                        }
                    } else {
                        objMensaje.setMensaje("Error al actualizar el estatus de ampliaciones");
                    }
                } else {
                    objMensaje.setMensaje("Error al actualizar el estatus del tipo de oficio");
                }
            } else {
                objMensaje.setMensaje("Error al actualizar el estatus del movimiento");
            }

        } catch (SQLException sql) {
            objMensaje.setMensaje("Error inesperado en el rechazo/cancelaci\u00f3n del movimiento");
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        } catch (Exception ex) {
            objMensaje.setMensaje("Error inesperado en el rechazo/cancelaci\u00f3n del movimiento");
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return bolResultado;
    }

    public boolean getAutorizaMovAmpliacionReduccion(int year, int oficio, String tipoUsr, String appLogin,
            boolean capturaEspecial, int tipoFlujo, MovimientosAmpliacionReduccion movAmpRed, MensajeError objMensaje) throws SQLException {
        Movimiento objMovimiento;
        EstatusMov objEstatusMov;
        List<FlujoFirmas> flujoFirmasList = new ArrayList<FlujoFirmas>();
        boolean bolFirmasCompletas = false;
        boolean resultado = false;
        boolean bolGrabo = false;
        Calendar calFechaMovtoPpto = new GregorianCalendar();
        Calendar calFechaActual = new GregorianCalendar();
        String strFechaPpto = new String();
        String strFechaActual = new String();
        Fechas objFechas = new Fechas();

        try {

            objMovimiento = super.getResultMovimientoByFolio(oficio);

            calFechaActual.setTime(super.getResultSQLgetServerDate());
            calFechaMovtoPpto.setTime(objMovimiento.getFecPPTO());
            strFechaPpto = objFechas.getFechaFormato(calFechaMovtoPpto, Fechas.FORMATO_CORTO);

            if (capturaEspecial) {
                if (super.getResultInsertBitMovto(oficio, appLogin, "A", "N", "A", "", tipoFlujo, tipoUsr, strFechaPpto)) {
                    bolGrabo = true;
                } else {
                    objMensaje.setMensaje("Error al insertar la autorizaci\u00f3n (captura especial) en la bit\u00e1cora del movimiento.");
                }

            } else if (calFechaActual.get(Calendar.YEAR) > calFechaMovtoPpto.get(Calendar.YEAR)) {
                calFechaActual.set(calFechaMovtoPpto.get(Calendar.YEAR), 11, 31);//11 es Diciembre
                strFechaActual = objFechas.getFechaFormato(calFechaActual, Fechas.FORMATO_CORTO);
                if (super.getResultInsertBitMovto(oficio, appLogin, "A", "N", "A", "", tipoFlujo, tipoUsr, strFechaActual)) {
                    bolGrabo = true;
                } else {
                    objMensaje.setMensaje("Error al insertar la autorizaci\u00f3n (fecha actual) en la bit\u00e1cora del movimiento.");
                }

            } else {
                if (super.getResultInsertBitMovto(oficio, appLogin, "A", "N", "A", "", tipoFlujo, tipoUsr)) {
                    bolGrabo = true;
                } else {
                    objMensaje.setMensaje("Error al insertar la autorizaci\u00f3n en la bit\u00e1cora del movimiento.");
                }
            }

            if (bolGrabo) {

                //Se cambia el estatus del movimiento cuando cumple con las firmas del flujo
                flujoFirmasList = super.getResultUsuariosAFirmar(tipoFlujo, tipoUsr);

                if (flujoFirmasList.size() > 0) {

                    for (FlujoFirmas flujoFirmas : flujoFirmasList) {

                        if (super.getResultValidaFirma(oficio, tipoFlujo, flujoFirmas.getAppLogin())) {
                            bolFirmasCompletas = true;
                        }
                    }

                    if (bolFirmasCompletas) {
                        objEstatusMov = super.getResultEstatusSiguiente(objMovimiento.getTipoMovimiento(), tipoUsr, objMovimiento.getStatus());

                        if (super.getResultSQLUpdateEstatusMov(objEstatusMov.getEstatusMovId(), oficio)) {
                            if (super.getResultSQLUpdateEstatusTipoOficio(objEstatusMov.getEstatusMovId(), oficio)) {
                                if (super.getResultSQLUpdateEstatusAmpliaciones(objEstatusMov.getEstatusMovId(), oficio)) {
                                    resultado = true;
                                } else {
                                    objMensaje.setMensaje("Error al actualizar el estatus de la ampliaci\u00f3n.");
                                }
                            } else {
                                objMensaje.setMensaje("Error al actualizar el estatus del tipo de oficio.");
                            }
                        } else {
                            objMensaje.setMensaje("Error al actualizar el estatus del movimiento.");
                        }
                    } else {
                        resultado = true;
                    }
                }

            }

        } catch (SQLException sql) {
            objMensaje.setMensaje("Error inesperado de sql al autorizar la ampliaci\u00f3n");
            resultado = false;
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        } catch (Exception ex) {
            objMensaje.setMensaje("Error inesperado al autorizar la ampliaci\u00f3n");
            resultado = false;
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }

        return resultado;
    }

    public boolean generaMomentosContables(int year, int oficio, String tipoOficio, boolean isActual, Calendar calFecha, String appLogin,
            String tipoMov, String usrRegistraMovto, MensajeError objMensaje) throws SQLException, Exception {
        boolean bolGrabo = true;
        boolean bolResultado = false;
        String idTramite = new String();
        long poliza = 0;
        Fechas objFechas = new Fechas();
        Parametro objParametro;
        String strFecha = new String();
        String strFechaRegistro = new String();
        Date fechaContabilidad = null;
        Calendar calFechaContabilidad = new GregorianCalendar();
        List<ProcesoMomentoCFG> procesoMomentoCFGList = null;
        List<MovimientoContabilidad> movtoContabilidadList = null;
        ProcesoMomento procesoMomento = null;
        String concepto = new String();
        String ctaContableMomento = new String();
        String ctaContableMomentoRelacionado = new String();
        double importe = 0;
        String tipoMovAux = "";
        String municipioUsrCaptura = "";        
        int i = 0;
        int intValorMomento = 0;
        int intValorMomentoRelacionado = 0;
        Object objectList = null;
        boolean bolTraspasoPol = false;

        try {
            strFecha = objFechas.getFechaFormato(calFecha, Fechas.FORMATO_CORTO);
            strFechaRegistro = strFecha;

            fechaContabilidad = getResultSQLGetFechaContabilidad(year);

            if (fechaContabilidad != null && fechaContabilidad.after(calFecha.getTime())) {
                calFechaContabilidad.setTime(fechaContabilidad);
                strFechaRegistro = objFechas.getFechaFormato(calFechaContabilidad, Fechas.FORMATO_CORTO);
            }

            objParametro = getResultValoresMomentosContables();
            poliza = getResultSQLgetSecuenciaPoliza(isActual, objFechas.getFechaFormato(calFecha, Fechas.FORMATO_YYMMDD));

            municipioUsrCaptura = getSQLMunicipioUsuario(usrRegistraMovto);

            if (super.getResultSQLInsertBuzonMomentos(objParametro.getEmpresaCG(), calFecha.get(Calendar.YEAR), poliza,
                    objParametro.getOrigenPolMCCG(), "AUTORIZACION DE PRESUPUESTOS", strFecha, 0, appLogin, strFechaRegistro, "A")) {

                //Se determinana los momentos contables a generar
                procesoMomento = super.getResultSQLProcesoMomentoByTipo(tipoOficio);
                procesoMomentoCFGList = super.getResultSQLProcesoMomentoCFG(procesoMomento.getProcesoMomento());

                if (procesoMomentoCFGList.size() > 0) {
                    idTramite = getResultSQLgetSecuenciaMomentoCont(isActual, objParametro.getSiglasIdTramite(), calFecha.get(Calendar.YEAR));

                    if (getResultSQLUpdateIdTramiteTipoOficio(oficio, tipoOficio, idTramite)) {

                        //Se agrupan los movimientos presupuestales por OFICIO, TIPOFICIO, TIPOMOV (A,R), PARTIDA para insertase en el detalle
                        movtoContabilidadList = super.getResultSQLMovtosContabilidad(year, oficio, tipoOficio, tipoMov);

                        if (movtoContabilidadList != null && movtoContabilidadList.size() > 0) {
                            bolTraspasoPol = true;
                        }

                        i = 1;
                        Iterator iteratorProcesoMomentoCFG = (Iterator) procesoMomentoCFGList.iterator();
                        while (bolGrabo && iteratorProcesoMomentoCFG.hasNext()) {
                            ProcesoMomentoCFG momentoCFG = (ProcesoMomentoCFG) iteratorProcesoMomentoCFG.next();

                            Iterator iteratorMovimientoContabilidad = (Iterator) movtoContabilidadList.iterator();
                            while (bolGrabo && iteratorMovimientoContabilidad.hasNext()) {
                                MovimientoContabilidad movConta = (MovimientoContabilidad) iteratorMovimientoContabilidad.next();

                                concepto = oficio + "-" + tipoMov + "-" + movConta.getTipoMov();
                                ctaContableMomento = super.getResultSQLCuentaContable(calFecha.get(Calendar.YEAR), movConta.getPartida(), momentoCFG.getMomento());
                                ctaContableMomentoRelacionado = super.getResultSQLCuentaContable(calFecha.get(Calendar.YEAR), movConta.getPartida(), momentoCFG.getMomentoRelacionado());

                                if (ctaContableMomento.equals("") || ctaContableMomento.equals("null")) {
                                    objMensaje.setMensaje("Error al obtener la cuenta contable del momento. Partida " + movConta.getPartida() + ", momento " + momentoCFG.getMomento() + ". " + i);
                                    bolGrabo = false;
                                } else if (ctaContableMomentoRelacionado.equals("") || ctaContableMomentoRelacionado.equals("null")) {
                                    objMensaje.setMensaje("Error al obtener la cuenta contable del momento relacionado. Partida " + movConta.getPartida() + ", momento relacionado " + momentoCFG.getMomentoRelacionado() + ". " + i);
                                    bolGrabo = false;
                                }

                                if (movConta.getImporte() < 0) {
                                    importe = movConta.getImporte() * -1;
                                } else {
                                    importe = movConta.getImporte();
                                }

                                if (movConta.getTipoMov().equals("R")) { //Reduccion
                                    intValorMomento = 3;
                                    intValorMomentoRelacionado = 4;
                                } else {
                                    intValorMomento = 1;
                                    intValorMomentoRelacionado = 2;
                                }

                                //Momento
                                if (bolGrabo) {
                                    bolGrabo = super.getResultSQLInsertBuzonMomentosDet(objParametro.getEmpresaCG(), calFecha.get(Calendar.YEAR), poliza,
                                            objParametro.getOrigenPolMCCG(), i, momentoCFG.getTipoMomento(), momentoCFG.getMomento(),
                                            concepto, "OFICIO - TIPOMOV - AMP/RED", idTramite, municipioUsrCaptura, oficio, procesoMomento.getClave(),
                                            importe, intValorMomento, ctaContableMomento, "A", momentoCFG.getMomentoPoliza(),
                                            objParametro.getOrigenPolMCCG(), movConta.getPartida());

                                    if (!bolGrabo) {
                                        objMensaje.setMensaje("Error al insertar el detalle del momento contable. " + i);
                                    }

                                    i++;
                                    if (bolGrabo) {
                                        //Momento Relacionado
                                        bolGrabo = super.getResultSQLInsertBuzonMomentosDet(objParametro.getEmpresaCG(), calFecha.get(Calendar.YEAR), poliza,
                                                objParametro.getOrigenPolMCCG(), i, momentoCFG.getTipoMomento(), momentoCFG.getMomentoRelacionado(),
                                                concepto, "OFICIO - TIPOMOV - AMP/RED", idTramite, municipioUsrCaptura, oficio, procesoMomento.getClave(),
                                                importe, intValorMomentoRelacionado, ctaContableMomentoRelacionado, "A", momentoCFG.getMomentoPoliza(),
                                                objParametro.getOrigenPolMCCG(), movConta.getPartida());
                                        if (!bolGrabo) {
                                            objMensaje.setMensaje("Error al insertar el detalle del momento contable relacionado. " + i);
                                        }
                                    }

                                    i++;
                                }
                            }

                        }

                        i = 1;
                        if (tipoMov.equals("A")) { //Ampliación/Reducción
                            objectList = getAmpliacionReduccionAccionReqByTipoOficioMomentos(oficio, tipoOficio, year);
                            Iterator iteratorAmpliacionReduccionAccionReq = (Iterator) ((List<AmpliacionReduccionAccionReq>) objectList).iterator();
                            while (bolGrabo && iteratorAmpliacionReduccionAccionReq.hasNext()) {
                                AmpliacionReduccionAccionReq ampRedAccionReq = (AmpliacionReduccionAccionReq) iteratorAmpliacionReduccionAccionReq.next();

                                concepto = oficio + "-" + tipoMov + "-" + ampRedAccionReq.getTipoMovAmpRed();

                                bolGrabo = super.getResultSQLInsertBuzonMomentosCod(objParametro.getEmpresaCG(), calFecha.get(Calendar.YEAR), poliza,
                                        objParametro.getOrigenPolMCCG(), i, idTramite, concepto, ampRedAccionReq.getRamo(), ampRedAccionReq.getDepto(),
                                        ampRedAccionReq.getFinalidad(), ampRedAccionReq.getFuncion(), ampRedAccionReq.getSubfuncion(), ampRedAccionReq.getPrgConac(),
                                        ampRedAccionReq.getPrograma(), ampRedAccionReq.getTipoProy(), ampRedAccionReq.getProy(), ampRedAccionReq.getMeta(),
                                        ampRedAccionReq.getAccion(), ampRedAccionReq.getPartida(), ampRedAccionReq.getTipoGasto(), ampRedAccionReq.getFuente(),
                                        ampRedAccionReq.getFondo(), ampRedAccionReq.getRecurso(), ampRedAccionReq.getMunicipio(), ampRedAccionReq.getDelegacion(),
                                        ampRedAccionReq.getRelLaboral(), ampRedAccionReq.getImporte());

                                if (!bolGrabo) {
                                    objMensaje.setMensaje("Error al insertar el c\u00f3digo del momento contable. Ampliaci\u00f3n " + i);
                                }

                                i++;
                            }
                        }
                        if (tipoMov.equals("T")) { //Transferencia
                            List<Transferencia> listTransferencia = null;
                            List<TransferenciaAccionReq> listTransferenciaAccionReq = null;

                            listTransferencia = super.getTransferenciaByTipoOficio(oficio, tipoOficio, year);

                            if (listTransferencia != null && listTransferencia.size() > 0) {
                                Iterator iteratorTransferencia = (Iterator) listTransferencia.iterator();
                                while (bolGrabo && iteratorTransferencia.hasNext()) {
                                    Transferencia transferencia = (Transferencia) iteratorTransferencia.next();

                                    concepto = oficio + "-" + tipoMov + "-" + "R";

                                    bolGrabo = super.getResultSQLInsertBuzonMomentosCod(objParametro.getEmpresaCG(), calFecha.get(Calendar.YEAR), poliza,
                                            objParametro.getOrigenPolMCCG(), i, idTramite, concepto, transferencia.getRamo(), transferencia.getDepto(),
                                            transferencia.getFinalidad(), transferencia.getFuncion(), transferencia.getSubfuncion(), transferencia.getPrgConac(),
                                            transferencia.getPrograma(), transferencia.getTipoProy(), transferencia.getProyecto(), transferencia.getMeta(),
                                            transferencia.getAccion(), transferencia.getPartida(), transferencia.getTipoGasto(), transferencia.getFuente(),
                                            transferencia.getFondo(), transferencia.getRecurso(), transferencia.getMunicipio(), transferencia.getDelegacion(),
                                            transferencia.getRelLaboral(), transferencia.getImporte() * -1);

                                    if (!bolGrabo) {
                                        objMensaje.setMensaje("Error al insertar el c\u00f3digo del momento contable. Transferencia R " + i);
                                    }

                                    i++;

                                }
                            } else {
                                objMensaje.setMensaje("No hay registros para insertar c\u00f3digos en la transferencia R.");
                                bolGrabo = false;
                            }

                            listTransferenciaAccionReq = getTransferenciaAccionReqByFolioMomentos(oficio, tipoOficio, year);
                            if (listTransferenciaAccionReq != null && listTransferenciaAccionReq.size() > 0) {
                                Iterator iteratorRecibe = (Iterator) listTransferenciaAccionReq.iterator();

                                while (bolGrabo && iteratorRecibe.hasNext()) {
                                    TransferenciaAccionReq transferenciaAccionReq = (TransferenciaAccionReq) iteratorRecibe.next();

                                    concepto = oficio + "-" + tipoMov + "-" + "A";

                                    bolGrabo = super.getResultSQLInsertBuzonMomentosCod(objParametro.getEmpresaCG(), calFecha.get(Calendar.YEAR), poliza,
                                            objParametro.getOrigenPolMCCG(), i, idTramite, concepto, transferenciaAccionReq.getRamo(), transferenciaAccionReq.getDepto(),
                                            transferenciaAccionReq.getFinalidad(), transferenciaAccionReq.getFuncion(), transferenciaAccionReq.getSubfuncion(), transferenciaAccionReq.getPrgConac(),
                                            transferenciaAccionReq.getPrograma(), transferenciaAccionReq.getTipoProy(), transferenciaAccionReq.getProy(), transferenciaAccionReq.getMeta(),
                                            transferenciaAccionReq.getAccion(), transferenciaAccionReq.getPartida(), transferenciaAccionReq.getTipoGasto(), transferenciaAccionReq.getFuente(),
                                            transferenciaAccionReq.getFondo(), transferenciaAccionReq.getRecurso(), transferenciaAccionReq.getMunicipio(), transferenciaAccionReq.getDelegacion(),
                                            transferenciaAccionReq.getRelLaboral(), transferenciaAccionReq.getImporte());

                                    if (!bolGrabo) {
                                        objMensaje.setMensaje("Error al insertar el c\u00f3digo del momento contable. Transferencia A " + i);
                                    }

                                    i++;
                                }
                            } else {
                                objMensaje.setMensaje("No hay registros para insertar c\u00f3digos en la transferencia A.");
                                bolGrabo = false;
                            }
                        }

                        if (bolGrabo) {
                            if (bolTraspasoPol) {
                                if (traspasarPoliza(objParametro.getEmpresaCG(), calFecha.get(Calendar.YEAR), poliza,
                                        objParametro.getOrigenPolMCCG())) {
                                    bolResultado = true;
                                } else {
                                    objMensaje.setMensaje("Error al traspazar la p\u00f3liza");
                                }
                            } else {
                                bolResultado = true;
                            }
                        }
                    } else {
                        objMensaje.setMensaje("Error al actualizar el ID del tr\u00e1mite");
                    }
                } else {
                    objMensaje.setMensaje("No existe configuraci\u00f3n de momentos contables");
                }
            } else {
                objMensaje.setMensaje("Error al insertar en el buz\u00f3n de momentos contables");
            }

        } catch (SQLException sql) {
            objMensaje.setMensaje("Error inesperado de sql al generar momentos contables");
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        } catch (Exception ex) {
            objMensaje.setMensaje("Error inesperado al generar momentos contables");
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return bolResultado;
    }

    public boolean traspasarPoliza(int empresa, int ejercicio, long poliza, int origen) throws SQLException {
        boolean bolGrabo = true;
        boolean bolResultado = false;
        int registros = 0;
        String mensaje = new String();
        try {
            registros = super.getResultSQLValidaPolizaCuadrada(empresa, ejercicio, poliza, origen, mensaje);
            if (registros > 0) {
                if (super.getResultSQLExistePoliza(empresa, ejercicio, poliza, origen) == 0) {
                    bolGrabo = super.getResultSQLTraspasaEncabezadoPoliza(empresa, ejercicio, poliza, origen);
                }
                if (bolGrabo) {
                    bolGrabo = super.getResultSQLUpdateRegistrosEncabezadoPoliza(empresa, ejercicio, poliza, origen, registros);
                    if (bolGrabo) {
                        if (super.getResultSQLTraspasaDetallePoliza(empresa, ejercicio, poliza, origen)) {
                            bolResultado = true;
                        }
                    }
                }

            }

        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        }

        return bolResultado;
    }

    public List<Movimiento> getMovimientoByRamoUsr(int year, String appLogin, String ramo) throws SQLException {
        List<Movimiento> movimientoList = new ArrayList<Movimiento>();
        try {

            movimientoList = super.getResultMovimientoByRamoUsr(year, appLogin, ramo);

        } catch (SQLException sql) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        } catch (IOException io) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(io, new Throwable());
            bitacora.grabaBitacora();
        }
        return movimientoList;
    }

    public boolean getCancelaRechazaTransferencia(int oficio, String tipoUsr, String appLogin, String motivo, String estatusActual,
            String estatusNvo, int tipoFlujo, MovimientosTransferencia movTransferencia, MensajeError objMensaje) throws SQLException {
        Movimiento objMovimiento;
        boolean bolResultado = false;
        boolean bolGrabo = true;
        Calendar calFechaReduccion = new GregorianCalendar();
        Iterator iterator = null;
        Transferencia transferencia = null;
        String tipoOficio = "A";
        Calendar calFechaMovtoPpto = new GregorianCalendar();
        Calendar calFechaActual = new GregorianCalendar();
        String strFechaActual = "";
        Fechas objFechas = new Fechas();

        try {
            objMovimiento = super.getResultMovimientoByFolio(oficio);

            calFechaActual.setTime(super.getResultSQLgetServerDate());
            if (objMovimiento.getFecPPTO() != null) {
                calFechaMovtoPpto.setTime(objMovimiento.getFecPPTO());
            } else {
                calFechaMovtoPpto.setTime(objMovimiento.getFechaElab());
            }

            if (super.getResultSQLUpdateEstatusMotivoMov(estatusNvo, motivo, oficio)) {
                if (!estatusActual.equals("X")) {
                    bolGrabo = super.getResultSQLUpdateEstatusTipoOficio(estatusNvo, oficio);
                } else {
                    bolGrabo = true;
                }
                if (bolGrabo) {
                    if (super.getResultSQLUpdateEstatusTransferencia(estatusNvo, oficio)) {

                        if (calFechaActual.get(Calendar.YEAR) > calFechaMovtoPpto.get(Calendar.YEAR)) {
                            calFechaActual.set(calFechaMovtoPpto.get(Calendar.YEAR), 11, 31);//11 es Diciembre
                            strFechaActual = objFechas.getFechaFormato(calFechaActual, Fechas.FORMATO_CORTO);
                            bolGrabo = super.getResultInsertBitMovto(oficio, appLogin, estatusNvo, "N", tipoOficio, "", tipoFlujo, tipoUsr, strFechaActual);
                        } else {
                            bolGrabo = super.getResultInsertBitMovto(oficio, appLogin, estatusNvo, "N", tipoOficio, "", tipoFlujo, tipoUsr);
                        }

                        if (bolGrabo) {

                            if (!estatusActual.equals("X") && !estatusActual.equals("R")) {
                                //Regresa el valor tomado del ppto en las reducciones
                                iterator = movTransferencia.getTransferenciaList().iterator();
                                while (bolGrabo && iterator.hasNext()) {
                                    transferencia = (Transferencia) iterator.next();

                                    //Se afecta el ACTUALIZADO en PPTO 
                                    calFechaReduccion.setTime(objMovimiento.getFecPPTO());
                                    bolGrabo = super.getResultSQLupdatePPTOreduccion(transferencia.getImporte(), calFechaReduccion.get(Calendar.YEAR),
                                            transferencia.getRamo(), transferencia.getDepto(), transferencia.getFinalidad(), transferencia.getFuncion(),
                                            transferencia.getSubfuncion(), transferencia.getPrgConac(), transferencia.getPrograma(), transferencia.getTipoProy(),
                                            transferencia.getProyecto(), transferencia.getMeta(), transferencia.getAccion(), transferencia.getPartida(),
                                            transferencia.getTipoGasto(), transferencia.getFuente(), transferencia.getFondo(), transferencia.getRecurso(),
                                            transferencia.getMunicipio(), transferencia.getDelegacion(), transferencia.getRelLaboral(),
                                            calFechaReduccion.get(Calendar.MONTH) + 1);

                                    if (!bolGrabo) {
                                        objMensaje.setMensaje("Error al devolver el importe al presupuesto");
                                    }

                                }
                            }

                            if (bolGrabo) {
                                if (getProcesosTipoOficioByOficio(oficio, appLogin, motivo, tipoFlujo, tipoUsr, "T", tipoOficio, objMensaje, new ArrayList<Meta>(), new ArrayList<Accion>())) {
                                    bolResultado = true;
                                }
                            }
                        } else {
                            objMensaje.setMensaje("Error al insertar en la bit\u00e1cora del movimiento");
                        }
                    } else {
                        objMensaje.setMensaje("Error al actualizar el estatus de transferencias");
                    }
                } else {
                    objMensaje.setMensaje("Error al actualizar el estatus del tipo de oficio");
                }
            } else {
                objMensaje.setMensaje("Error al actualizar el estatus del movimiento");
            }
        } catch (SQLException sql) {
            objMensaje.setMensaje("Error inesperado de sql al cancelar/rechazar la transferencia");
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        } catch (Exception ex) {
            objMensaje.setMensaje("Error inesperado al cancelar/rechazar la transferencia");
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return bolResultado;
    }

    public boolean getCancelaRechazaTransferenciaTipoOficio(int oficio, String tipoUsr, String appLogin, String motivo, String tipoOficio, String estatusMov,
            int tipoFlujo, MovimientosTransferencia movTransferencia, MensajeError objMensaje) {
        TipoOficio objMovimiento;
        boolean bolResultado = false;
        boolean bolGrabo = true;
        Calendar calFechaReduccion = new GregorianCalendar();
        Calendar calFechaMovtoPpto = new GregorianCalendar();
        Calendar calFechaActual = new GregorianCalendar();
        String strFechaActual = "";
        Fechas objFechas = new Fechas();

        try {
            objMovimiento = super.getResultMovimientoByFolioTipoOficio(oficio, tipoOficio);

            calFechaActual.setTime(super.getResultSQLgetServerDate());
            calFechaMovtoPpto.setTime(objMovimiento.getFecPPTO());

            if (super.getResultSQLUpdateEstatusMotivoMovTipoOficio(estatusMov, motivo, oficio, tipoOficio)) {
                if (super.getResultSQLUpdateEstatusTransferenciaTipoOficio(estatusMov, oficio, tipoOficio)) {

                    if (calFechaActual.get(Calendar.YEAR) > calFechaMovtoPpto.get(Calendar.YEAR)) {
                        calFechaActual.set(calFechaMovtoPpto.get(Calendar.YEAR), 11, 31);//11 es Diciembre
                        strFechaActual = objFechas.getFechaFormato(calFechaActual, Fechas.FORMATO_CORTO);
                        bolGrabo = super.getResultInsertBitMovto(oficio, appLogin, estatusMov, "N", tipoOficio, "", tipoFlujo, tipoUsr, strFechaActual);
                    } else {
                        bolGrabo = super.getResultInsertBitMovto(oficio, appLogin, estatusMov, "N", tipoOficio, "", tipoFlujo, tipoUsr);
                    }

                    if (bolGrabo) {

                        //Regresa el valor tomado del ppto en las reducciones
                        Iterator iterator = (Iterator) movTransferencia.getTransferenciaList().iterator();
                        while (bolGrabo && iterator.hasNext()) {
                            Transferencia transferencia = (Transferencia) iterator.next();

                            //Se afecta el ACTUALIZADO en PPTO 
                            calFechaReduccion.setTime(objMovimiento.getFecPPTO());
                            bolGrabo = super.getResultSQLupdatePPTOreduccion(transferencia.getImporte(), calFechaReduccion.get(Calendar.YEAR),
                                    transferencia.getRamo(), transferencia.getDepto(), transferencia.getFinalidad(), transferencia.getFuncion(),
                                    transferencia.getSubfuncion(), transferencia.getPrgConac(), transferencia.getPrograma(), transferencia.getTipoProy(),
                                    transferencia.getProyecto(), transferencia.getMeta(), transferencia.getAccion(), transferencia.getPartida(),
                                    transferencia.getTipoGasto(), transferencia.getFuente(), transferencia.getFondo(), transferencia.getRecurso(),
                                    transferencia.getMunicipio(), transferencia.getDelegacion(), transferencia.getRelLaboral(),
                                    calFechaReduccion.get(Calendar.MONTH) + 1);

                            if (!bolGrabo) {
                                objMensaje.setMensaje("Error al devolver el importe al presupuesto");
                            }

                        }

                        if (bolGrabo) {
                            if (getProcesosTipoOficioByOficio(oficio, appLogin, motivo, tipoFlujo, tipoUsr, "T", tipoOficio, objMensaje, new ArrayList<Meta>(), new ArrayList<Accion>())) {
                                bolResultado = true;
                            }
                        }
                    } else {
                        objMensaje.setMensaje("Error al insertar en la bit\u00e1cora del movimiento");
                    }
                } else {
                    objMensaje.setMensaje("Error al actualizar el estatus de transferencias");
                }
            } else {
                objMensaje.setMensaje("Error al actualizar el estatus del tipo de oficio");
            }

        } catch (SQLException sql) {
            objMensaje.setMensaje("Error inesperado de sql al cancelar/rechazar la transferencia por tipo de oficio");
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        } catch (Exception ex) {
            objMensaje.setMensaje("Error inesperado al cancelar/rechazar la transferencia por tipo de oficio");
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return bolResultado;
    }

    public boolean getAutorizaMovTransferencia(int year, int oficio, String tipoUsr, String appLogin,
            boolean capturaEspecial, int tipoFlujo, MovimientosTransferencia movTransferencia, MensajeError objMensaje) throws SQLException {
        Movimiento objMovimiento;
        EstatusMov objEstatusMov;
        List<FlujoFirmas> flujoFirmasList = new ArrayList<FlujoFirmas>();
        boolean bolFirmasCompletas = false;
        boolean resultado = false;
        boolean bolGrabo = false;
        Calendar calFechaMovtoPpto = new GregorianCalendar();
        Calendar calFechaActual = new GregorianCalendar();
        String strFechaPpto = new String();
        String strFechaActual = new String();
        Fechas objFechas = new Fechas();

        try {

            objMovimiento = super.getResultMovimientoByFolio(oficio);
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion("Folio en método: " + objMovimiento.getOficio());
            bitacora.grabaBitacora();
            calFechaActual.setTime(super.getResultSQLgetServerDate());
            calFechaMovtoPpto.setTime(objMovimiento.getFecPPTO());
            strFechaPpto = objFechas.getFechaFormato(calFechaMovtoPpto, Fechas.FORMATO_CORTO);

            if (capturaEspecial) {
                if (super.getResultInsertBitMovto(oficio, appLogin, "A", "N", "A", "", tipoFlujo, tipoUsr, strFechaPpto)) {
                    bolGrabo = true;
                } else {
                    objMensaje.setMensaje("Error al insertar la autorizaci\u00f3n (captura especial) en la bit\u00e1cora del movimiento.");
                }

            } else if (calFechaActual.get(Calendar.YEAR) > calFechaMovtoPpto.get(Calendar.YEAR)) {
                calFechaActual.set(calFechaMovtoPpto.get(Calendar.YEAR), 11, 31);//11 es Diciembre
                strFechaActual = objFechas.getFechaFormato(calFechaActual, Fechas.FORMATO_CORTO);
                if (super.getResultInsertBitMovto(oficio, appLogin, "A", "N", "A", "", tipoFlujo, tipoUsr, strFechaActual)) {
                    bolGrabo = true;
                } else {
                    objMensaje.setMensaje("Error al insertar la autorizaci\u00f3n (fecha actual) en la bit\u00e1cora del movimiento.");
                }

            } else {
                if (super.getResultInsertBitMovto(oficio, appLogin, "A", "N", "A", "", tipoFlujo, tipoUsr)) {
                    bolGrabo = true;
                } else {
                    objMensaje.setMensaje("Error al insertar la autorizaci\u00f3n en la bit\u00e1cora del movimiento.");
                }
            }

            if (bolGrabo) {

                //Se cambia el estatus del movimiento cuando cumple con las firmas del flujo
                flujoFirmasList = super.getResultUsuariosAFirmar(tipoFlujo, tipoUsr);

                if (flujoFirmasList.size() > 0) {

                    for (FlujoFirmas flujoFirmas : flujoFirmasList) {

                        if (super.getResultValidaFirma(oficio, tipoFlujo, flujoFirmas.getAppLogin())) {
                            bolFirmasCompletas = true;
                        }
                    }

                    if (bolFirmasCompletas) {
                        bitacora.setStrUbicacion(getStrUbicacion());
                        bitacora.setStrServer(getStrServer());
                        bitacora.setITipoBitacora(3);
                        bitacora.setStrInformacion(objMovimiento.getOficio() + " Parametros antes de método\n tipoMov : " + objMovimiento.getTipoMovimiento() + "\n tipoUsr: " + tipoUsr + "\n estatus: " + objMovimiento.getStatus());
                        bitacora.grabaBitacora();
                        objEstatusMov = super.getResultEstatusSiguiente(objMovimiento.getTipoMovimiento(), tipoUsr, objMovimiento.getStatus());

                        if (super.getResultSQLUpdateEstatusMov(objEstatusMov.getEstatusMovId(), oficio)) {
                            if (super.getResultSQLUpdateEstatusTipoOficio(objEstatusMov.getEstatusMovId(), oficio)) {
                                if (super.getResultSQLUpdateEstatusTransferencia(objEstatusMov.getEstatusMovId(), oficio)) {
                                    resultado = true;
                                } else {
                                    objMensaje.setMensaje("Error al actualizar el estatus de la transferencia.");
                                }
                            } else {
                                objMensaje.setMensaje("Error al actualizar el estatus del tipo de oficio.");
                            }
                        } else {
                            objMensaje.setMensaje("Error al actualizar el estatus del movimiento.");
                        }
                    } else {
                        resultado = true;
                    }
                }

            }

        } catch (SQLException sql) {
            objMensaje.setMensaje("Error inesperado de sql al autorizar la transferencia");
            resultado = false;
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        } catch (Exception ex) {
            objMensaje.setMensaje("Error inesperado al autorizar la transferencia");
            resultado = false;
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }

        return resultado;
    }

    public boolean getAutorizaMovTransferenciaTipoOficio(int year, int oficio, String tipoOficio, String tipoUsr, String appLogin,
            boolean capturaEspecial, int tipoFlujo, MovimientosTransferencia movTransferencia, MensajeError objMensaje) {
        TipoOficio objMovimiento;
        EstatusMov objEstatusMov;
        List<FlujoFirmas> flujoFirmasList = new ArrayList<FlujoFirmas>();
        List<TransferenciaAccion> movAccionList = new ArrayList<TransferenciaAccion>();
        List<TransferenciaMeta> movMetaList = new ArrayList<TransferenciaMeta>();
        List<Meta> metaList = new ArrayList<Meta>();
        List<Accion> accionList = new ArrayList<Accion>();

        boolean bolFirmasCompletas = false;
        boolean bolResultado = false;
        boolean bolGrabo = false;
        int metaId = 0;
        int accionId = 0;
        int requerimientoId = 0;
        double estimacion = 0.0;

        Calendar calFechaMovtoPpto = new GregorianCalendar();
        Calendar calFechaActual = new GregorianCalendar();
        String strFechaPpto = "";
        String strFechaActual = "";
        Fechas objFechas = new Fechas();
        boolean isActual = true;

        Accion accionTemp;
        Meta metaTemp;

        try {

            objMovimiento = super.getResultMovimientoByFolioTipoOficio(oficio, tipoOficio);

            calFechaActual.setTime(super.getResultSQLgetServerDate());
            calFechaMovtoPpto.setTime(objMovimiento.getFecPPTO());
            strFechaPpto = objFechas.getFechaFormato(calFechaMovtoPpto, Fechas.FORMATO_CORTO);
            movAccionList = movTransferencia.getTransferenciaAccionList();
            movMetaList = movTransferencia.getTransferenciaMetaList();

            for (TransferenciaMeta meta : movMetaList) {
                metaTemp = new Meta();
                estimacion = 0.0;
                for (MovOficioEstimacion metaEstimacion : meta.getMovOficioEstimacion()) {
                    estimacion += metaEstimacion.getValor();
                }
                if (estimacion == 0.0) {
                    if (meta.getMovOficioMeta().getNvaCreacion().equals("N")) {
                        if (this.validaMetaInhabilitada(year,
                                meta.getMovOficioMeta().getRamoId(),
                                meta.getMovOficioMeta().getMetaId())) {
                            metaTemp.setYear(year);
                            metaTemp.setRamo(meta.getMovOficioMeta().getRamoId());
                            metaTemp.setMetaId(meta.getMovOficioMeta().getMetaId());
                            metaList.add(metaTemp);
                        }
                    }
                }
            }
            for (TransferenciaAccion accion : movAccionList) {
                accionTemp = new Accion();
                estimacion = 0.0;
                for (MovOficioAccionEstimacion accionEstimacion : accion.getMovOficioAccionEstList()) {
                    estimacion += accionEstimacion.getValor();
                }
                if (estimacion == 0.0) {
                    if (accion.getMovOficioAccion().getNvaCreacion().equals("N")) {
                        if (this.validaAccionInhabilitada(year,
                                accion.getMovOficioAccion().getRamoId(),
                                accion.getMovOficioAccion().getMetaId(),
                                accion.getMovOficioAccion().getAccionId())) {
                            accionTemp.setYear(year);
                            accionTemp.setRamo(accion.getMovOficioAccion().getRamoId());
                            accionTemp.setMeta(accion.getMovOficioAccion().getMetaId());
                            accionTemp.setAccionId(accion.getMovOficioAccion().getAccionId());
                            accionList.add(accionTemp);
                        }
                    }
                }
            }
            if (capturaEspecial) {
                calFechaActual = calFechaMovtoPpto;
                if (super.getResultInsertBitMovto(oficio, appLogin, "A", "N", tipoOficio, "", tipoFlujo, tipoUsr, strFechaPpto)) {
                    bolGrabo = true;
                } else {
                    objMensaje.setMensaje("Error al insertar la autorizaci\u00f3n (captura especial) en la bit\u00e1cora del movimiento.");
                }
            } else if (calFechaActual.get(Calendar.YEAR) > calFechaMovtoPpto.get(Calendar.YEAR)) {
                isActual = false;
                calFechaActual.set(calFechaMovtoPpto.get(Calendar.YEAR), 11, 31);//11 es Diciembre
                strFechaActual = objFechas.getFechaFormato(calFechaActual, Fechas.FORMATO_CORTO);
                if (super.getResultInsertBitMovto(oficio, appLogin, "A", "N", tipoOficio, "", tipoFlujo, tipoUsr, strFechaActual)) {
                    bolGrabo = true;
                } else {
                    objMensaje.setMensaje("Error al insertar la autorizaci\u00f3n (fecha actual) en la bit\u00e1cora del movimiento.");
                }
            } else {
                if (super.getResultInsertBitMovto(oficio, appLogin, "A", "N", tipoOficio, "", tipoFlujo, tipoUsr)) {
                    bolGrabo = true;
                } else {
                    objMensaje.setMensaje("Error al insertar la autorizaci\u00f3n en la bit\u00e1cora del movimiento.");
                }
            }

            if (bolGrabo) {

                //Se cambia el estatus del movimiento cuando cumple con las firmas del flujo
                flujoFirmasList = super.getResultUsuariosAFirmar(tipoFlujo, tipoUsr);

                if (flujoFirmasList.size() > 0) {

                    for (FlujoFirmas flujoFirmas : flujoFirmasList) {

                        if (super.getResultValidaFirma(oficio, tipoFlujo, flujoFirmas.getAppLogin())) {
                            bolFirmasCompletas = true;
                        }
                    }

                    if (bolFirmasCompletas) {
                        bitacora.setStrUbicacion(getStrUbicacion());
                        bitacora.setStrServer(getStrServer());
                        bitacora.setITipoBitacora(3);
                        bitacora.setStrInformacion(objMovimiento.getOficio() + " Parametros antes de método\n tipoMov : " + objMovimiento.getTipoMovimiento() + "\n tipoUsr: " + tipoUsr + "\n estatus: " + objMovimiento.getStatus());
                        bitacora.grabaBitacora();
                        objEstatusMov = super.getResultEstatusSiguiente(objMovimiento.getTipoMovimiento(), tipoUsr, objMovimiento.getStatus());

                        if (objEstatusMov.getEstatusMovId().equals("A")) {

                            if (movTransferencia.getTransferenciaList().size() == 0) {
                                bolGrabo = false;
                                objMensaje.setMensaje("Error en el listado de transferencias.");
                            }

                            Iterator iterator = (Iterator) movTransferencia.getTransferenciaList().iterator();
                            while (bolGrabo && iterator.hasNext()) {
                                Transferencia transferencia = (Transferencia) iterator.next();
                                Iterator iteratorRecibe = (Iterator) transferencia.getTransferenciaAccionReqList().iterator();

                                if (transferencia.getTransferenciaAccionReqList().size() == 0) {
                                    bolGrabo = false;
                                    objMensaje.setMensaje("Error en el listado de codigos que reciben. Transferencia " + transferencia.getTipoOficio() + " " + transferencia.getConsec());
                                }

                                while (bolGrabo && iteratorRecibe.hasNext()) {
                                    TransferenciaAccionReq transferenciaAccionReq = (TransferenciaAccionReq) iteratorRecibe.next();

                                    //Inserta en meta las metas nuevas relacionadas al req autorizado en caso de existir (se actualiza movoficios_meta
                                    //con el id generado para que no se vuelva a insertar)
                                    if (bolGrabo && super.getResultSQLGetValidaMetasNuevasFromMovoficios(oficio, year, transferenciaAccionReq.getRamo(), transferenciaAccionReq.getMeta())) {
                                        metaId = super.getMaxMeta(year, transferenciaAccionReq.getRamo());
                                        bolGrabo = super.getResultSQLInsertMetaFromMovoficios(oficio, year, transferenciaAccionReq.getRamo(), transferenciaAccionReq.getMeta(), metaId, tipoOficio);
                                        if (bolGrabo) {
                                            bolGrabo = super.getResultSQLInsertEstimacionFromMovoficios(oficio, year, transferenciaAccionReq.getRamo(), transferenciaAccionReq.getMeta(), metaId);
                                            if (bolGrabo) {
                                                bolGrabo = super.getResultSQLUpdateMovoficiosMeta(metaId, oficio, year, transferenciaAccionReq.getRamo(), transferenciaAccionReq.getMeta());
                                                if (bolGrabo) {
                                                    bolGrabo = super.getResultSQLUpdateMovoficiosMetaAccion(metaId, oficio, year, transferenciaAccionReq.getRamo(), transferenciaAccionReq.getMeta());
                                                    if (bolGrabo) {
                                                        bolGrabo = super.getResultSQLUpdateMovoficiosMetaAccionEstimacion(metaId, oficio, year, transferenciaAccionReq.getRamo(), transferenciaAccionReq.getMeta());
                                                        if (bolGrabo) {
                                                            bolGrabo = super.getResultSQLUpdateMetaTransfrec(oficio, transferenciaAccionReq.getRamo(), metaId, transferenciaAccionReq.getMeta());
                                                            if (bolGrabo) {
                                                                bolGrabo = super.getResultSQLUpdateMovoficiosMetaAccionReq(metaId, oficio, year, transferenciaAccionReq.getRamo(), transferenciaAccionReq.getMeta());
                                                                if (bolGrabo) {
                                                                    for (Transferencia transferenciaAux : movTransferencia.getTransferenciaList()) {
                                                                        for (TransferenciaAccionReq transferenciaAccReqAux : transferenciaAux.getTransferenciaAccionReqList()) {
                                                                            if (transferenciaAccReqAux.getRamo().equals(transferenciaAccionReq.getRamo())
                                                                                    && transferenciaAccReqAux.getMeta() == transferenciaAccionReq.getMeta()) {
                                                                                transferenciaAccReqAux.setbMetaInsertada(true);
                                                                                transferenciaAccReqAux.setMetaAux(metaId);
                                                                            }
                                                                        }
                                                                    }
                                                                } else {
                                                                    objMensaje.setMensaje("Error al actualizar la meta en el requerimiento. Meta " + transferenciaAccionReq.getMeta() + ", ramo " + transferenciaAccionReq.getRamo() + ".");
                                                                }
                                                            } else {
                                                                objMensaje.setMensaje("Error al actualizar la meta en el c\u00f3digo que recibe. Meta " + transferenciaAccionReq.getMeta() + ", ramo " + transferenciaAccionReq.getRamo() + ".");
                                                            }
                                                        } else {
                                                            objMensaje.setMensaje("Error al actualizar la meta en la estimaci\u00f3n del movimiento acci\u00f3n. Meta " + transferenciaAccionReq.getMeta() + ", ramo " + transferenciaAccionReq.getRamo() + ".");
                                                        }
                                                    } else {
                                                        objMensaje.setMensaje("Error al actualizar la meta en el movimiento acci\u00f3n. Meta " + transferenciaAccionReq.getMeta() + ", ramo " + transferenciaAccionReq.getRamo() + ".");
                                                    }
                                                } else {
                                                    objMensaje.setMensaje("Error al actualizar la meta en el movimiento. Meta " + transferenciaAccionReq.getMeta() + ", ramo " + transferenciaAccionReq.getRamo() + ".");
                                                }
                                            } else {
                                                objMensaje.setMensaje("Error al insertar la estimaci\u00f3n de la meta " + transferenciaAccionReq.getMeta() + ", ramo " + transferenciaAccionReq.getRamo() + ".");
                                            }
                                        } else {
                                            objMensaje.setMensaje("Error al insertar la meta " + transferenciaAccionReq.getMeta() + ", ramo " + transferenciaAccionReq.getRamo() + ".");
                                        }
                                    } else {
                                        if (transferenciaAccionReq.isbMetaInsertada()) {
                                            metaId = transferenciaAccionReq.getMetaAux();
                                        } else {
                                            metaId = transferenciaAccionReq.getMeta();
                                        }
                                    }

                                    //Inserta en accion las acciones nuevas relacionadas al req autorizado en caso de existir(se actualiza movoficios_accion
                                    //con el id generado para que no se vuelva a insertar)
                                    if (bolGrabo && super.getResultSQLGetValidaAccionesNuevasFromMovoficios(oficio, year, transferenciaAccionReq.getRamo(), metaId, transferenciaAccionReq.getAccion())) {
                                        accionId = super.getResultSQLMaxAccion(year, transferenciaAccionReq.getRamo(), metaId);
                                        bolGrabo = super.getResultSQLInsertAccionFromMovoficios(oficio, year, transferenciaAccionReq.getRamo(), metaId, transferenciaAccionReq.getAccion(), accionId, tipoOficio);
                                        if (bolGrabo) {
                                            bolGrabo = super.getResultSQLInsertAccionEstimacionFromMovoficios(oficio, year, transferenciaAccionReq.getRamo(), metaId, transferenciaAccionReq.getAccion(), accionId);
                                            if (bolGrabo) {
                                                bolGrabo = super.getResultSQLUpdateMovoficiosAccion(accionId, oficio, year, transferenciaAccionReq.getRamo(), metaId, transferenciaAccionReq.getAccion());
                                                if (bolGrabo) {
                                                    bolGrabo = super.getResultSQLUpdateAccionTransfrec(oficio, transferenciaAccionReq.getRamo(), metaId, accionId, transferenciaAccionReq.getAccion());
                                                    if (bolGrabo) {
                                                        bolGrabo = super.getResultSQLUpdateMovoficiosAccionReq(accionId, oficio, year, transferenciaAccionReq.getRamo(), metaId, transferenciaAccionReq.getAccion());
                                                        if (bolGrabo) {
                                                            for (Transferencia transferenciaAux : movTransferencia.getTransferenciaList()) {
                                                                for (TransferenciaAccionReq transferenciaAccReqAux : transferenciaAux.getTransferenciaAccionReqList()) {
                                                                    if (transferenciaAccReqAux.getRamo().equals(transferenciaAccionReq.getRamo())
                                                                            && transferenciaAccReqAux.getMeta() == transferenciaAccionReq.getMeta()
                                                                            && transferenciaAccReqAux.getAccion() == transferenciaAccionReq.getAccion()) {
                                                                        transferenciaAccReqAux.setbAccionInsertada(true);
                                                                        //transferenciaAccReqAux.setMetaAux(metaId);
                                                                        transferenciaAccReqAux.setAccionAux(accionId);
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            objMensaje.setMensaje("Error al actualizar la acci\u00f3n en el requerimiento. Acci\u00f3n " + transferenciaAccionReq.getAccion() + ", meta " + transferenciaAccionReq.getMeta() + ", ramo " + transferenciaAccionReq.getRamo() + ".");
                                                        }
                                                    } else {
                                                        objMensaje.setMensaje("Error al actualizar la acci\u00f3n en el c\u00f3digo que recibe. Acci\u00f3n " + transferenciaAccionReq.getAccion() + ", meta " + transferenciaAccionReq.getMeta() + ", ramo " + transferenciaAccionReq.getRamo() + ".");
                                                    }
                                                } else {
                                                    objMensaje.setMensaje("Error al actualizar la acci\u00f3n en el movimiento. Acci\u00f3n " + transferenciaAccionReq.getAccion() + ", meta " + transferenciaAccionReq.getMeta() + ", ramo " + transferenciaAccionReq.getRamo() + ".");
                                                }
                                            } else {
                                                objMensaje.setMensaje("Error al insertar la estimaci\u00f3n de la acci\u00f3n " + transferenciaAccionReq.getAccion() + ", meta " + transferenciaAccionReq.getMeta() + ", ramo " + transferenciaAccionReq.getRamo() + ".");
                                            }
                                        } else {
                                            objMensaje.setMensaje("Error al insertar la acci\u00f3n " + transferenciaAccionReq.getAccion() + ", meta " + transferenciaAccionReq.getMeta() + ", ramo " + transferenciaAccionReq.getRamo() + ".");
                                        }
                                    } else {
                                        if (transferenciaAccionReq.isbMetaInsertada()) {
                                            metaId = transferenciaAccionReq.getMetaAux();
                                        } else {
                                            metaId = transferenciaAccionReq.getMeta();
                                        }
                                        if (transferenciaAccionReq.isbAccionInsertada()) {
                                            accionId = transferenciaAccionReq.getAccionAux();
                                        } else {
                                            accionId = transferenciaAccionReq.getAccion();
                                        }
                                    }

                                    if (bolGrabo && transferenciaAccionReq.getTipoMovTransf().equals("C")) {

                                        bolGrabo = super.getRestulSQLGetFuncionCreaCodigo(year, transferenciaAccionReq.getRamo(), transferenciaAccionReq.getDepto(),
                                                transferenciaAccionReq.getFinalidad(), transferenciaAccionReq.getFuncion(), transferenciaAccionReq.getSubfuncion(),
                                                transferenciaAccionReq.getPrgConac(), transferenciaAccionReq.getPrograma(), transferenciaAccionReq.getTipoProy(),
                                                transferenciaAccionReq.getProy(), metaId, accionId, transferenciaAccionReq.getPartida(), transferenciaAccionReq.getTipoGasto(),
                                                transferenciaAccionReq.getFuente(), transferenciaAccionReq.getFondo(), transferenciaAccionReq.getRecurso(),
                                                transferenciaAccionReq.getMunicipio(), transferenciaAccionReq.getDelegacion(), transferenciaAccionReq.getRelLaboral());

                                        if (!bolGrabo) {
                                            objMensaje.setMensaje("Error al crear el c\u00f3digo para la transferencia.");
                                        }
                                    }

                                    //si hay meses calendarizados menores al actual se suman esos importes y se pasan
                                    //al actual, incluyendo años anteriores con meses menores al 31 de diciembre 
                                    //afectando los registros de MOVOFICIOS_ACCION_REQ
                                    if (bolGrabo) {
                                        if (!capturaEspecial && !super.getResultSQLisParaestatal()) {
                                            bolGrabo = super.getResultSQLAjustarAcumuladoMesesAnteriores(transferenciaAccionReq.getMovOficioAccionReq(), metaId, accionId, calFechaActual.get(Calendar.MONTH) + 1);
                                            if (!bolGrabo) {
                                                objMensaje.setMensaje("Error al ajustar el acumulado de meses anteriores.");
                                            }
                                        }
                                        //Inserta en accion_req los requerimientos del tipoficio autorizado
                                        if (bolGrabo) {
                                            requerimientoId = super.getResultSQLMaxRequerimiento(year, transferenciaAccionReq.getRamo(), metaId, accionId);
                                            bolGrabo = super.getResultSQLInsertRequerimientoFromMovoficios(requerimientoId, oficio, year, transferenciaAccionReq.getRamo(), transferenciaAccionReq.getPrograma(),
                                                    transferenciaAccionReq.getDepto(), metaId, accionId, transferenciaAccionReq.getMovOficioAccionReq().getRequerimiento());
                                            if (bolGrabo) {
                                                bolGrabo = super.getResultSQLUpdateMovoficiosRequerimiento(requerimientoId, oficio, year, transferenciaAccionReq.getRamo(), transferenciaAccionReq.getPrograma(),
                                                        transferenciaAccionReq.getDepto(), metaId, accionId, transferenciaAccionReq.getMovOficioAccionReq().getRequerimiento());

                                                if (bolGrabo) {
                                                    bolGrabo = super.getResultSQLUpdateRequerimientoTransfrec(oficio, transferenciaAccionReq.getRamo(), metaId, accionId, requerimientoId,
                                                            transferenciaAccionReq.getMovOficioAccionReq().getRequerimiento());

                                                    //Se afecta el ACTUALIZADO en PPTO 
                                                    if (bolGrabo) {
                                                        bolGrabo = getUpdatePPTOTransferencia(transferenciaAccionReq, metaId, accionId, requerimientoId);
                                                        if (!bolGrabo) {
                                                            objMensaje.setMensaje("Error al actualizar el presupuesto. Requerimiento " + transferenciaAccionReq.getMovOficioAccionReq().getRequerimiento() + ", acci\u00f3n " + transferenciaAccionReq.getAccion() + ", meta " + transferenciaAccionReq.getMeta() + ", ramo " + transferenciaAccionReq.getRamo() + ".");
                                                        }
                                                    } else {
                                                        objMensaje.setMensaje("Error al actualizar el c\u00f3digo que recibe. Requerimiento " + transferenciaAccionReq.getMovOficioAccionReq().getRequerimiento() + ", acci\u00f3n " + transferenciaAccionReq.getAccion() + ", meta " + transferenciaAccionReq.getMeta() + ", ramo " + transferenciaAccionReq.getRamo() + ".");
                                                    }
                                                } else {
                                                    objMensaje.setMensaje("Error al actualizar el movimiento. Requerimiento " + transferenciaAccionReq.getMovOficioAccionReq().getRequerimiento() + ", acci\u00f3n " + transferenciaAccionReq.getAccion() + ", meta " + transferenciaAccionReq.getMeta() + ", ramo " + transferenciaAccionReq.getRamo() + ".");
                                                }
                                            } else {
                                                objMensaje.setMensaje("Error al insertar el requerimiento " + transferenciaAccionReq.getMovOficioAccionReq().getRequerimiento() + ", acci\u00f3n " + transferenciaAccionReq.getAccion() + ", meta " + transferenciaAccionReq.getMeta() + ", ramo " + transferenciaAccionReq.getRamo() + ".");
                                            }
                                        }
                                    }
                                }

                            }

                            if (bolGrabo) {
                                if (!super.getResultSQLisParaestatal() || super.getResultSqlGetIsAyuntamiento()) {
                                    bolGrabo = generaMomentosContables(year, oficio, tipoOficio, isActual, calFechaActual, appLogin,
                                            "T", objMovimiento.getAppLogin(), objMensaje);

                                    if (!bolGrabo) {
                                        objMensaje.setMensaje("Error al generar momentos contables. " + objMensaje.getMensaje());
                                    }
                                }
                                if (bolGrabo) {
                                    bolGrabo = super.getResultSQLUpdateEstatusMovTipoOficio(objEstatusMov.getEstatusMovId(), oficio, tipoOficio);
                                    if (bolGrabo) {
                                        bolGrabo = super.getResultSQLUpdateEstatusTransferenciaTipoOficio(objEstatusMov.getEstatusMovId(), oficio, tipoOficio);
                                        if (bolGrabo) {
                                            if (getProcesosTipoOficioByOficio(oficio, appLogin, "", tipoFlujo, tipoUsr, "T", tipoOficio, objMensaje, metaList, accionList)) {
                                                bolResultado = true;
                                            }
                                        } else {
                                            objMensaje.setMensaje("Error al actualizar el estatus de la transferencia por tipo de oficio.");
                                        }
                                    } else {
                                        objMensaje.setMensaje("Error al actualizar el estatus del movimiento por tipo de oficio.");
                                    }
                                }
                            }

                        } else {
                            bolResultado = true;
                        }
                        //}
                    } else {
                        bolResultado = true;
                    }
                }

            }

        } catch (SQLException sql) {
            objMensaje.setMensaje("Error inesperado de sql al autorizar la transferencia por tipo de oficio");
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(sql, new Throwable());
            bitacora.grabaBitacora();
        } catch (Exception ex) {
            objMensaje.setMensaje("Error inesperado  al autorizar la transferencia por tipo de oficio");
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        } finally {
        }
        return bolResultado;
    }

    public boolean getUpdatePPTOTransferencia(TransferenciaAccionReq transferenciaAccionReq, int meta, int accion, int requerimiento) throws Exception, SQLException {
        boolean bolGrabo = true;
        boolean bolResultado = false;
        int contMes = 1;
        double cantXmes = 0;
        String mensaje = "";
        double costoUnitario = 0;

        costoUnitario = transferenciaAccionReq.getMovOficioAccionReq().getCostoUnitario();

        if (!super.getResultSQLisCodigoRepetido(transferenciaAccionReq.getMovOficioAccionReq().getYear(), transferenciaAccionReq.getRamo(), transferenciaAccionReq.getDepto(),
                transferenciaAccionReq.getFinalidad(), transferenciaAccionReq.getFuncion(), transferenciaAccionReq.getSubfuncion(), transferenciaAccionReq.getPrgConac(),
                transferenciaAccionReq.getPrograma(), transferenciaAccionReq.getTipoProy(), transferenciaAccionReq.getProy(), meta, accion, transferenciaAccionReq.getPartida(),
                transferenciaAccionReq.getTipoGasto(), transferenciaAccionReq.getFuente(), transferenciaAccionReq.getFondo(), transferenciaAccionReq.getRecurso(), transferenciaAccionReq.getMunicipio(),
                transferenciaAccionReq.getDelegacion(), transferenciaAccionReq.getRelLaboral())) {

            bolGrabo = super.getResultSQLInsertCodigo(transferenciaAccionReq.getMovOficioAccionReq().getYear(), transferenciaAccionReq.getRamo(), transferenciaAccionReq.getDepto(),
                    transferenciaAccionReq.getFinalidad(), transferenciaAccionReq.getFuncion(), transferenciaAccionReq.getSubfuncion(), transferenciaAccionReq.getPrgConac(),
                    transferenciaAccionReq.getPrograma(), transferenciaAccionReq.getTipoProy(), transferenciaAccionReq.getProy(), meta, accion, transferenciaAccionReq.getPartida(),
                    transferenciaAccionReq.getTipoGasto(), transferenciaAccionReq.getFuente(), transferenciaAccionReq.getFondo(), transferenciaAccionReq.getRecurso(), transferenciaAccionReq.getMunicipio(),
                    transferenciaAccionReq.getDelegacion(), transferenciaAccionReq.getRelLaboral());

        }

        while (bolGrabo && contMes <= 12) {

            mensaje = super.getRestulSQLCallAmpliacionCodPPTO(transferenciaAccionReq.getMovOficioAccionReq().getOficio(),
                    transferenciaAccionReq.getMovOficioAccionReq().getYear(), transferenciaAccionReq.getMovOficioAccionReq().getRamo(),
                    transferenciaAccionReq.getMovOficioAccionReq().getDepto(), transferenciaAccionReq.getFinalidad(), transferenciaAccionReq.getFuncion(),
                    transferenciaAccionReq.getSubfuncion(), transferenciaAccionReq.getPrgConac(), transferenciaAccionReq.getMovOficioAccionReq().getPrograma(),
                    transferenciaAccionReq.getTipoProy(), transferenciaAccionReq.getProy(), meta, accion, transferenciaAccionReq.getMovOficioAccionReq().getPartida(),
                    transferenciaAccionReq.getMovOficioAccionReq().getTipoGasto(), transferenciaAccionReq.getMovOficioAccionReq().getFuente(),
                    transferenciaAccionReq.getMovOficioAccionReq().getFondo(), transferenciaAccionReq.getMovOficioAccionReq().getRecurso(),
                    transferenciaAccionReq.getMunicipio(), transferenciaAccionReq.getDelegacion(), transferenciaAccionReq.getMovOficioAccionReq().getRelLaboral(),
                    requerimiento, contMes);

            if (!mensaje.equals("exito")) {
                bolGrabo = false;
            }

            contMes++;
        }

        if (bolGrabo) {
            bolResultado = true;
        }

        return bolResultado;
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

    public boolean validaMetaInhabilitada(int year, String ramo, int meta) {
        boolean isInhabilitado = false;
        try {
            if (super.getResultSLQgetSumaAsignadoActualizadoMeta(year, ramo, meta)) {
                if (super.getResultSLQgetEjercidoCompromisoMeta(year, ramo, meta)) {
                    isInhabilitado = super.getResultSQLisAvanceMetaCapturada(year, ramo, meta);
                }
            }
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return isInhabilitado;
    }

    public boolean validaMetaInhabilitada(int year, String ramo, int meta, double totalMeta, boolean isUsuario) {
        boolean isInhabilitado = false;
        try {
            if (super.getResultSLQgetSumaAsignadoActualizadoMeta(year, ramo, meta, totalMeta, isUsuario)) {
                if (super.getResultSLQgetEjercidoCompromisoMeta(year, ramo, meta)) {
                    isInhabilitado = super.getResultSQLisAvanceMetaCapturada(year, ramo, meta);
                }
            }
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return isInhabilitado;
    }

    public boolean validaAccionInhabilitada(int year, String ramo, int meta, int accion) {
        boolean isInhabilitado = false;
        try {
            if (super.getResultSLQgetSumaAsignadoActualizadoAccion(year, ramo, meta, accion)) {
                if (super.getResultSLQgetEjercidoCompromisoAccion(year, ramo, meta, accion)) {
                    isInhabilitado = super.getResultSQLisAvanceAccionCapturado(year, ramo, meta, accion);
                }
            }
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return isInhabilitado;
    }

    public boolean validaAccionInhabilitada(int year, String ramo, int meta, int accion, double totalAccion, boolean isUsuario) {
        boolean isInhabilitado = false;
        try {
            if (super.getResultSLQgetSumaAsignadoActualizadoAccion(year, ramo, meta, accion, totalAccion, isUsuario)) {
                if (super.getResultSLQgetEjercidoCompromisoAccion(year, ramo, meta, accion)) {
                    isInhabilitado = super.getResultSQLisAvanceAccionCapturado(year, ramo, meta, accion);
                }
            }
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }
        return isInhabilitado;
    }

    public int validaTransferenciasNumOficons(int folio) {
        int num = 0;
        try {
            num = super.getResultSQLCountValidaTransferenciasOficons(folio);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }

        return num;
    }

    public int validaAmpliacionesNumOficons(int folio) {
        int num = 0;
        try {
            num = super.getResultSQLCountValidaAmpliacionesOficons(folio);
        } catch (SQLException ex) {
            bitacora.setStrUbicacion(getStrUbicacion());
            bitacora.setStrServer(getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, new Throwable());
            bitacora.grabaBitacora();
        }

        return num;
    }

}
