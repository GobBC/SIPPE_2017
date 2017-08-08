<%-- 
    Document   : ajaxSaveJustificacion
    Created on : jun 12, 2017, 9:03:06 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.JustificacionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    JustificacionBean justificacionBean = null;
    String strResult = new String();
    String justificacion = new String();
    String tipoDependencia = new String();
    long idJustificacion = 0;
    int folio = 0;
    boolean resultado = false;

    try {

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("folio") != null && !request.getParameter("folio").equals("")) {
            folio = Integer.parseInt(request.getParameter("folio"));
        }
        if (request.getParameter("justificacion") != null && !request.getParameter("justificacion").equals("")) {
            justificacion = request.getParameter("justificacion");
            justificacion =  justificacion.replaceAll("'","''");
        }
        if (request.getParameter("idJustificacion") != null && !request.getParameter("idJustificacion").equals("")) {
            idJustificacion = Long.parseLong(request.getParameter("idJustificacion"));
        }

        justificacionBean = new JustificacionBean(tipoDependencia);
        justificacionBean.setStrServer(request.getHeader("host"));
        justificacionBean.setStrUbicacion(getServletContext().getRealPath(""));
        justificacionBean.resultSQLConecta(tipoDependencia);

        resultado = justificacionBean.getSaveJustificacion(folio,idJustificacion,justificacion);

        if (resultado) {
            strResult = "1";
        } else {
            strResult = "-1";
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
        if (justificacionBean != null) {
            justificacionBean.resultSQLDesconecta();
        }
        out.print(strResult);
    }
%>
