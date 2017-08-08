<%-- 
    Document   : ajaxValidarMovimientosPendientesReprogramacion
    Created on : Dec 29, 2015, 11:43:06 AM
    Author     : jarguelles
--%>

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
    List<ReprogramacionMeta> reprogMetaList = new ArrayList<ReprogramacionMeta>();
    List<ReprogramacionAccion> reprogAccionList = new ArrayList<ReprogramacionAccion>();
    MovimientosReprogramacion movimiento = new MovimientosReprogramacion();
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
    try {
        if (session.getAttribute("reprogramacion") != null) {
            movimiento = (MovimientosReprogramacion) session.getAttribute("reprogramacion");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramoId = (String) request.getParameter("ramoId");
        }
        if (request.getParameter("metaId") != null && !request.getParameter("metaId").equals("")) {
            metaId = Integer.parseInt((String) request.getParameter("metaId"));
        }
        if (request.getParameter("accionId") != null && !request.getParameter("accionId").equals("")) {
            accionId = Integer.parseInt((String) request.getParameter("accionId"));
        }
        if (request.getParameter("oficio") != null && !request.getParameter("oficio").equals("")) {
            oficio = Integer.parseInt((String) request.getParameter("oficio"));
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependencia") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }

        validadorBean = new ResultSQL(tipoDependencia);
        validadorBean.setStrServer(request.getHeader("host"));
        validadorBean.setStrUbicacion(getServletContext().getRealPath(""));
        validadorBean.resultSQLConecta(tipoDependencia);

        reprogMetaList = movimiento.getMovOficioMetaList();
        reprogAccionList = movimiento.getMovOficioAccionList();

        strResult = "0";

        if (reprogMetaList.size() > 0) {
            for (ReprogramacionMeta movMeta : reprogMetaList) {
                if ((movMeta.getMetaInfo().getRamoId().equalsIgnoreCase(ramoId) && movMeta.getMetaInfo().getMetaId() == metaId && movMeta.getMetaInfo().getYear() == year)) {
                    strResult = "3";
                }
            }
        } else {
            existenMovMetas = validadorBean.getResultExistenMovMetaReprogramacionByClave(year, ramoId, metaId, oficio);
            if (existenMovMetas != 0) {
                strResult = "1|"+existenMovMetas;
            }
        }

        if (reprogAccionList.size() > 0) {
            for (ReprogramacionAccion movAccion : reprogAccionList) {
                if ((movAccion.getMovOficioAccion().getRamoId().equalsIgnoreCase(ramoId) && movAccion.getMovOficioAccion().getMetaId() == metaId && movAccion.getMovOficioAccion().getAccionId() == accionId && movAccion.getMovOficioAccion().getYear() == year)) {
                    strResult = "4";
                }
            }
        } else {
            existenMovAcciones = validadorBean.getResultExistenMovAccionReprogramacionByClave(year, ramoId, metaId, accionId, oficio);
            if (existenMovAcciones != 0) {
                strResult = "2|"+existenMovAcciones;
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
