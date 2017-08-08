<%-- 
    Document   : ajaxActualizaTablaMovimientosByCaratula
    Created on : May 24, 2016, 9:03:06 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.Caratula"%>
<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.util.Fechas"%>
<%@page import="gob.gbc.entidades.RecalendarizacionAccion"%>
<%@page import="gob.gbc.entidades.RecalendarizacionMeta"%>
<%@page import="gob.gbc.entidades.Movimiento"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.MovimientosRecalendarizacion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    response.setCharacterEncoding("UTF-8");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    Caratula objCaratula = null;
    CaratulaBean caratulaBean = null;
    Movimiento movto = new Movimiento();
    MovimientosRecalendarizacion movimiento = new MovimientosRecalendarizacion();

    List<Movimiento> movimientoList = new ArrayList<Movimiento>();

    Fechas objFechas = new Fechas();

    String allDisabled = "";
    String rol = new String();
    String caratulaEstatus = "A";
    String btnJust = new String();
    String appLogin = new String();
    String strResult = new String();
    String ramoSession = new String();
    String tipoDependencia = new String();
    String btnAsignarCaratula = new String();
    String btnDesAsignarCaratula = new String();

    long caratula = -2;
    long caratulaSelect = -2;

    int year = 0;
    int folio = 0;

    try {

        if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
            rol = (String) session.getAttribute("strRol");
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            appLogin = (String) session.getAttribute("strUsuario");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramoSession = request.getParameter("ramoId");
        }
        if (request.getParameter("caratula") != null && !request.getParameter("caratula").equals("")) {
            caratula = Long.parseLong(request.getParameter("caratula"));
        }
        if (request.getParameter("caratulaSelect") != null && !request.getParameter("caratulaSelect").equals("")) {
            caratulaSelect = Long.parseLong(request.getParameter("caratulaSelect"));
        }

        caratulaBean = new CaratulaBean(tipoDependencia);
        caratulaBean.setStrServer(request.getHeader("host"));
        caratulaBean.setStrUbicacion(getServletContext().getRealPath(""));
        caratulaBean.resultSQLConecta(tipoDependencia);

        movimientoList = caratulaBean.getMovimientosByCaratula(year, ramoSession, appLogin, rol.equals(caratulaBean.getRolNormativo()), caratula);
        objCaratula = caratulaBean.getResultSQLCaratulaByYearIdRamoIdCaratula(String.valueOf(year), ramoSession, caratulaSelect);

        if (objCaratula != null) {
            caratulaEstatus = objCaratula.getiStatus();
        }

        if (caratulaEstatus.equals("C")) {
            allDisabled = "disabled";
        }

        if (allDisabled.equals("") || caratula != -2) {
            for (Movimiento solicitudMovimiento : movimientoList) {

                strResult += "<tr>";
                strResult += "  <td>" + solicitudMovimiento.getOficio() + "</td>";

                if (solicitudMovimiento.getFechaElab() != null) {
                    strResult += "  <td>" + objFechas.getFechaFormato(solicitudMovimiento.getFechaElab(), Fechas.FORMATO_CORTO) + "</td>";
                } else {
                    strResult += "  <td>" + "" + "</td>";
                }

                strResult += "  <td>" + solicitudMovimiento.getStatusDescr() + "</td>";

                btnJust = "<input type='button' class='btnbootstrap-drop btn-infoGlobe-reporte ' style='width:40px;height:25px' title='" + solicitudMovimiento.getJutificacion() + "' " + "  />";

                if (caratula == -2) {
                    btnAsignarCaratula = "<input " + allDisabled + " type='checkbox' id='btn-asignar-caratula'  title='Asignar a la caratula' onclick='asignarCaratulaOficio(\"" + caratula + "\",\"" + solicitudMovimiento.getOficio() + "\")' />";
                } else {
                    btnDesAsignarCaratula = "<input " + allDisabled + " checked type='checkbox' id='btn-desasignar-caratula'  title='Desasignar de la caratula' onclick='desasignarCaratulaOficio(\"" + caratula + "\",\"" + solicitudMovimiento.getOficio() + "\")' />";
                }

                strResult += "<td>" + btnJust + btnAsignarCaratula + btnDesAsignarCaratula + "</td>";

                strResult += "</tr>";

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
        if (caratulaBean != null) {
            caratulaBean.resultSQLDesconecta();
        }
        out.print(strResult);
    }
%>
