<%-- 
    Document   : ajaxValidaBorrarLineaPed
    Created on : Dec 16, 2015, 9:53:00 PM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="gob.gbc.entidades.Linea"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.LineaSectorial"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.aplicacion.LineaBean"%>
<%@page import="gob.gbc.aplicacion.MetaAccionPlantillaBean"%>
<%@page import="gob.gbc.util.Bitacora"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    boolean blResultado = false;
    String strResultado = new String();
    String ramoId = new String();
    int metaId = 0;
    int accionId = 0;
    String tipoDependencia = new String();
    ResultSQL resultSQL = null;
    int existen;
    List<Linea> lineaProyecto = new ArrayList<Linea>();
    List<Linea> lineaProyectoTemp = new ArrayList<Linea>();
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    try {

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }

        if (request.getParameter("ramoId") != null
                && !request.getParameter("ramoId").equals("")) {
            ramoId = (String) request.getParameter("ramoId");
        }

        if (request.getParameter("metaId") != null
                && !request.getParameter("metaId").equals("")) {
            metaId = Integer.parseInt((String) request.getParameter("metaId"));
        }

        if (request.getParameter("accionId") != null
                && !request.getParameter("accionId").equals("")) {
            accionId = Integer.parseInt((String) request.getParameter("accionId"));
        }

        resultSQL = new LineaBean(tipoDependencia);
        resultSQL.setStrServer(request.getHeader("host"));
        resultSQL.setStrUbicacion(getServletContext().getRealPath(""));
        resultSQL.resultSQLConecta(tipoDependencia);

        existen = resultSQL.getResultExistenMovOficiosByRamoMetaAccion(ramoId, metaId, accionId);
        strResultado = "" + existen;

    } catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (resultSQL != null) {
            resultSQL.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
