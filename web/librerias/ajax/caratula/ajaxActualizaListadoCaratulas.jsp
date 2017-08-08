<%-- 
    Document   : ajaxActualizaListadoCaratulas
    Created on : May 24, 2016, 08:30:34 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page import="gob.gbc.entidades.Caratula"%>
<%@page import="gob.gbc.entidades.TipoSesion"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
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

    CaratulaBean caratulaBean = null;
    ArrayList<Caratula> arrCaratulas = null;
    String ramo = new String();
    String strSelected = new String();
    String strResultado = new String();
    String caratulaDisable = new String();
    String tipoDependencia = new String();
    long caratula = -1;
    int year = 0;
    boolean bFiltraEstatusAbiertas = false;

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

        caratulaBean = new CaratulaBean(tipoDependencia);
        caratulaBean.setStrServer((request.getHeader("Host")));
        caratulaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        caratulaBean.resultSQLConecta(tipoDependencia);

        arrCaratulas = caratulaBean.getCaratulas(String.valueOf(year), ramo, bFiltraEstatusAbiertas, 1, false);

        strResultado += "<option value='-1'> -- Seleccione un caratula -- </option>";

        if (arrCaratulas.size() > 0) {
            for (Caratula objCaratula : arrCaratulas) {
                caratulaDisable = "";
                if (year != objCaratula.getiYear()) {
                    caratulaDisable = " class='disabled' disabled ";
                } else {
                    caratulaDisable = " class='enabled' ";
                }

                strSelected = "";
                if (caratula == objCaratula.getsIdCaratula()) {
                    strSelected = " selected ";
                }
                if (year != objCaratula.getiYearSesion()) {
                    strResultado += "<option " + caratulaDisable + strSelected + " title='Sesi&oacute;n " + objCaratula.getiYearSesion() + "' value=" + objCaratula.getsIdCaratula() + ">" + objCaratula.getStrTipoModificacionDescr() + " " + objCaratula.getsDescr() + " </option>";
                } else {
                    strResultado += "<option " + caratulaDisable + strSelected + " value=" + objCaratula.getsIdCaratula() + ">" + objCaratula.getStrTipoModificacionDescr() + " " + objCaratula.getsDescr() + " </option>";
                }

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
        out.print(strResultado);
    }
%>
