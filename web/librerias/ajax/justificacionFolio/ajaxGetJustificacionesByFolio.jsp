<%-- 
    Document   : ajaxGetJustificacionesByFolio
    Created on : Jun 09, 2017, 10:03:06 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.Justificacion"%>
<%@page import="gob.gbc.aplicacion.JustificacionBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.util.Fechas"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    response.setCharacterEncoding("UTF-8");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    JustificacionBean justificacionBean = null;
    List<Justificacion> justificacionList = new ArrayList<Justificacion>();

    Fechas objFechas = new Fechas();

    String allDisabled = "";
    String rol = new String();
    String appLogin = new String();
    String strResult = new String();
    String tipoDependencia = new String();

    int year = 0;
    int selFolios = 0;

    try {

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("selFolios") != null && !request.getParameter("selFolios").equals("")) {
            selFolios = Integer.parseInt((String) request.getParameter("selFolios"));
        }

        justificacionBean = new JustificacionBean(tipoDependencia);
        justificacionBean.setStrServer(request.getHeader("host"));
        justificacionBean.setStrUbicacion(getServletContext().getRealPath(""));
        justificacionBean.resultSQLConecta(tipoDependencia);

        justificacionList = justificacionBean.getJustificacionesByFolio(selFolios);

        strResult += "<option value='-1' Selected > -- Seleccione una justificación -- </option> ";

        for (Justificacion justificacion : justificacionList) {
            strResult += "<option value=" + justificacion.getId_justificacion() + "  > " + justificacion.getId_justificacion() + " - " + justificacion.getJustificacion() + " </option>";
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
