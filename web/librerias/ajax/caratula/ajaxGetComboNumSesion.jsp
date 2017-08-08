<%-- 
    Document   : ajaxDisplayPopUpCaratula
    Created on : May 24, 2016, 08:30:34 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.TipoModificacion"%>
<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page import="gob.gbc.entidades.Caratula"%>
<%@page import="gob.gbc.entidades.TipoSesion"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="gob.gbc.aplicacion.RevisionCaratulaBean"%>
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
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    Caratula objCaratula = null;
    CaratulaBean caratulaBean = null;
    RevisionCaratulaBean revisionCaratulaBean = null;
    ArrayList<TipoSesion> tipoSesionList = null;
    List<RevisionCaratula> revisionesCaratulaList = null;
    ArrayList<TipoModificacion> arrTiposModificaciones = null;
    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String allDisabled = "";
    String rol = new String();
    String ramo = new String();
    String fecha = new String();
    String selected = new String();
    String disabledCheckStatus = "";
    String strResultado = new String();
    String fechaSession = new String();
    String tipoDependencia = new String();
    long selCaratula = -1;
    int yearAct;
    int monthAct;
    int cont = 1;
    int year = 0;
    int caratulaYearSesion = 0;
    int caratulaTipoSesion = 0;
    int caratulaTipoModificacion = 0;

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
        if (Utilerias.existeParametro("fechaSession", request)) {
            fechaSession = (String) request.getParameter("fechaSession");
        }
        if (request.getParameter("selCaratula") != null && !request.getParameter("selCaratula").equals("")) {
            selCaratula = Long.parseLong(request.getParameter("selCaratula"));
        }
        if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
            rol = (String) session.getAttribute("strRol");
        }
        if (request.getParameter("fecha") != null && !request.getParameter("fecha").equals("")) {
            fecha = (String) request.getParameter("fecha");
        }
        if (request.getParameter("selTipoSesion") != null && !request.getParameter("selTipoSesion").equals("")) {
            caratulaTipoSesion = Integer.parseInt(request.getParameter("selTipoSesion"));
        }
        if (request.getParameter("selTipoModificacion") != null && !request.getParameter("selTipoModificacion").equals("")) {
            caratulaTipoModificacion = Integer.parseInt(request.getParameter("selTipoModificacion"));
        }

        revisionCaratulaBean = new RevisionCaratulaBean(tipoDependencia);
        revisionCaratulaBean.setStrServer((request.getHeader("Host")));
        revisionCaratulaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        revisionCaratulaBean.resultSQLConecta(tipoDependencia);

        if (!fecha.equals("")) {
            caratulaYearSesion = Integer.parseInt(fecha.split("/")[2]);
        }

        revisionesCaratulaList = revisionCaratulaBean.getListRevisionesCaratulaByRamoCaratulaYear(ramo, selCaratula, year, caratulaYearSesion, caratulaTipoModificacion, caratulaTipoSesion);

        strResultado += "<option value='-1'> -- Seleccione un numero de sesi&oacute;n -- </option>";

        if (caratulaYearSesion * caratulaTipoModificacion * caratulaTipoSesion > 0) {
            for (RevisionCaratula revisionCaratula : revisionesCaratulaList) {
                if (revisionCaratula.isLibre_Num_Session()) {
                    strResultado += "<option class='enabled' value='" + revisionCaratula.getRevision() + "'>";
                } else {
                    strResultado += "<option class='disabled' disabled value='" + revisionCaratula.getRevision() + "'>";
                }
                strResultado += " " + revisionCaratula.getDescr_Corta() + " " + revisionCaratula.getDescr();
                strResultado += "</option>";
            }
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

        if (revisionCaratulaBean != null) {
            revisionCaratulaBean.resultSQLDesconecta();
        }

        out.print(strResultado);
    }
%>
