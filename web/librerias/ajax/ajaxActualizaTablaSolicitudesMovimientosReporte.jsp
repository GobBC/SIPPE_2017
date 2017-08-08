<%-- 
    Document   : ajaxActualizaTablaSolicitudesMovimientosReporte
    Created on : Dec 11, 2015, 9:03:06 AM
    Author     : jarguelles
--%>


<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.util.Fechas"%>
<%@page import="gob.gbc.entidades.RecalendarizacionAccion"%>
<%@page import="gob.gbc.entidades.RecalendarizacionMeta"%>
<%@page import="gob.gbc.entidades.Movimiento"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.MovimientosRecalendarizacion"%>
<%@page import="gob.gbc.aplicacion.MovimientosBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%

    MovimientosBean movimientosBean = null;
    MovimientosRecalendarizacion movimiento = new MovimientosRecalendarizacion();

    List<Movimiento> movimientoList = new ArrayList<Movimiento>();

    Fechas objFechas = new Fechas();

    String rowPar = new String();
    String btnVer = new String();
    String btnInfo = new String();
    String btnJust = new String();
    String strResult = new String();
    String tipoSolicitudId = new String();
    String tipoDependencia = new String();
    String estatusMovimientoId = new String();

    int contPar = 0;
    int year = 0;

    try {

        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("tipoSolicitudId") != null && !request.getParameter("tipoSolicitudId").equals("")) {
            tipoSolicitudId = request.getParameter("tipoSolicitudId");
        }
        if (request.getParameter("estatusMovimientoId") != null && !request.getParameter("estatusMovimientoId").equals("")) {
            estatusMovimientoId = request.getParameter("estatusMovimientoId");
        }
        String sTipoReporte = "";
        if (Utilerias.existeParametro("rdGeneral", request)) {
            sTipoReporte = request.getParameter("rdGeneral");
        }
        movimientosBean = new MovimientosBean(tipoDependencia);
        movimientosBean.setStrServer(request.getHeader("host"));
        movimientosBean.setStrUbicacion(getServletContext().getRealPath(""));
        movimientosBean.resultSQLConecta(tipoDependencia);

        movimientoList = movimientosBean.getMovimientoByTipoMovReporte(tipoSolicitudId, estatusMovimientoId, year);

        for (Movimiento solicitudMovimiento : movimientoList) {
            contPar++;
            if (contPar % 2 == 0) {
                rowPar = "rowPar";
            } else {
                rowPar = "rowImpar";
            }

            btnVer = "<input type='button' class='btnbootstrap-drop btn-reporte-mini' title='PDF' style='width:40px;height:25px' onClick=\"imprimirReporteTransAmp(" + solicitudMovimiento.getOficio() + ",'" + solicitudMovimiento.getTipoMovimiento() + "','pdf','" + solicitudMovimiento.getTipoOficio() + "','" + solicitudMovimiento.getStatus() + "')\"' />";
            if (!sTipoReporte.equalsIgnoreCase("3")) {
                btnVer += "<input type='button' class='btnbootstrap-drop btn-Excel-mini' title='Excel' style='width:40px;height:25px' onClick=\"imprimirReporteTransAmp(" + solicitudMovimiento.getOficio() + ",'" + solicitudMovimiento.getTipoMovimiento() + "','xls','" + solicitudMovimiento.getTipoOficio() + "','" + solicitudMovimiento.getStatus() + "')\"' />";
            }
            /*btnVer = "<input type='button' class='btnbootstrap-drop btn-reporte' style='width:40px;height:40px' onClick=\"imprimirReporteTransAmp(" + solicitudMovimiento.getOficio() + ",'" + solicitudMovimiento.getTipoMovimiento() + "','pdf'," + sTipoReporte + ",'" + tipoSolicitudId + "')\"' />";
             if (!sTipoReporte.equalsIgnoreCase("2")) {
             btnVer += "<input type='button' class='btnbootstrap-drop btn-Excel' style='width:40px;height:40px' onClick=\"imprimirReporteTransAmp(" + solicitudMovimiento.getOficio() + ",'" + solicitudMovimiento.getTipoMovimiento() + "','xls'," + sTipoReporte + ",'" + tipoSolicitudId + "')\"' />";
             }*/
            btnInfo = "";
            strResult += "<tr class='" + rowPar + "'>";
            strResult += "<td>" + solicitudMovimiento.getOficio() + "</td>";
            if (solicitudMovimiento.getFechaElab() != null) {
                strResult += "<td>" + objFechas.getFechaFormato(solicitudMovimiento.getFechaElab(), Fechas.FORMATO_CORTO) + "</td>";
            } else {
                strResult += "<td>" + "" + "</td>";
            }
            if (solicitudMovimiento.getFecPPTO() != null) {
                strResult += "<td>" + objFechas.getFechaFormato(solicitudMovimiento.getFecPPTO(), Fechas.FORMATO_CORTO) + "</td>";
            } else {
                strResult += "<td>" + "" + "</td>";

            }
            strResult += "<td>" + solicitudMovimiento.getTipoMovimiento() + "</td>";
            strResult += "<td>" + solicitudMovimiento.getAppLogin() + "</td>";
            btnJust = "<input type='button' class='btnbootstrap-drop btn-infoGlobe-reporte ' style='width:40px;height:25px' title='" + solicitudMovimiento.getJutificacion() + "' " + "  />";
            strResult += "<td></td>";
            strResult += "<td>" + btnJust + btnInfo + btnVer + "</td>";
            strResult += "</tr>";


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
        if (movimientosBean != null) {
            movimientosBean.resultSQLDesconecta();
        }
        out.print(strResult);
    }
%>
