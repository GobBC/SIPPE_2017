<%-- 
    Document   : ajaxValidarAvanceEstimacion
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

    ResultSQL validadorBean = null;

    String ramoId = new String();
    String strResult = new String();
    String tipoValidacion = new String();
    String tipoDependencia = new String();

    int year = -1;
    int metaId = -1;
    int numMes = -1;
    int accionId = -1;

    double valorViejo = -1;
    double valorNuevo = -1;

    try {

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
        if (request.getParameter("numMes") != null && !request.getParameter("numMes").equals("")) {
            numMes = Integer.parseInt((String) request.getParameter("numMes"));
        }
        if (request.getParameter("valorNuevo") != null && !request.getParameter("valorNuevo").equals("")) {
            valorNuevo = new Double((String) request.getParameter("valorNuevo"));
        }
        if (request.getParameter("tipoValidacion") != null && !request.getParameter("tipoValidacion").equals("")) {
            tipoValidacion = (String) request.getParameter("tipoValidacion");
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependencia") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }

        validadorBean = new ResultSQL(tipoDependencia);
        validadorBean.setStrServer(request.getHeader("host"));
        validadorBean.setStrUbicacion(getServletContext().getRealPath(""));
        validadorBean.resultSQLConecta(tipoDependencia);

        strResult = "0";

        if (tipoValidacion.equalsIgnoreCase("M")) {
            valorViejo = validadorBean.getResultGetValorPeridoAvanceMetaByYearRamoMetaPeriodo(year, ramoId, metaId, numMes);
        } else {
            if (tipoValidacion.equalsIgnoreCase("A")) {
                valorViejo = validadorBean.getResultGetValorPeridoAvanceAccionByYearRamoMetaAccionMes(year, ramoId, metaId, accionId, numMes);
            }
        }

        if (valorViejo > valorNuevo) {
            strResult = "" + valorViejo;
        } else {
            strResult = "correcto";
        }

    } catch (Exception ex) {
        strResult = "error";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (validadorBean != null) {
            validadorBean.resultSQLDesconecta();
        }
        out.print(strResult);
    }
%>
