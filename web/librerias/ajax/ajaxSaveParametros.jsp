<%-- 
    Document   : ajaxSaveParametros
    Created on : Oct 25, 2016, 15:32:00 PM
    Author     : jarguelles
--%>
<%@page import="gob.gbc.aplicacion.ParametrosBean"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="org.json.JSONObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%

    response.setHeader("Expires", "0");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");

    ParametrosBean parametrosBean = null;

    String reporteCierre = "N";
    String repWebImpFirma = "N";
    String validaTrimestre = "N";
    String repValidaInfoCim = "N";
    String strResult = new String();
    String validaTodosTrimestre = "N";
    String tipoDependencia = new String();

    try {

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("chkFirmas") != null && request.getParameter("chkFirmas") != "") {
            repWebImpFirma = (String) request.getParameter("chkFirmas");
        }
        if (request.getParameter("chkTrimestre") != null && request.getParameter("chkTrimestre") != "") {
            validaTrimestre = (String) request.getParameter("chkTrimestre");
        }
        if (request.getParameter("chkAvance") != null && request.getParameter("chkAvance") != "") {
            reporteCierre = (String) request.getParameter("chkAvance");
        }
        if (request.getParameter("chkEtiqueta") != null && request.getParameter("chkEtiqueta") != "") {
            repValidaInfoCim = (String) request.getParameter("chkEtiqueta");
        }
        if (request.getParameter("chkTodosTrimestre") != null && request.getParameter("chkTodosTrimestre") != "") {
            validaTodosTrimestre = (String) request.getParameter("chkTodosTrimestre");
        }

        parametrosBean = new ParametrosBean(tipoDependencia);
        parametrosBean.setStrServer((request.getHeader("Host")));
        parametrosBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        parametrosBean.resultSQLConecta(tipoDependencia);

        if (parametrosBean.saveParametros(repWebImpFirma, validaTrimestre, reporteCierre, repValidaInfoCim, validaTodosTrimestre)) {
            parametrosBean.transaccionCommit();
            strResult = "1";
        } else {
            parametrosBean.transaccionRollback();
        }

    } catch (Exception ex) {
        strResult = "No es posible actualizar la información";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (parametrosBean != null) {
            parametrosBean.resultSQLDesconecta();
        }
        out.print(strResult);
    }
%>
