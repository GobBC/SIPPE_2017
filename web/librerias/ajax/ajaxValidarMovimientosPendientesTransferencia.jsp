<%-- 
    Document   : ajaxValidarMovimientosPendientesTransferencia
    Created on : Jan 15, 2016, 11:43:06 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.TransferenciaAccionReq"%>
<%@page import="gob.gbc.entidades.Transferencia"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.TransferenciaAccion"%>
<%@page import="gob.gbc.entidades.TransferenciaMeta"%>
<%@page import="gob.gbc.entidades.TransferenciaMeta"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%

    ResultSQL validadorBean = null;
    MovimientosTransferencia movimiento = new MovimientosTransferencia();
    Transferencia transAccion = new Transferencia();
    List<TransferenciaMeta> transferenciaMetaList = new ArrayList<TransferenciaMeta>();
    List<TransferenciaAccion> transferenciaAccionList = new ArrayList<TransferenciaAccion>();
    List<Transferencia> movimientosTransferenciaRecibenList = new ArrayList<Transferencia>();
    String strResult = new String();
    String ramoId = new String();
    String tipoDependencia = new String();
    String tipoValidacion = new String();
    String tipoTransferencia = new String();
    int year = 0;
    int transferenciaId = 0;
    int metaId = 0;
    int accionId = 0;
    int oficio = 0;
    int existenMovMetas = 0;
    int existenMovAcciones = 0;
    boolean bandera = true; // cambiar a false si vuelve validación de registros programáticos

    try {

        if (session.getAttribute("transferencia") != null) {
            movimiento = (MovimientosTransferencia) session.getAttribute("transferencia");
        }

        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }

        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramoId = request.getParameter("ramoId");
        }

        if (request.getParameter("metaId") != null && !request.getParameter("metaId").equals("")) {
            metaId = Integer.parseInt(request.getParameter("metaId"));
        }

        if (request.getParameter("accionId") != null && !request.getParameter("accionId").equals("")) {
            accionId = Integer.parseInt(request.getParameter("accionId"));
        }

        if (request.getParameter("oficio") != null && !request.getParameter("oficio").equals("")) {
            oficio = Integer.parseInt(request.getParameter("oficio"));
        }

        if (request.getParameter("transferenciaId") != null && !request.getParameter("transferenciaId").equals("")) {
            transferenciaId = Integer.parseInt(request.getParameter("transferenciaId"));
        }

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependencia") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }

        if (request.getParameter("tipoValidacion") != null && !request.getParameter("tipoValidacion").equals("")) {
            tipoValidacion = request.getParameter("tipoValidacion");
        }

        if (request.getParameter("tipoTransferencia") != null && !request.getParameter("tipoTransferencia").equals("")) {
            tipoTransferencia = request.getParameter("tipoTransferencia");
        }

        validadorBean = new ResultSQL(tipoDependencia);
        validadorBean.setStrServer(request.getHeader("host"));
        validadorBean.setStrUbicacion(getServletContext().getRealPath(""));
        validadorBean.resultSQLConecta(tipoDependencia);

        transferenciaAccionList = movimiento.getTransferenciaAccionList();

        strResult = "0";

        if (tipoValidacion.equalsIgnoreCase("M")) {

            /*for (Transferencia trans : movimiento.getTransferenciaList()) {
                if (trans.getRamo().equals(ramoId) && trans.getMeta() == metaId) {
                    bandera = true;
                }
            }*/
            if (bandera) {
                transferenciaMetaList = movimiento.getTransferenciaMetaList();
                if (transferenciaMetaList.size() > 0) {
                    for (TransferenciaMeta movMeta : transferenciaMetaList) {
                        if ((movMeta.getMovOficioEstimacion().get(0).getRamo().equalsIgnoreCase(ramoId) && movMeta.getMovOficioEstimacion().get(0).getMeta() == metaId && movMeta.getMovOficioEstimacion().get(0).getYear() == year)) {
                            strResult = "1";
                        }
                    }
                }
            } else {
                strResult = "5";
            }


            if (strResult.equals("0")) {
                existenMovMetas = validadorBean.getResultExistenMovMetaAmpliacionReduccionByClave(year, ramoId, metaId, oficio);
                if (existenMovMetas != 0) {
                    strResult = "2|"+existenMovMetas;
                }
            }

        } else {

            if (tipoValidacion.equalsIgnoreCase("A")) {
                /*for (Transferencia trans : movimiento.getTransferenciaList()) {
                    if (trans.getRamo().equals(ramoId) && trans.getMeta() == metaId && trans.getAccion() == accionId) {
                        bandera = true;
                    }
                }*/
                if (bandera) {
                    transferenciaAccionList = movimiento.getTransferenciaAccionList();
                    if (transferenciaAccionList.size() > 0) {
                        for (TransferenciaAccion movAccion : transferenciaAccionList) {
                            if ((movAccion.getMovOficioAccionEstList().get(0).getRamo().equalsIgnoreCase(ramoId) && movAccion.getMovOficioAccionEstList().get(0).getMeta() == metaId && movAccion.getMovOficioAccionEstList().get(0).getAccion() == accionId && movAccion.getMovOficioAccionEstList().get(0).getYear() == year)) {
                                strResult = "3";
                            }
                        }
                    }
                } else {
                    strResult = "6";
                }

                if (strResult.equals("0")) {
                    existenMovAcciones = validadorBean.getResultExistenMovAccionAmpliacionReduccionByClave(year, ramoId, metaId, accionId, oficio);
                    if (existenMovAcciones != 0) {
                        strResult = "4|"+existenMovAcciones;
                    }
                }
            }
        }

    } catch (NullPointerException ex) {
        strResult = "7";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } catch (Exception ex) {
        strResult = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();

    } finally {

        out.print(strResult);

    }
%>
