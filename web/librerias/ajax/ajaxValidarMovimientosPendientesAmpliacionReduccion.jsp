<%-- 
    Document   : ajaxValidarMovimientosPendientesAmpliacionReduccion
    Created on : Dec 29, 2015, 11:43:06 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.MovOficioEstimacion"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionAccion"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionMeta"%>
<%@page import="gob.gbc.entidades.MovimientosAmpliacionReduccion"%>
<%@page import="gob.gbc.entidades.MovimientosReprogramacion"%>
<%@page import="gob.gbc.entidades.ReprogramacionAccion"%>
<%@page import="gob.gbc.entidades.ReprogramacionMeta"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.RecalendarizacionAccionReq"%>
<%@page import="gob.gbc.entidades.MovimientosRecalendarizacion"%>
<%@page import="gob.gbc.entidades.RecalendarizacionAccion"%>
<%@page import="gob.gbc.entidades.RecalendarizacionMeta"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    List<AmpliacionReduccionMeta> ampliacionReduccionMetaList = new ArrayList<AmpliacionReduccionMeta>();
    List<AmpliacionReduccionAccion> ampliacionReduccionAccionList = new ArrayList<AmpliacionReduccionAccion>();
    MovimientosAmpliacionReduccion movimiento = new MovimientosAmpliacionReduccion();
    String strResult = new String();
    int year = 0;
    String ramoId = new String();
    int metaId = 0;
    int accionId = 0;
    int oficio = 0;
    ResultSQL validadorBean = null;
    String tipoDependencia = new String();
    int existenMovMetas = 0;
    int existenMovAcciones = 0;
    String tipoValidacion = new String();
    try {
        request.setCharacterEncoding("UTF-8");
        if (session.getAttribute("ampliacionReduccion") != null) {
            movimiento = (MovimientosAmpliacionReduccion) session.getAttribute("ampliacionReduccion");
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
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependencia") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("tipoValidacion") != null && !request.getParameter("tipoValidacion").equals("")) {
            tipoValidacion = request.getParameter("tipoValidacion");
        }

        validadorBean = new ResultSQL(tipoDependencia);
        validadorBean.setStrServer(request.getHeader("host"));
        validadorBean.setStrUbicacion(getServletContext().getRealPath(""));
        validadorBean.resultSQLConecta(tipoDependencia);

        ampliacionReduccionMetaList = movimiento.getAmpReducMetaList();
        ampliacionReduccionAccionList = movimiento.getAmpReducAccionList();

        strResult = "0";

        if (tipoValidacion.equalsIgnoreCase("M")) {            
            if (ampliacionReduccionMetaList.size() > 0) {
                for (AmpliacionReduccionMeta movMeta : ampliacionReduccionMetaList) {
                    if ((movMeta.getMovOficioEstimacion().get(0).getRamo().equalsIgnoreCase(ramoId) && movMeta.getMovOficioEstimacion().get(0).getMeta() == metaId && movMeta.getMovOficioEstimacion().get(0).getYear() == year)) {
                        strResult = "1";
                    }
                }
            }

            if (strResult.equals("0")) {
                existenMovMetas = validadorBean.getResultExistenMovMetaAmpliacionReduccionByClave(year, ramoId, metaId, oficio);
                if (existenMovMetas != 0) {
                    strResult = "2|"+existenMovMetas;
                }
            }

        } else {

            if (tipoValidacion.equalsIgnoreCase("A")) {

                if (ampliacionReduccionAccionList.size() > 0) {
                    for (AmpliacionReduccionAccion movAccion : ampliacionReduccionAccionList) {
                        if ((movAccion.getMovOficioAccionEstList().get(0).getRamo().equalsIgnoreCase(ramoId) && movAccion.getMovOficioAccionEstList().get(0).getMeta() == metaId && movAccion.getMovOficioAccionEstList().get(0).getAccion() == accionId && movAccion.getMovOficioAccionEstList().get(0).getYear() == year)) {
                            strResult = "3";
                        }
                    }
                }

                if (strResult.equals("0")) {
                    existenMovAcciones = validadorBean.getResultExistenMovAccionAmpliacionReduccionByClave(year, ramoId, metaId, accionId, oficio);
                    if (existenMovAcciones != 0) {
                        strResult = "4|"+existenMovAcciones;
                    }
                }
                
            }
            
        }
        
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
