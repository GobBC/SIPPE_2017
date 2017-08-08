<%-- 
    Document   : ajaxSaveCaratulaOficio
    Created on : May 30, 2016, 08:30:34 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.Caratula"%>
<%@page import="gob.gbc.entidades.TipoSesion"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page import="gob.gbc.entidades.RevisionCaratula"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="java.util.Date"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.util.Utileria"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    response.setCharacterEncoding("UTF-8");
    ResultSQL resultSQL = new ResultSQL();
    CaratulaBean caratulaBean = null;

    String ramo = new String();
    String strResultado = new String();
    String tipoDependencia = new String();

    long caratula = -2;
    long caratulaAnterior = -2;

    int year = 0;
    int folio = 0;

    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    try {
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramo = request.getParameter("ramoId");
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependecia") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("caratula") != null && !request.getParameter("caratula").equals("")) {
            caratula = Long.parseLong(request.getParameter("caratula"));
        }
        if (request.getParameter("caratulaAnterior") != null && !request.getParameter("caratulaAnterior").equals("")) {
            caratulaAnterior = Long.parseLong(request.getParameter("caratulaAnterior"));
        }
        if (request.getParameter("folio") != null && !request.getParameter("folio").equals("")) {
            folio = Integer.parseInt((String) request.getParameter("folio"));
        }

        caratulaBean = new CaratulaBean(tipoDependencia);
        caratulaBean.setStrServer((request.getHeader("Host")));
        caratulaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        caratulaBean.resultSQLConecta(tipoDependencia);

        if (caratulaBean.saveCaratulaOficio(folio, year, ramo, caratula, caratulaAnterior)) {
            if (caratula != -2) {
                strResultado = "Folio correctamente agregado a la caratula";
            } else {
                strResultado = "Folio correctamente eliminado de la caratula";
            }
            caratulaBean.transaccionCommit();
        } else {
            strResultado = "No se pudo modificar";
            caratulaBean.transaccionRollback();
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
        if (caratulaBean != null) {
            caratulaBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
