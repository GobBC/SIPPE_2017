<%-- 
    Document   : ajaxValidarReportesExcel
    Created on : Jun 12, 2015, 9:33:49 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.ReporteExcelBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", -1);

    String strResultado = new String();
    String tipoDependencia = new String();
    ReporteExcelBean reporteExcelBean = null;
    int year = 0;
    String ramos = new String();
    int reporte = 0;
    int countRow = 0;
    String queryPartidas = new String();

    try {
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (request.getParameter("ramos") != null
                && !request.getParameter("ramos").equals("")) {
            ramos = (String) request.getParameter("ramos");
        }
        if (request.getParameter("queryPartidas") != null
                && !request.getParameter("queryPartidas").equals("")) {
            queryPartidas = (String) request.getParameter("queryPartidas");
        }
        if (request.getParameter("reporte") != null
                && !request.getParameter("reporte").equals("")) {
            reporte = Integer.parseInt((String) request.getParameter("reporte"));
        }
        reporteExcelBean = new ReporteExcelBean(tipoDependencia);
        reporteExcelBean.setStrServer(((String) request.getHeader("Host")));
        reporteExcelBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        reporteExcelBean.resultSQLConecta(tipoDependencia);
        if (reporte == 1) {
            countRow = reporteExcelBean.getValidarPresDetalleAccionString(ramos, year);
        } else if (reporte == 2) {
            countRow = reporteExcelBean.getValidarPresXCodigoPrg(ramos, year);
        } else if (reporte == 3) {
            countRow = reporteExcelBean.getValidarReporteLineasEstrategicas(year);
        } else if (reporte == 4) {
            countRow = reporteExcelBean.getValidarReportePptoCalAnualRamoPartida(ramos, year);
        } else if (reporte == 5) {
            countRow = reporteExcelBean.getValidarReportePptoCalAnualRamoProgramaPartida(ramos, year);
        } else if (reporte == 6) {
            countRow = reporteExcelBean.getValidarReportePptoCalAnualCodigo(ramos, year);
        } else if (reporte == 7) {
            countRow = reporteExcelBean.getValidarReportePptoAnualRamoGrupo(ramos, year);
        } else if (reporte == 8) {
            countRow = reporteExcelBean.getValidarReportePptoAnualRamoProgramaGrupo(ramos, year);
        } else if (reporte == 9) {
            countRow = reporteExcelBean.getValidarReportePptoAnualCodigo(ramos, year);
        } else if (reporte == 10) {
            countRow = reporteExcelBean.getValidarReporteComparativoPresupuesto(ramos, year, queryPartidas);
        } else if (reporte == 11) {
            countRow = reporteExcelBean.getValidarPresXCodigoPrgModif(ramos, year);
        }

        if (countRow == 0) {
            strResultado = "0";
        } else {
            strResultado = "1";
        }
    } catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (reporteExcelBean != null) {
            reporteExcelBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
