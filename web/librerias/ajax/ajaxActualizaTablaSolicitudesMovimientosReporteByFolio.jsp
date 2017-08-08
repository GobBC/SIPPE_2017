<%-- 
    Document   : ajaxActualizaTablaSolicitudesMovimientosReporteByFolio
    Created on : Dec 11, 2015, 9:03:06 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.aplicacion.MovimientosBean"%>
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

    Movimiento movto = new Movimiento();
    MovimientosBean movimientosBean = null;
    MovimientosRecalendarizacion movimiento = new MovimientosRecalendarizacion();
    List<Movimiento> movimientoList = new ArrayList<Movimiento>();
    Fechas objFechas = new Fechas();
    String sTipoReporte = "";
    String rol = new String();
    String rowPar = new String();
    String btnVer = new String();
    String btnInfo = new String();
    String btnJust = new String();
    String appLogin = new String();
    String strResult = new String();
    String tipoSolicitudId = new String();
    String tipoDependencia = new String();
    String estatusMovimientoId = new String();
    String strTipoOficio = new String();
    int year = 0;
    int folio = 0;
    int contPar = 0;

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
        if (request.getParameter("tipoSolicitudId") != null && !request.getParameter("tipoSolicitudId").equals("")) {
            tipoSolicitudId = request.getParameter("tipoSolicitudId");
        }
        if (request.getParameter("estatusMovimientoId") != null && !request.getParameter("estatusMovimientoId").equals("")) {
            estatusMovimientoId = request.getParameter("estatusMovimientoId");
        }

        if (Utilerias.existeParametro("rdGeneral", request)) {
            sTipoReporte = request.getParameter("rdGeneral");
        }
        if (Utilerias.existeParametro("folio", request)) {
            folio = Integer.parseInt(request.getParameter("folio"));
        }

        movimientosBean = new MovimientosBean(tipoDependencia);
        movimientosBean.setStrServer(request.getHeader("host"));
        movimientosBean.setStrUbicacion(getServletContext().getRealPath(""));
        movimientosBean.resultSQLConecta(tipoDependencia);

        movimientoList = movimientosBean.getMovsReporteByOficio(folio, year, appLogin, rol.equals(movimientosBean.getRolNormativo()));

        for (Movimiento solicitudMovimiento : movimientoList) {

            contPar++;

            if (contPar % 2 == 0) {
                rowPar = "rowPar";
            } else {
                rowPar = "rowImpar";
            }

            btnVer = "<input type='button' data-toggle='tooltip' class='btnbootstrap-drop btn-reporte-mini' title='PDF' style='width:40px;height:25px' onClick=\"imprimirReporteTransAmpByOficio(" + solicitudMovimiento.getOficio() + ",'" + solicitudMovimiento.getTipoMovimiento() + "','pdf','" + solicitudMovimiento.getTipoOficio() + "','" + solicitudMovimiento.getStatus() + "')\"' />";

            /*if (!solicitudMovimiento.getTipoMovimiento().equalsIgnoreCase("C")) {
             btnVer += "<input type='button' class='btnbootstrap-drop btn-Excel-mini' title='Excel' style='width:40px;height:25px' onClick=\"imprimirReporteTransAmpByOficio(" + solicitudMovimiento.getOficio() + ",'" + solicitudMovimiento.getTipoMovimiento() + "','xls','" + solicitudMovimiento.getTipoOficio() + "','" + solicitudMovimiento.getStatus() + "')\"' />";
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
            strTipoOficio = "";
            if (solicitudMovimiento.getTipoOficio() == null) {
                strTipoOficio = " - ";
            } else {
                if (solicitudMovimiento.getTipoOficio().trim().equals("A")) {
                    strTipoOficio = "Solicitud de Autorización";
                }
                if (solicitudMovimiento.getTipoOficio().trim().equals("V")) {
                    strTipoOficio = "Aviso de Modificación";
                }
                if (solicitudMovimiento.getTipoOficio().trim().equals("U")) {
                    strTipoOficio = "Solicitud Movimientos Automaticos";
                }
            }
            strResult += "<td>" + strTipoOficio + "</td>";
            strResult += "<td>" + solicitudMovimiento.getAppLogin() + "</td>";
            btnJust = "<input type='button' data-toggle='tooltip' class='btnbootstrap-drop btn-infoGlobe-reporte ' style='width:40px;height:25px' title='" + solicitudMovimiento.getJutificacion() + "' " + "  />";
            btnInfo = "<input type='button' class='btnbootstrap-drop btn-Excel-mini' style='width:40px;height:25px' "
                    + "onClick=\"imprimirReporteTransAmpByOficio("+ solicitudMovimiento.getOficio()+",'W','XLS')\" "
                    + "data-toggle='tooltip' title='Excel Detalle de Requerimientos' data-placement='right' />";
            
            //strResult += "<td></td>";
            strResult += "<td>" + btnJust + btnInfo + btnVer + "</td>";
            strResult += "</tr>";
            
            strResult += "<script type='text/javascript'>$(\"[data-toggle='tooltip']\").tooltip();</script>";
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
